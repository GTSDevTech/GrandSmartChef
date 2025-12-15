import {Component, inject, OnInit, signal} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  IonButton,
  IonCol,
  IonContent,
  IonIcon,
  IonRow,
} from '@ionic/angular/standalone';

import {HeaderComponent} from "../../../components/headers/main-header/header.component";
import {Router} from "@angular/router";
import {ScrollFooterService} from "../../../services/scroll/scroll-footer/scroll-footer.service";
import {RecipeCardDTO} from "../../../models/recipe-card.model";
import {CreateRecipe} from "../../../services/createRecipe/create-recipe";
import {CreateRecipeCardPage} from "../create-recipe-card/create-recipe-card.page";

@Component({
  selector: 'app-recipe-home',
  templateUrl: './recipe-home.page.html',
  styleUrls: ['./recipe-home.page.scss'],
  standalone: true,
  imports: [IonContent, CommonModule, FormsModule, HeaderComponent, IonButton, IonCol, IonIcon, IonRow, CreateRecipeCardPage]
})
export class RecipeHomePage implements OnInit {
  private scrollFooter = inject(ScrollFooterService);
  private createRecipeService = inject(CreateRecipe);
  private routes = inject(Router);
  recipesEdtiable = signal<RecipeCardDTO[]>([]);
  constructor() {
  }

  ngOnInit(): void {
    this.loadRecipes();
  }

  private loadRecipes(): void {
    this.createRecipeService.getAllRecipes().subscribe({
      next: (recipes) => {
        this.recipesEdtiable.set(recipes ?? []);
      },
      error: (err) => {
        console.error('Error cargando recetas', err);
        this.recipesEdtiable.set([]);
      }
    });
  }

  onScroll(event?: CustomEvent): void {
    const scrollTop = (event?.detail as any)?.scrollTop;
    if (scrollTop != null) {
      this.scrollFooter.updateScroll(scrollTop);
    }
  }

  goToRecipeForm(): void {
    this.routes.navigate(['/recipe-form']);
  }
}
