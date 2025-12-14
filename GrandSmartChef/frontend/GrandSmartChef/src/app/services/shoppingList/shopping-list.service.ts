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


  shoppingLists = signal<ShoppingListDTO[]>([]);


  getAllShoppingListByUserId(idUser: number): Observable<ShoppingListDTO[]> {
    const token = this.auth.getToken();
    if (!token) {
      this.shoppingLists.set([]);
      return throwError(() => new Error('No authentication token'));
    }

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    const url = `${this.apiUrl}/user/${idUser}`;

    return this.http.get<ShoppingListDTO[]>(url, { headers }).pipe(
      tap(data => {
        this.shoppingLists.set(data ?? []);
      }),
      catchError(err => {
        this.shoppingLists.set([]);
        return throwError(() => err);
      })
    );
  }


  addRecipeToShoppingList(
    userId: number,
    recipeId: number
  ): Observable<ShoppingListDTO> {
    const token = this.auth.getToken();
    if (!token) return throwError(() => new Error('No authentication token'));

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    return this.http.post<ShoppingListDTO>(
      `${this.apiUrl}/create?userId=${userId}&recipeId=${recipeId}`,
      {},
      { headers }
    );
  }

  markIngredientBought(
    listId: number,
    recipeId: number,
    ingredientId: number,
    bought: boolean
  ): Observable<void> {
    const token = this.auth.getToken();
    if (!token) return throwError(() => new Error('No authentication token'));

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    const url = `${this.apiUrl}/${listId}/recipe/${recipeId}/ingredient/${ingredientId}/bought`;

    return this.http.patch<void>(
      url,
      null,
      { headers, params: new HttpParams().set('bought', bought) }
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

  deleteAllBoughtIngredientsByUser(userId: number): Observable<void> {
    const token = this.auth.getToken();
    if (!token) return throwError(() => new Error('No authentication token'));

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    const url = `${this.apiUrl}/delete/boughtIngredients/${userId}`;

    return this.http.delete<void>(url, { headers });
  }

  addRecipeToCart(
    userId: number,
    recipeId: number
  ): Observable<ShoppingListDTO> {
    return this.addRecipeToShoppingList(userId, recipeId).pipe(
      tap(newList => {
        if (!newList) return;

        const current = this.shoppingLists();
        const index = current.findIndex(l => l.id === newList.id);

        if (index >= 0) {
          current[index] = newList;
        } else {
          current.push(newList);
        }

        // 👇 fuerza nueva referencia
        this.shoppingLists.set([...current]);
      })
    );
  }
}
