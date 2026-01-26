import {Component, computed, inject, OnInit, signal} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  IonButton,
  IonCol,
  IonContent,
  IonIcon,
  IonRow, IonSearchbar,
} from '@ionic/angular/standalone';

import {Router} from "@angular/router";
import {ScrollFooterService} from "../../../services/scroll/scroll-footer/scroll-footer.service";
import {RecipeCardDTO} from "../../../models/recipe-card.model";
import {CreateRecipe} from "../../../services/createRecipe/create-recipe";
import {CreateRecipeCardPage} from "../create-recipe-card/create-recipe-card.page";
import {AlertController, ToastController} from "@ionic/angular";



@Component({
  selector: 'app-recipe-home',
  templateUrl: './recipe-home.page.html',
  styleUrls: ['./recipe-home.page.scss'],
  standalone: true,
  imports: [IonContent, CommonModule, FormsModule, IonButton, IonCol, IonIcon, IonRow, CreateRecipeCardPage, IonSearchbar]
})
export class RecipeHomePage implements OnInit {
  private scrollFooter = inject(ScrollFooterService);
  private createRecipeService = inject(CreateRecipe);
  private routes = inject(Router);
  recipesEdtiable = signal<RecipeCardDTO[]>([]);
  searchRecipe = signal('');

  private alert = inject(AlertController);
  private toast = inject(ToastController);
  constructor() {
  }

  ngOnInit(): void {
    this.loadRecipes();

  }
  ionViewWillEnter(): void {
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

  deleteRecipe(id: number): void {
    this.createRecipeService.deleteRecipe(id).subscribe({
      next: () => {
        this.recipesEdtiable.update(recipes =>
          recipes.filter(r => r.id !== id)
        );

        this.showToast('Receta eliminada correctamente', 'success');
      },
      error: (err) => {
        console.error('Error eliminando receta', err);
        this.showToast('No se pudo eliminar la receta', 'danger');
      }
    });
  }

  async showToast(
    message: string,
    color: 'success' | 'danger' | 'warning' = 'success'
  ) {
    const toast = await this.toast.create({
      message,
      duration: 2500,
      position: 'bottom',
      color,
      buttons: [
        {
          text: 'OK',
          role: 'cancel'
        }
      ]
    });

    await toast.present();
  }

  async confirmDeleteRecipe(id: number) {
    const alert = await this.alert.create({
      header: 'Eliminar receta',
      message: '¿Estás seguro de que quieres eliminar esta receta? Esta acción no se puede deshacer.',
      buttons: [
        {
          text: 'Cancelar',
          role: 'cancel'
        },
        {
          text: 'Eliminar',
          role: 'destructive',
          handler: () => {
            this.deleteRecipe(id);
          }
        }
      ]
    });

    await alert.present();
  }


  filteredRecipes = computed(() => {
    const term = this.searchRecipe().toLowerCase().trim();

    if (!term) return this.recipesEdtiable();

    return this.recipesEdtiable().filter(recipe =>
      recipe.name.toLowerCase().includes(term)
    );
  });

  onSearch(event: any): void {
    this.searchRecipe.set(event.target.value || '');
  }


}
