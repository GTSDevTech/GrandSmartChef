import {Component, inject, OnInit, signal} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {IonCol, IonContent, IonLabel, IonRow} from '@ionic/angular/standalone';
import {BackHeaderComponent} from "../../components/headers/back-header/back-header.component";
import {
    IngredientOptionsComponent
} from "../../components/ingredient-panel/ingredient-options/ingredient-options.component";
import {IngredientDTO} from "../../models/ingredient.model";
import {IngredientService} from "../../services/ingredient/ingredient.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-ingredients',
  templateUrl: './ingredients.page.html',
  styleUrls: ['./ingredients.page.scss'],
  standalone: true,
  imports: [IonContent, CommonModule, FormsModule, BackHeaderComponent, IngredientOptionsComponent, IonCol, IonLabel, IonRow]
})
  export class IngredientsPage implements OnInit {
    private activatedRoute = inject(ActivatedRoute);
    private ingredientService = inject(IngredientService);
    ingredient = signal<IngredientDTO[]>([]);
    categoryId: string = '';

    constructor() { }

    loadIngredientsByCategory() {
      this.ingredientService.getAllIngredientByCategories(this.categoryId).subscribe({
        next: (data) => this.ingredient.set(data),
        error: (err) => console.error("Error cargando ingredientes", err),
      });
    }

    ngOnInit() {
      this.activatedRoute.queryParams.subscribe(params => {
        this.categoryId = params['categoryId'];
        this.loadIngredientsByCategory();
      });

    }


  }
