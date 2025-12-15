import {Component, inject, input, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  IonButton,
  IonCard,
  IonCardContent, IonCardHeader, IonCardSubtitle, IonCardTitle, IonCol,
  IonContent, IonGrid,
  IonHeader, IonIcon, IonRow,
  IonTitle,
  IonToolbar
} from '@ionic/angular/standalone';
import {Router, RouterLink} from "@angular/router";
import {RecipeCardDTO} from "../../../models/recipe-card.model";

@Component({
  selector: 'app-create-recipe-card',
  templateUrl: './create-recipe-card.page.html',
  styleUrls: ['./create-recipe-card.page.scss'],
  standalone: true,
  imports: [IonContent, IonHeader, IonTitle, IonToolbar, CommonModule, FormsModule, IonButton, IonCard, IonCardContent, IonCardHeader, IonCardSubtitle, IonCardTitle, IonCol, IonGrid, IonIcon, IonRow, RouterLink]
})
export class CreateRecipeCardPage implements OnInit {

  private routes = inject(Router);
  recipe = input.required<RecipeCardDTO>();
  constructor() { }

  ngOnInit() {
  }
  goToRecipeForm(id: number) {
    this.routes.navigate(['/recipe-form', id]);
  }

}
