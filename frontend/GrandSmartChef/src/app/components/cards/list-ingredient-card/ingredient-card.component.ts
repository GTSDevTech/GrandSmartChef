import {Component, inject, input, OnInit, output} from '@angular/core';
import {
  IonCard,
  IonCardHeader,
  IonCheckbox,
  IonIcon,
  IonItem,
  IonLabel,
  IonRow
} from "@ionic/angular/standalone";
import {ShoppingListDTO} from "../../../models/shoppingList.model";
import {ShoppingListService} from "../../../services/shoppingList/shopping-list.service";
import {ShoppingListIngredientDTO} from "../../../models/shoppingListIngredient.model";


@Component({
  selector: 'app-ingredient-card',
  templateUrl: './ingredient-card.component.html',
  styleUrls: ['./ingredient-card.component.scss'],
  imports: [
    IonLabel,
    IonRow,
    IonItem,
    IonCard,
    IonCheckbox,
    IonIcon,
    IonCardHeader
  ]
})
export class IngredientCardComponent  implements OnInit {

  private shoppingListService = inject(ShoppingListService);

  shoppingList = input.required<ShoppingListDTO>()
  ingredientToggled = output<void>();

  constructor() {
  }

  ngOnInit() {

  }

  getRecipeName(items: ShoppingListIngredientDTO[]){

    return items.map(sli => sli.recipeName).pop();
  }

  toggleIngredient(ingredient: ShoppingListIngredientDTO) {
    this.shoppingListService.markIngredientBought(
      this.shoppingList().id!,
      ingredient.recipeId!,
      ingredient.ingredientId!,
      !ingredient.bought
    ).subscribe(() => {
      ingredient.bought = !ingredient.bought;
      this.ingredientToggled.emit();
    });
  }


}

