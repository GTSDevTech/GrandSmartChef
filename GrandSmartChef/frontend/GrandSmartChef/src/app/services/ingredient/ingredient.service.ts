import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {AuthService} from "../auth/auth.service";
import {environment} from "../../../environments/environment";
import {throwError} from "rxjs";
import {CategoryDTO} from "../../models/category.model";
import {IngredientDTO} from "../../models/ingredient.model";


@Injectable({
  providedIn: 'root'
})
export class IngredientService {

  private http = inject(HttpClient);
  private auth = inject(AuthService)
  private apiUrl = `${environment.apiUrl}/ingredient`;
  private token = this.auth.getToken();

  getAllCategoriesWithIngredient(){

    if(!this.token){
      return throwError(() => new Error('No authentication token'));
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.token}`
    });
    const url = `${this.apiUrl}/categories`;
    return this.http.get<CategoryDTO[]>(url, {headers});
  }

  getAllIngredientByCategories(categoryId: string){
    if(!this.token){
      return throwError(() => new Error('No authentication token'));
    }
    const params = new HttpParams().set('id', categoryId);
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.token}`
    });
    const url = `${this.apiUrl}/by-category/${categoryId}`;
    return this.http.get<IngredientDTO[]>(url, {headers, params});
  }


  getAllIngredients() {
    if(!this.token){
      return throwError(() => new Error('No authentication token'));
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.token}`
    });
    const url = `${this.apiUrl}/all`;
    return this.http.get<IngredientDTO[]>(url, {headers});
  }


}
