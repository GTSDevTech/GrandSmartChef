import {Component, computed, inject, input, OnInit, signal} from '@angular/core';
import {
  IonButton,
  IonCard,
  IonCardContent,
  IonCardHeader,
  IonCardSubtitle,
  IonCardTitle, IonCol, IonGrid,
  IonIcon, IonRow
} from "@ionic/angular/standalone";
import {RouterLink} from "@angular/router";
import {RecipeCardDTO} from "../../../models/recipe-card.model";
import {CollectionService} from "../../../services/collection/collection.service";
import {environment} from "../../../../environments/environment.prod";
import {RatingService} from "../../../services/rating/rating.service";

@Component({
  selector: 'app-home-card',
  templateUrl: './home-card.component.html',
  styleUrls: ['./home-card.component.scss'],
  standalone: true,
  imports: [
    IonCard,
    IonCardHeader,
    IonCardTitle,
    IonCardSubtitle,
    IonCardContent,
    IonIcon,
    IonButton,
    IonGrid,
    IonRow,
    IonCol,
    RouterLink
  ]
})
export class HomeCardComponent  implements OnInit {

  private readonly backendUrl = environment.imageBaseUrl;
  private collectionService = inject(CollectionService);
  private ratingService = inject(RatingService);

  recipe = input.required<RecipeCardDTO>();


  constructor() {
  }

  ngOnInit() {
    this.isInFavorites()
  }


  isFavorite = computed(() => {
    const recipeId = this.recipe()?.id;
    if (!recipeId) return false;
    return this.collectionService.collections().some(c =>
      c.recipes?.some(r => r.id === recipeId)
    );
  });

  isInFavorites(): boolean {
    return this.isFavorite();
  }

  getStars() {
    return this.ratingService.getStars(
      this.recipe().averageRating
    );
  }


  getRecipeImage(imageUrl?: string | null): string {
    if (!imageUrl) {
      return '/assets/images/recipes/default_profile_image.png';
    }
    return `${this.backendUrl}${imageUrl}`;
  }
}
