import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {AuthService} from "../auth/auth.service";
import {environment} from "../../../environments/environment";
import {throwError} from "rxjs";
import {RecipeCardDTO} from "../../models/recipe-card.model";
import {RecipeDTO} from "../../models/recipe.model";

@Injectable({
  providedIn: 'root'
})


export class RecipeService {

  private http = inject(HttpClient);
  private auth  = inject(AuthService);
  private apiUrl = `${environment.apiUrl}/recipes`;

  getAllActiveRecipes(){
    const token = this.auth.getToken();
    if (!token) {
      return throwError(() => new Error('No authentication token'));
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    const url = `${this.apiUrl}/card`;
    return this.http.get<RecipeCardDTO[]>(url, {headers});
  }

  getActiveRecipeDetails(id:number){
    const token = this.auth.getToken();
    if (!token) {
      return throwError(() => new Error('No authentication token'));
    }
    const params = new HttpParams().set('id', id.toString());
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    const url = `${this.apiUrl}/detail`;
    return this.http.get<RecipeDTO>(url, {headers, params});
  }

}
