import {RecipeCardDTO} from "./recipe-card.model";

export interface HistoryDTO{
  id: number;
  clientId: number;
  recipe: RecipeCardDTO;
  date: string;
}
