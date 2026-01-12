import {Component, computed, inject, input, OnInit, output, signal} from '@angular/core';
import {
  IonContent,
  IonHeader,
  IonItem,
  IonLabel,
  IonList,
  IonModal,
  IonSearchbar,
  IonToolbar
} from "@ionic/angular/standalone";
import {ModalService} from "../../../../services/modal/modal.service";
import {IngredientDTO} from "../../../../models/ingredient.model";
import {PreferenceDTO} from "../../../../models/preference.model";
import {TagDTO} from "../../../../models/tag.model";

@Component({
    selector: 'app-ingredient-modal',
    templateUrl: './ingredient-modal.component.html',
    styleUrls: ['./ingredient-modal.component.scss'],
  imports: [
    IonContent,
    IonItem,
    IonLabel,
    IonList,
    IonModal,
    IonSearchbar,
    IonHeader,
    IonToolbar
  ]
})
export class IngredientModalComponent  implements OnInit {
  private modalService = inject(ModalService);
  isOpen = this.modalService.isOpen('ingredient-modal');
  ingredients = signal<IngredientDTO[]>([]);
  disableIngredientIds = signal<Set<number>>(new Set());
  searchText = signal('');


  ngOnInit() {}

  filteredIngredients = computed(() => {
    const value = this.searchText().toLowerCase();
    return this.ingredients().filter(i =>
      i.name.toLowerCase().includes(value)
    );
  });

  open(data: {ingredients: IngredientDTO[]; disabledIds: number[] }) {
    this.ingredients.set(data.ingredients);
    this.disableIngredientIds.set(new Set(data.disabledIds));
    this.searchText.set('');
    this.modalService.open('ingredient-modal');
  }

  isDisabled(ingredient: IngredientDTO): boolean {
    return this.disableIngredientIds().has(ingredient.id);
  }

  select(ingredient: IngredientDTO) {
    this.modalService.close('ingredient-modal', ingredient);
  }

  close() {
    this.modalService.close('ingredient-modal');
  }

  onSearch(event: Event) {
    const value = (event.target as HTMLIonSearchbarElement).value || '';
    this.searchText.set(value);
  }
}


