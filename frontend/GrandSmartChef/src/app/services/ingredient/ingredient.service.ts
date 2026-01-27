import {inject, Injectable, signal} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {CategoryDTO} from "../../models/category.model";
import {IngredientDTO} from "../../models/ingredient.model";


@Injectable({
  providedIn: 'root'
})
export class IngredientService {

  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/ingredient`;

  private _selectedIngredients = signal<IngredientDTO[]>([]);
  selectedIngredients = this._selectedIngredients.asReadonly();


  getAllCategoriesWithIngredient() {
    return this.http.get<CategoryDTO[]>(
      `${this.apiUrl}/categories`
    );
  }

  getAllIngredientByCategories(categoryId: string) {
    return this.http.get<IngredientDTO[]>(
      `${this.apiUrl}/by-category/${categoryId}`
    );
  }

  getAllIngredients() {
    return this.http.get<IngredientDTO[]>(
      `${this.apiUrl}/all`
    );
  }

  toggleIngredient(ingredient: IngredientDTO) {
    const current = this._selectedIngredients();
    const exists = current.find(i => i.id === ingredient.id);

    if (exists) {
      this._selectedIngredients.set(
        current.filter(i => i.id !== ingredient.id)
      );
      return;
    }

    if (current.length >= 3) {
      return;
    }

    this._selectedIngredients.set([...current, ingredient]);
  }

  clearSelection() {
    this._selectedIngredients.set([]);
  }

  isSelected(id: number): boolean {
    return this._selectedIngredients().some(i => i.id === id);
  }

  getSelectedIds(): number[] {
    return this._selectedIngredients().map(i => i.id);
  }



}
