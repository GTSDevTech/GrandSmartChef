import {Component, effect, inject, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import {HeaderComponent} from "../../components/headers/main-header/header.component";
import {FooterNavComponent} from "../../components/footer-nav/footer-nav.component";
import {HomeCardComponent} from "../../components/cards/home-recipe-card/home-card.component";
import {SearcherComponent} from "../../components/searchers/searcher-home/searcher.component";
import {FilterComponent} from "../../components/filters/filter-modal/filter.component";
import {RecipeService} from "../../services/recipe/recipe.service";
import {ModalService} from "../../services/modal/modal.service";
import {AuthService} from "../../services/auth/auth.service";
import {CollectionService} from "../../services/collection/collection.service";
import {ScrollFooterService} from "../../services/scroll/scroll-footer/scroll-footer.service";
import {IonButton, IonCol, IonContent, IonIcon, IonRow} from "@ionic/angular/standalone";
import {ClientService} from "../../services/client/client.service";
import {IngredientService} from "../../services/ingredient/ingredient.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule, HeaderComponent, FooterNavComponent, RouterModule, HomeCardComponent,
    SearcherComponent, FilterComponent, IonContent, IonRow, IonCol, IonButton, IonIcon]
})
export class HomePage implements OnInit {
  private scrollFooter = inject(ScrollFooterService);
  private recipeService = inject(RecipeService);
  private ingredientService = inject(IngredientService);
  private modalService = inject(ModalService);
  private auth = inject(AuthService);
  private collectionService = inject(CollectionService);
  private clientService = inject(ClientService);


  user = this.auth.currentUser;
  recipes = this.recipeService.recipes;

  constructor() {
    effect(() => {
      const user = this.user();
      if (!user?.id) return;
      this.collectionService.loadCollections(user.id);
    });

    effect(() => {
      const user = this.user();
      const ingredientIds = this.ingredientService.getSelectedIds();

      const hasIngredients = ingredientIds.length > 0;
      const hasPrefs = (user?.preferences?.length ?? 0) > 0;

      if (hasIngredients) {
        this.recipeService.filterByIngredients(ingredientIds);
        this.recipeService.filterByUserPreferences(hasPrefs ? user!.id! : null);
        return;
      }
      this.recipeService.filterByUserPreferences(hasPrefs ? user?.id ?? null : null);
    });
  }

  ngOnInit() {
    this.auth.ensureCurrentUserLoaded();
    this.recipeService.loadActiveRecipes();

  }


  openModal() {
    this.modalService.open('recipe-filter');
  }

  onScroll(event?: CustomEvent) {
    const scrollTop = (event?.detail as any)?.scrollTop;
    if (scrollTop != null) {
      this.scrollFooter.updateScroll(scrollTop);
    }
  }

  onPreferencesChange(prefs: { id: number; name: string }[]) {
    this.clientService.updatePreferences(prefs).subscribe({
      next: () => {

      }
    });
  }


  clearFilters() {
    this.ingredientService.clearSelection();
    this.recipeService.clearFilters();
  }

}
