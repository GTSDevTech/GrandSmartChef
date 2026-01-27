import {Component, effect, inject, OnInit, signal} from '@angular/core';
import {CommonModule, Location} from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  IonButton, IonCol,
  IonContent, IonGrid,
  IonIcon,
  IonImg, IonItem, IonLabel, IonList,
  IonRow, IonText,

} from '@ionic/angular/standalone';
import {RecipeDTO} from "../../models/recipe.model";
import {RecipeService} from "../../services/recipe/recipe.service";
import {ActivatedRoute} from "@angular/router";
import {ModalRateComponent} from "../../components/modals/modal-rate/modal-rate.component";
import {ModalService} from "../../services/modal/modal.service";
import {CollectionService} from "../../services/collection/collection.service";
import {AuthService} from "../../services/auth/auth.service";
import {CollectionModalComponent} from "../../components/modals/collection-modal/collection-modal.component";
import {ActionSheetController, ToastController} from "@ionic/angular";
import {FooterNavComponent} from "../../components/footer-nav/footer-nav.component";
import {ScrollFooterService} from "../../services/scroll/scroll-footer/scroll-footer.service";
import {ShoppingListService} from "../../services/shoppingList/shopping-list.service";
import {environment} from "../../../environments/environment.prod";


@Component({
  selector: 'app-main-recipe',
  templateUrl: './main-recipe.page.html',
  styleUrls: ['./main-recipe.page.scss'],
  standalone: true,
  imports: [IonContent, CommonModule, FormsModule,
    IonRow, IonButton, IonIcon,
    IonCol, IonGrid, IonList,
    IonItem, IonLabel, IonText,
    ModalRateComponent,
    CollectionModalComponent,
    FooterNavComponent, IonImg
  ]
})
export class MainRecipePage implements OnInit {
  private location = inject(Location);
  private auth = inject(AuthService);
  private route = inject(ActivatedRoute);
  private recipeService = inject(RecipeService);
  protected modalService = inject(ModalService);
  private collectionService = inject(CollectionService);
  private shoppingListService = inject(ShoppingListService);
  private actionSheetCtrl = inject(ActionSheetController);
  private toast = inject(ToastController);
  private scrollFooter = inject(ScrollFooterService);

  private readonly backendUrl = environment.imageBaseUrl;

  recipe = signal<RecipeDTO | null>(null);
  collections = this.collectionService.collections;
  user = this.auth.getCurrentUser();
  pendingRecipeToAdd = signal<RecipeDTO | null>(null);


  constructor() {
    effect(() => {
      const newCollection = this.modalService.getData('collection')();
      const recipe = this.pendingRecipeToAdd();

      if (newCollection && recipe) {
        this.addToFavorite(newCollection.id!, recipe);
        this.pendingRecipeToAdd.set(null);
        this.modalService.clearData('collection');
      }
    });
  }

  ngOnInit() {
    this.loadCollections();
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if(!isNaN(id)){
      this.recipeService.getActiveRecipeDetails(id).subscribe({
        next: (data) => this.recipe.set(data),
        error: (err) => console.error('Error cargando receta', err)
      });
    }else{
      console.error("Id de receta incorrecto");
    }



  }

  onClose() {
    this.location.back();
  }

  openModalRate() {
    this.modalService.open('rate');
  }




  addToFavorite(collectionId: number, recipe: RecipeDTO): void {
    this.collectionService.addRecipeToCollection(collectionId, recipe.id).subscribe({
      next: () => {
        this.toast.create({
          message: 'Receta añadida a favoritos',
          duration: 2000
        }).then(t => t.present());
      },
      error: () => {
        this.toast.create({
          message: 'Error al añadir a favoritos',
          duration: 2000
        }).then(t => t.present());
      }
    });
  }

  openModalCollection() {
    this.modalService.open('collection')
  }

  isInFavorites(): boolean {
    const recipeId =  this.recipe()?.id;
    if (!recipeId) return false;

    return this.collections().some(collection =>
      collection.recipes?.some(r => r.id === recipeId)
    );
  }

