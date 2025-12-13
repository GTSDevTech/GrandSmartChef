import {CategoryDTO} from "./category.model";

export interface IngredientDTO {
  id: number;
  name: string;
  calories: number;
  proteins: number;
  carbs: number;
  fats: number;
  urlPhoto: string;
  unit: string;
  category: CategoryDTO;

}
