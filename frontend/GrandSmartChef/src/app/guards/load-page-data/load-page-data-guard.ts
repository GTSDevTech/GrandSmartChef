import { CanMatchFn } from '@angular/router';
import {inject} from "@angular/core";
import {RecipeService} from "../../services/recipe/recipe.service";
import {AuthService} from "../../services/auth/auth.service";
import {CollectionService} from "../../services/collection/collection.service";
import {firstValueFrom, lastValueFrom} from "rxjs";

export const loadPageDataGuard: CanMatchFn = async (route, segments) => {
  const recipeService = inject(RecipeService)
  const auth = inject(AuthService);
  const collectionService = inject(CollectionService);

  try{

    const recipes = await lastValueFrom(recipeService.getAllActiveRecipes())


    return true;
  }catch(error){
    return false
  }

};
