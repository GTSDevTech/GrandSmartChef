import { inject, Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { RecipeCardDTO } from '../../models/recipe-card.model';
import { RecipeDTO } from '../../models/recipe.model';
import { RecipeCreateDTO } from '../../models/recipeCreateDTO.model';
import {AuthService} from "../auth/auth.service";

@Injectable({
  providedIn: 'root'
})
export class CreateRecipe {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/recipes`;
  private authService = inject(AuthService);

  private getAuthHeaders() {
    const token = this.authService.getToken();
    if (!token) {
      throw new Error('No authentication token found');
    }
    return new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
  }

  getAllRecipes(): Observable<RecipeCardDTO[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<RecipeCardDTO[]>(`${this.apiUrl}/create/all`, { headers });
  }

  getActiveRecipeDetails(id: number): Observable<RecipeDTO> {
    const params = new HttpParams().set('id', id.toString());
    const headers = this.getAuthHeaders();
    return this.http.get<RecipeDTO>(`${this.apiUrl}/create/detail`, { headers, params });
  }

  createRecipe(formData: FormData): Observable<RecipeDTO> {
    const headers = this.getAuthHeaders();
    return this.http.post<RecipeDTO>(
      `${this.apiUrl}/create`,
      formData,
      { headers }
    );
  }

  updateRecipe(formData: FormData): Observable<RecipeDTO> {
    const headers = this.getAuthHeaders();
    return this.http.put<RecipeDTO>(
      `${this.apiUrl}/update`,
      formData,
      { headers }
    );
  }
  deleteRecipe(id: number): Observable<void> {
    const headers = this.getAuthHeaders();
    return this.http.delete<void>(
      `${this.apiUrl}/${id}`,
      { headers }
    );
  }



}
