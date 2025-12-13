import {Injectable, signal} from '@angular/core';
import {ShoppingListDTO} from "../../models/shoppingList.model";

@Injectable({
  providedIn: 'root'
})
export class ShoppingProgressService {

  progress = signal<number>(0)
  totalItems = signal<number>(0);
  boughtItems = signal<number>(0);

  updateProgress(shoppingLists: ShoppingListDTO[]){
    const total = shoppingLists
      .reduce((acc, list) => acc + (list.items?.length || 0), 0);
    const bought = shoppingLists
      .reduce((acc , list) => acc + (list.items?.filter(i => i.bought).length || 0), 0);
    this.totalItems.set(total);
    this.boughtItems.set(bought);
    const percentage = total > 0 ? (bought / total) * 100 : 0;
    this.progress.set(Math.round(percentage));

  }

}
