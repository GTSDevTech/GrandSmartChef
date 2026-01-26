import {Component, inject, OnInit} from '@angular/core';
import {IonButton, IonChip, IonCol, IonImg, IonLabel, IonRow} from "@ionic/angular/standalone";
import {SearcherIngredientComponent} from "../../searchers/searcher-ingredient/searcher-ingredient.component";
import {Router} from "@angular/router";
import {IngredientService} from "../../../services/ingredient/ingredient.service";
import {RecipeService} from "../../../services/recipe/recipe.service";

@Component({
  selector: 'app-ingredient-options',
  templateUrl: './ingredient-options.component.html',
  styleUrls: ['./ingredient-options.component.scss'],
  imports: [
    IonRow,
    IonImg,
    IonButton,
    SearcherIngredientComponent,
    IonCol,
    IonChip,
    IonLabel
  ]
})
export class IngredientOptionsComponent  implements OnInit {

  private ingredientService = inject(IngredientService);
  private recipeService = inject(RecipeService);
  private router = inject(Router);

  selected = this.ingredientService.selectedIngredients;

  applyFilter() {
    this.router.navigate(['/home'], {
      queryParams: { refresh: Date.now() }
    });
  }

  clearAll() {
    this.ingredientService.clearSelection();
    this.recipeService.clearFilters();
    this.applyFilter();
  }

  ngOnInit() {}

}
