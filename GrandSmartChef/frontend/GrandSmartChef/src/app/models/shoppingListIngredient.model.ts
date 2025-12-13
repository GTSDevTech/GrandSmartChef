export interface ShoppingListIngredientDTO{

  id: number;
  recipeId: number;
  ingredientId:number;
  ingredientName:string;
  recipeName:string;
  quantity: number;
  unit: string;
  bought: boolean;

}