  private loadCollections() {
    if (this.user?.id) {
      this.collectionService.loadCollections(this.user.id);
    }

  }

  async openFavoriteSelector(recipe: RecipeDTO) {
    if (!this.user?.id) return;

    const recipeId = recipe.id;
    const isFavorite = this.isInFavorites();

    if (!isFavorite) {
      await this.openAddToCollection(recipe);
      return;
    }

    await this.openRemoveFromCollection(recipeId);
  }

  async openAddToCollection(recipe: RecipeDTO) {
    const collections = this.collections().filter(c => c.id);

    if (collections.length === 0) {
      this.pendingRecipeToAdd.set(recipe);
      this.openModalCollection();
      return;
    }

    const buttons = collections.map(c => ({
      text: c.title,
      handler: () => {
        if (c.id) this.addToFavorite(c.id, recipe);
      }
    }));

    buttons.push({
      text: 'Crear nueva colección',
      handler: () => {
        this.pendingRecipeToAdd.set(recipe);
        this.modalService.open('collection');
      }
    });

    buttons.push({ text: 'Cancelar',
      handler() {}
    });

    const actionSheet = await this.actionSheetCtrl.create({
      header: 'Añadir a colección',
      buttons
    });

    await actionSheet.present();
  }
    async openRemoveFromCollection(recipeId: number) {
      const collections = this.collections().filter((c) =>
        c.recipes?.some((r) => r.id === recipeId)
      );

      if (collections.length === 0) {
        this.toast
          .create({
            message: 'Esta receta no está en tus favoritos',
            duration: 2000,
          })
          .then((t) => t.present());
        return;
      }

      const buttons = collections.map((c) => ({
        text: `Eliminar de ${c.title}`,
        handler: () => this.removeFromCollection(c.id!, recipeId),
      }));

      buttons.push({
        text: 'Eliminar de todas',
        handler: () => {
          collections.forEach((c) =>
            this.removeFromCollection(c.id!, recipeId, false)
          );
          this.toast
            .create({
              message: 'Receta eliminada de todas las colecciones',
              duration: 2000,
            })
            .then((t) => t.present());
        },
      });

      buttons.push({
        text: 'Cancelar',
        handler() {}
      });

      const actionSheet = await this.actionSheetCtrl.create({
        header: 'Eliminar de colección',
        buttons,
      });

      await actionSheet.present();
    }

removeFromCollection(collectionId: number, recipeId: number, showToast = true) {
    console.log(collectionId, recipeId);
    this.collectionService.removeRecipeFromCollection(collectionId, recipeId).subscribe({
      next: () => {
        if (showToast) {
          this.toast.create({
            message: 'Receta eliminada de favoritos',
            duration: 2000
          }).then(t => t.present());
        }
      },
      error: () => {
        this.toast.create({
          message: 'Error al eliminar de favoritos',
          duration: 2000
        }).then(t => t.present());
      }
    });
  }


  addToCartList() {
    if(!this.user?.id || !this.recipe()?.id) return;
    const userId = this.user.id;
    const recipeId = this.recipe()!.id;

    this.shoppingListService.addRecipeToCart(userId, recipeId).subscribe({
      next: () => {
        this.toast.create({message: "Receta añadida correctamente a tu lista de Compras", duration: 2000})
          .then(t => t.present);
      },
      error: () => {
        this.toast.create({message: 'Error al añadir a la lista de compras', duration: 2000})
          .then(t => t.present());
      }
    });
  }

  onScroll(event?: CustomEvent) {
    const scrollTop = (event?.detail as any)?.scrollTop;
    if (scrollTop != null) {
      this.scrollFooter.updateScroll(scrollTop);
    }
  }


  getRecipeImage(imageUrl?: string | null): string {
    if (!imageUrl) {
      return '/assets/images/recipes/default_profile_image.png';
    }
    return `${this.backendUrl}${imageUrl}`;
  }

  onRatingSubmitted() {
    const id = this.recipe()?.id;
    if (!id) return;

    this.recipeService.getActiveRecipeDetails(id).subscribe({
      next: data => this.recipe.set(data),
      error: err => console.error('Error recargando receta', err)
    });
  }
}





