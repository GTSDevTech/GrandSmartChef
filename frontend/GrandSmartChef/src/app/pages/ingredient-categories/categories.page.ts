import {Component, inject, OnInit, signal} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  IonCol,
  IonContent,
  IonLabel,
  IonRow,
} from '@ionic/angular/standalone';
import {BackHeaderComponent} from "../../components/headers/back-header/back-header.component";
import {
  IngredientOptionsComponent
} from "../../components/ingredient-panel/ingredient-options/ingredient-options.component";
import {CategoryDTO} from "../../models/category.model";
import {IngredientService} from "../../services/ingredient/ingredient.service";

@Component({
  selector: 'app-categories',
  templateUrl: './categories.page.html',
  styleUrls: ['./categories.page.scss'],
  standalone: true,
  imports: [IonContent, CommonModule, FormsModule, BackHeaderComponent,
    IonCol, IngredientOptionsComponent, IonLabel, IonRow]
})
export class CategoriesPage implements OnInit {

  private ingredientService = inject(IngredientService);

  category = signal<CategoryDTO[]>([]);

  constructor() { }

  ngOnInit() {
    this.ingredientService.getAllCategoriesWithIngredient().subscribe({
      next: (data) => this.category.set(data),
      error: (err) => console.error("Error cargando categorias", err)
    });
  }

}
