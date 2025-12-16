import {Component, inject, input, OnInit, output} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  IonButton,
  IonCard,
  IonCardContent, IonCardHeader, IonCardSubtitle, IonCardTitle, IonCol,
  IonContent, IonGrid,
  IonHeader, IonIcon, IonImg, IonRow,
  IonTitle,
  IonToolbar
} from '@ionic/angular/standalone';
import {Router, RouterLink} from "@angular/router";
import {RecipeCardDTO} from "../../../models/recipe-card.model";
import {environment} from "../../../../environments/environment.prod";

@Component({
  selector: 'app-create-recipe-card',
  templateUrl: './create-recipe-card.page.html',
  styleUrls: ['./create-recipe-card.page.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule, IonButton, IonCard, IonCardContent, IonCardHeader, IonCardSubtitle, IonCardTitle, IonCol, IonGrid, IonIcon, IonRow]
})
export class CreateRecipeCardPage {

  private readonly backendUrl = environment.imageBaseUrl;
  private routes = inject(Router);
  recipe = input.required<RecipeCardDTO>();

  delete = output<number>();


  goToRecipeForm(id: number) {
    this.routes.navigate(['/recipe-form', id]);
  }

  onDelete() {
    this.delete.emit(this.recipe().id);
  }

  getRecipeImage(imageUrl?: string | null): string {
    if (!imageUrl) {
      return '/assets/images/recipes/default_profile_image.png';
    }
    return `${this.backendUrl}${imageUrl}`;
  }
}
