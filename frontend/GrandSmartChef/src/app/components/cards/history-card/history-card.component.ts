import {Component, inject, input, OnInit} from '@angular/core';
import {
  IonIcon,
  IonImg,
  IonItem,
  IonItemDivider,
  IonLabel,
  IonList, IonRow, IonText
} from "@ionic/angular/standalone";
import {HistoryDTO} from "../../../models/history.model";
import {RecipeCardDTO} from "../../../models/recipe-card.model";
import {environment} from "../../../../environments/environment.prod";
import {RatingService} from "../../../services/rating/rating.service";

@Component({
  selector: 'app-history-card',
  templateUrl: './history-card.component.html',
  styleUrls: ['./history-card.component.scss'],
  imports: [
    IonList,
    IonItemDivider,
    IonLabel,
    IonItem,
    IonIcon,
    IonImg,
    IonText,
    IonRow,

  ]
})
export class HistoryCardComponent  implements OnInit {

  private readonly backendUrl = environment.imageBaseUrl;
  private ratingService = inject(RatingService);

  histories = input<HistoryDTO[]>();


  ngOnInit() {
    console.log(this.histories());
  }

  getRecipeImage(imageUrl?: string | null): string {
    if (!imageUrl) {
      return '/assets/images/recipes/default_profile_image.png';
    }
    return `${this.backendUrl}${imageUrl}`;
  }

  getStars(recipe: RecipeCardDTO) {
    return this.ratingService.getStars(recipe.averageRating);
  }

}
