import {Component, inject, OnInit, signal} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import {IonicModule, ScrollBaseDetail} from '@ionic/angular';
import {HeaderComponent} from "../../components/headers/main-header/header.component";
import {FooterNavComponent} from "../../components/footer-nav/footer-nav.component";
import {HomeCardComponent} from "../../components/cards/home-recipe-card/home-card.component";
import {SearcherComponent} from "../../components/searchers/searcher-home/searcher.component";
import {FilterComponent} from "../../components/filters/filter-modal/filter.component";

import {RecipeService} from "../../services/recipe/recipe.service";
import {RecipeCardDTO} from "../../models/recipe-card.model";
import {ModalService} from "../../services/modal/modal.service";
import {AuthService} from "../../services/auth/auth.service";
import {CollectionService} from "../../services/collection/collection.service";
import {ScrollFooterService} from "../../services/scroll/scroll-footer/scroll-footer.service";
import {IonButton, IonCol, IonContent, IonIcon, IonRow} from "@ionic/angular/standalone";
import {ClientService} from "../../services/client/client.service";
import {FilterProfileComponent} from "../../components/filters/filter-profile/filter-profile.component";

@Component({
  selector: 'app-home',
  templateUrl: './home.page.html',
  styleUrls: ['./home.page.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule, HeaderComponent, FooterNavComponent, RouterModule, HomeCardComponent,
    SearcherComponent, FilterComponent, IonContent, IonRow, IonCol, IonButton, IonIcon, FilterProfileComponent]
})
export class HomePage implements OnInit {
  private scrollFooter = inject(ScrollFooterService);
  private recipeService = inject(RecipeService);
  private modalService = inject(ModalService);
  private auth = inject(AuthService);
  private collectionService = inject(CollectionService);
  private clientService = inject(ClientService);

  user = this.auth.currentUser();

  recipes = signal<RecipeCardDTO[]>([]);
  constructor() {
  }

  ngOnInit() {
    this.auth.ensureCurrentUserLoaded();
    this.loadRecipes();
    this.loadUserCollections();
  }


  private loadRecipes(): void {
    this.recipeService.getAllActiveRecipes().subscribe({
      next: (recipes) => {
        this.recipes.set(recipes ?? []);
      },
      error: (error) => {
        console.error('Error cargando recetas', error);
        this.recipes.set([]);
      }
    });
  }

  private loadUserCollections(): void {
    if (!this.user?.id) return;

    this.collectionService
      .getAllFavoriteCollections(this.user.id)
      .subscribe();
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
    this.clientService.updatePreferences(prefs).subscribe();
  }

}
