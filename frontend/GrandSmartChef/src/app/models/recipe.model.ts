import {RecipeIngredientDTO} from "./recipe-ingredient.model";
import {RecipeStepDTO} from "./recipeStep.model";
import {NutritionInfoDTO} from "./nutritionInfo.model";
import {TagDTO} from "./tag.model";

export interface RecipeDTO{

  id:number
  name:string;
  difficulty:string;
  servings:number;
  prepTime: number;
  averageRating: number;
  description: string;
  imageUrl:string;
  tags: TagDTO[];
  ingredients: RecipeIngredientDTO[];
  steps: RecipeStepDTO[];
  nutritionInfo?: NutritionInfoDTO


}
