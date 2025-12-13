import {RecipeCardDTO} from "./recipe-card.model";

export interface FavoriteCollectionDTO{
  id?:number;
  title?:string;
  color?:string;
  clientId?:number;
  recipes?: RecipeCardDTO[];
}
