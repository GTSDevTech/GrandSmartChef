import {IngredientDTO} from "./ingredient.model";


export interface RecipeIngredientDTO{
  quantity: number;
  unit: string;
  ingredient: IngredientDTO;

}
