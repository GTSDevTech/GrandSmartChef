import {Component, computed, inject, input, OnInit} from '@angular/core';
import {IonCol, IonGrid, IonIcon, IonImg, IonItem, IonLabel, IonRow} from "@ionic/angular/standalone";
import {IngredientDTO} from "../../../models/ingredient.model";
import {IngredientService} from "../../../services/ingredient/ingredient.service";


@Component({
  selector: 'app-ingredient-item',
  templateUrl: './ingredient-item.component.html',
  styleUrls: ['./ingredient-item.component.scss'],
  imports: [
    IonCol,
    IonGrid,
    IonIcon,
    IonImg,
    IonItem,
    IonLabel,
    IonRow
  ]
})
export class IngredientItemComponent  implements OnInit {


  ingredient = input.required<IngredientDTO>();
  private ingredientService = inject(IngredientService);

  toggle() {
    this.ingredientService.toggleIngredient(this.ingredient());
  }

  isSelected = computed(() =>
    this.ingredientService.isSelected(this.ingredient().id)
  );

  ngOnInit(): void {
  }

}
