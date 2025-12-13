import { Component, OnInit } from '@angular/core';
import {IonSearchbar} from "@ionic/angular/standalone";

@Component({
  selector: 'app-searcher-ingredient',
  templateUrl: './searcher-ingredient.component.html',
  styleUrls: ['./searcher-ingredient.component.scss'],
  imports: [
    IonSearchbar
  ]
})
export class SearcherIngredientComponent  implements OnInit {

  constructor() { }

  ngOnInit() {}

}
