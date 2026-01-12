import {TagDTO} from "./tag.model";

export interface RecipeCardDTO{

  id:number
  name:string;
  imageUrl:string;
  averageRating: number;
  servings:number;
  prepTime:number;
  difficulty:string;
  tags: TagDTO[];
}
