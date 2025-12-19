import {Component, computed, effect, inject, Input, OnInit, output, signal} from '@angular/core';
import {RecipeService} from "../../../services/recipe/recipe.service";
import {ModalService} from "../../../services/modal/modal.service";
import {RecipeCardDTO} from "../../../models/recipe-card.model";
import {CollectionService} from "../../../services/collection/collection.service";
import {
  IonAvatar,
  IonButton,
  IonCol,
  IonContent,
  IonIcon, IonImg,
  IonItem, IonLabel,
  IonList, IonModal, IonRow, IonSearchbar,
} from "@ionic/angular/standalone";
import {forkJoin} from "rxjs";

@Component({
  selector: 'app-add-recipe-to-collection-sheet',
  templateUrl: './add-recipe-to-collection-sheet.component.html',
  styleUrls: ['./add-recipe-to-collection-sheet.component.scss'],
  imports: [
    IonModal,
    IonContent,
    IonList,
    IonItem,
    IonButton,
    IonLabel,
    IonModal,
    IonSearchbar,
    IonAvatar,
    IonImg,
    IonRow,
    IonCol,
    IonIcon
  ]
})
export class AddRecipeToCollectionSheetComponent implements OnInit {

  private recipeService = inject(RecipeService);
  private collectionService = inject(CollectionService);
  private modalService = inject(ModalService);

  isOpen = this.modalService.isOpen('add-recipe-to-collection');
  collectionId = this.modalService.getData('add-recipe-to-collection');

  recipes = signal<RecipeCardDTO[]>([]);
  selectedRecipes = signal<number[]>([]);
  searchRecipe = signal<string>('');
  recipesToAdd = output<number>();


  private alreadyInCollectionIds = computed<number[]>(() => {
    const id = this.collectionId();
    if (!id) return [];

    const col = this.collectionService.collections().find(c => c.id === id);
    return (col?.recipes ?? []).map(r => r.id);
  });

  constructor() {

    effect(() => {
      const open = this.isOpen();
      const id = this.collectionId();

      if (open && id) {
        this.searchRecipe.set('');
        this.selectedRecipes.set([]);
      }
    });
  }

  ngOnInit() {
    this.loadRecipes();
  }

  loadRecipes() {
    this.recipeService.getAllActiveRecipes().subscribe({
      next: (res) => this.recipes.set(res ?? []),
      error: (err) => console.error('Error loading recipes', err)
    });
  }

  isAlreadyInCollection(recipeId: number): boolean {
    return this.alreadyInCollectionIds().includes(recipeId);
  }

  toggleSelection(recipeId: number) {
    if (this.isAlreadyInCollection(recipeId)) return;

    const selected = [...this.selectedRecipes()];
    const index = selected.indexOf(recipeId);
    if (index >= 0) selected.splice(index, 1);
    else selected.push(recipeId);
    this.selectedRecipes.set(selected);
  }

  confirm() {
    const ids = this.selectedRecipes();
    const collectionId = this.collectionId();
    if (!collectionId) return;

    if (ids.length === 0) {
      this.modalService.close('add-recipe-to-collection');
      return;
    }

    const calls = ids.map(id => this.collectionService.addRecipeToCollection(collectionId, id));
    forkJoin(calls).subscribe({
      next: () => {
        this.recipesToAdd.emit(collectionId);
        this.modalService.close('add-recipe-to-collection', collectionId);

        this.selectedRecipes.set([]);
        this.searchRecipe.set('');
      },
      error: (err) => console.error('Error adding recipes to collection:', err)
    });
  }

  updateSearch(event: Event) {
    const input = event.target as HTMLIonSearchbarElement;
    this.searchRecipe.set(input.value || '');
  }

  filteredRecipes() {
    const term = this.searchRecipe().trim().toLowerCase();
    if (!term) return this.recipes();
    return this.recipes().filter(r => r.name.toLowerCase().includes(term));
  }

  close() {
    this.modalService.close('add-recipe-to-collection');
    this.searchRecipe.set('');
    this.selectedRecipes.set([]);
  }
}
