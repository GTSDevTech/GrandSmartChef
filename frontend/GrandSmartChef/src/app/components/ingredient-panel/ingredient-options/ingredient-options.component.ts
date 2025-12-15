import { Component, OnInit } from '@angular/core';
import {IonButton, IonCol, IonImg, IonRow} from "@ionic/angular/standalone";
import {SearcherIngredientComponent} from "../../searchers/searcher-ingredient/searcher-ingredient.component";

@Component({
  selector: 'app-ingredient-options',
  templateUrl: './ingredient-options.component.html',
  styleUrls: ['./ingredient-options.component.scss'],
  imports: [
    IonRow,
    IonImg,
    IonButton,
    SearcherIngredientComponent,
    IonCol
  ]
})
export class IngredientOptionsComponent  implements OnInit {

  constructor() { }

  ngOnInit() {}

}
