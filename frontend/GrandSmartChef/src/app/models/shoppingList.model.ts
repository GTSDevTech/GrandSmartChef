import {ShoppingListIngredientDTO} from "./shoppingListIngredient.model";

export interface ShoppingListDTO{

    id?: number;
    clientId: number;
    status: boolean;

    items: ShoppingListIngredientDTO[];


}
