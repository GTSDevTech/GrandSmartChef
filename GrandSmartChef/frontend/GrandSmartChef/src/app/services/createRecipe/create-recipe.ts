import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {RecipeCardDTO} from "../../models/recipe-card.model";
import {environment} from "../../../environments/environment";
import {Observable, of, throwError} from "rxjs";
import {RecipeDTO} from "../../models/recipe.model";

@Injectable({
  providedIn: 'root'
})
export class CreateRecipe {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/recipes`;

  getAllRecipes(){
    const url = `${this.apiUrl}/create/all`;
    console.log(url);
    return this.http.get<RecipeCardDTO[]>(url);
  }

  getActiveRecipeDetails(id: number): Observable<RecipeDTO> {
    const url = `${this.apiUrl}/create/detail`;

    const params = new HttpParams().set('id', id.toString());

    return this.http.get<RecipeDTO>(url, { params });
  }

  updateRecipe(id: number | undefined, payload: any) {
    console.log('Simulando updateRecipe', id, payload);
    return of(null);
  }

  createRecipe(payload: any) {
    console.log('Simulando createRecipe', payload);
    return of(null);
  }
}
