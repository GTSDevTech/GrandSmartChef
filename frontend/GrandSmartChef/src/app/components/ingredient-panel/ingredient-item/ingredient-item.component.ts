import {Component, input, OnInit} from '@angular/core';
import {IonCol, IonGrid, IonIcon, IonImg, IonItem, IonLabel, IonRow} from "@ionic/angular/standalone";
import {IngredientDTO} from "../../../models/ingredient.model";


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
  constructor() { }

  ngOnInit() {}

}
