import {inject, Injectable, signal} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {RecipeCardDTO} from "../../models/recipe-card.model";
import {RecipeDTO} from "../../models/recipe.model";

@Injectable({
  providedIn: 'root'
})
export class RecipeService {

  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/recipes`;

  private filterUserId: number | null = null;
  private filterIngredientIds: number[] = [];

  private _recipes = signal<RecipeCardDTO[]>([]);
  recipes = this._recipes.asReadonly();


  loadActiveRecipes() {
    this.http.get<RecipeCardDTO[]>(`${this.apiUrl}/card`)
      .subscribe({
        next: recipes => this._recipes.set(recipes ?? []),
        error: () => this._recipes.set([])
      });
  }

  getActiveRecipeDetails(id: number) {
    return this.http.get<RecipeDTO>(
      `${this.apiUrl}/detail`,
      { params: new HttpParams().set('id', id.toString()) }
    );
  }

  updateRecipeRating(recipeId: number, rating: number) {
    this._recipes.update(recipes =>
      recipes.map(r =>
        r.id === recipeId
          ? {
            ...r,
            averageRating: rating
          }
          : r
      )
    );
  }

  filterByUserPreferences(userId: number | null) {
    this.filterUserId = userId;
    this.search();
  }


  filterByIngredients(ids: number[]) {
    this.filterIngredientIds = ids;
    this.search();
  }

  clearFilters() {
    this.filterUserId = null;
    this.filterIngredientIds = [];
    this.loadActiveRecipes();
  }

  private search() {
    const hasUser = !!this.filterUserId;
    const hasIngredients = this.filterIngredientIds.length > 0;

    if (!hasUser && !hasIngredients) {
      this.loadActiveRecipes();
      return;
    }

    let params = new HttpParams();

    if (hasUser) {
      params = params.set('userId', this.filterUserId!);
    }

    if (hasIngredients) {
      this.filterIngredientIds.forEach(id => {
        params = params.append('ingredientIds', id);
      });
    }

    this.http.get<RecipeCardDTO[]>(
      `${this.apiUrl}/search`,
      { params }
    ).subscribe({
      next: r => this._recipes.set(r ?? []),
      error: () => this._recipes.set([])
    });
  }
}
