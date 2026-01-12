import {inject, Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {RatingDTO} from "../../models/recipe-rating";
import {tap} from "rxjs";
import {RecipeService} from "../recipe/recipe.service";

@Injectable({
  providedIn: 'root'
})
export class RatingService {

  private http = inject(HttpClient);
  private recipeService = inject(RecipeService);
  private apiUrl = `${environment.apiUrl}/rating`;

  rateRecipe(body: RatingDTO) {
    return this.http.post<void>(
      `${this.apiUrl}/vote`,
      body
    ).pipe(
      tap(() => {
        // 🔁 Actualiza rating en memoria (cards + detalle)
        this.recipeService.updateRecipeRating(
          body.recipeId,
          body.rating
        );
      })
    );
  }

  getStars(rating: number | null | undefined): ('full' | 'half' | 'empty')[] {
    const stars: ('full' | 'half' | 'empty')[] = [];
    let value = rating ?? 0;

    for (let i = 0; i < 5; i++) {
      if (value >= 1) stars.push('full');
      else if (value >= 0.5) stars.push('half');
      else stars.push('empty');
      value -= 1;
    }

    return stars;
  }

}
