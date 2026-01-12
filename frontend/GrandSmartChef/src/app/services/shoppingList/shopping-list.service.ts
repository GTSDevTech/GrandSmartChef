import {inject, Injectable, signal} from '@angular/core';
import {AuthService} from "../auth/auth.service";
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {catchError, Observable, tap, throwError} from "rxjs";
import {ShoppingListDTO} from "../../models/shoppingList.model";

@Injectable({
  providedIn: 'root'
})
export class ShoppingListService {

  private http = inject(HttpClient);
  private auth = inject(AuthService);
  private apiUrl = `${environment.apiUrl}/shopping-list`;


  private _shoppingLists = signal<ShoppingListDTO[]>([]);
  shoppingLists = this._shoppingLists.asReadonly();

  private loaded = false;

  getAllShoppingListByUserId(userId: number){
    if (this.loaded) return;

    this.http.get<ShoppingListDTO[]>(
      `${this.apiUrl}/user/${userId}`
    ).subscribe({
      next: lists => {
        this._shoppingLists.set(lists ?? []);
        this.loaded = true;
      },
      error: () => {
        this._shoppingLists.set([]);
        this.loaded = true;
      }
    });
  }


  addRecipeToShoppingList(userId: number,recipeId: number){
    const params = new HttpParams()
      .set('userId', userId)
      .set('recipeId', recipeId);
    return this.http.post<ShoppingListDTO>(
      `${this.apiUrl}/create`,
      null,
      { params }
    ).pipe(
      tap(newList => {
        const current = this._shoppingLists();
        const index = current.findIndex(l => l.id === newList.id);

        if (index >= 0) {
          current[index] = newList;
        } else {
          current.push(newList);
        }

        this._shoppingLists.set([...current]);
      })
    );
  }

  markIngredientBought(
    listId: number,
    recipeId: number,
    ingredientId: number,
    bought: boolean
  ) {
    return this.http.patch<void>(
      `${this.apiUrl}/${listId}/recipe/${recipeId}/ingredient/${ingredientId}/bought`,
      null,
      { params: { bought } }
    ).pipe(
      tap(() => {
        this._shoppingLists.update(lists =>
          lists.map(list =>
            list.id !== listId
              ? list
              : {
                ...list,
                items: list.items.map(item =>
                  item.ingredientId === ingredientId
                    ? { ...item, bought }
                    : item
                )
              }
          )
        );
      })
    );
  }


  deleteShoppingListIngredient(
    listId: number,
    bought: boolean
  ): Observable<void> {
    const token = this.auth.getToken();
    if (!token) return throwError(() => new Error('No authentication token'));

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    const url = `${this.apiUrl}/${listId}/ingredient/${bought}`;

    return this.http.delete<void>(url, { headers });
  }


  deleteAllBoughtIngredientsByUser(userId: number) {
    return this.http.delete<void>(
      `${this.apiUrl}/delete/boughtIngredients/${userId}`
    ).pipe(
      tap(() => {
        this._shoppingLists.update(lists =>
          lists
            .map(list => ({
              ...list,
              items: list.items.filter(i => !i.bought)
            }))
            .filter(list => list.items.length > 0)
        );
      })
    );
  }


  addRecipeToCart(userId: number, recipeId: number) {
    return this.addRecipeToShoppingList(userId, recipeId);
  }
}
