import {Component, inject, Input, input, OnInit, output} from '@angular/core';
import {
  IonButton,
  IonCard,
  IonCardContent,
  IonCardHeader,
  IonCardSubtitle,
  IonFab,
  IonFabButton,
  IonFabList,
  IonIcon,
  IonImg,
  IonItem,
  IonLabel,
  IonList,
  IonNote,
  IonThumbnail
} from "@ionic/angular/standalone";
import {FavoriteCollectionDTO} from "../../models/collection.model";
import {AlertController} from "@ionic/angular";
import {CollectionService} from "../../services/collection/collection.service";

import {ModalService} from "../../services/modal/modal.service";
import {Router} from "@angular/router";
import {environment} from "../../../environments/environment.prod";


@Component({
  selector: 'app-collection',
  templateUrl: './collection.component.html',
  styleUrls: ['./collection.component.scss'],
  imports: [
    IonCard,
    IonCardHeader,
    IonCardContent,
    IonIcon,
    IonList,
    IonItem,
    IonLabel,
    IonCardSubtitle,
    IonThumbnail,
    IonImg,
    IonFabButton,
    IonFab,
    IonFabList,
    IonButton,

  ]
})
export class CollectionComponent  implements OnInit {
  private readonly backendUrl = environment.imageBaseUrl;
  private route = inject(Router);
  private alertController = inject(AlertController)
  private collectionService = inject(CollectionService);
  private modalService = inject(ModalService);
  collection = input.required<FavoriteCollectionDTO>();

  deleted = output<number>();

  constructor() { }

  ngOnInit() {
    console.log('COLLECTION INPUT:', this.collection());
  }

  async confirmDelete(){
    const alert = await this.alertController.create({
      header: 'Eliminar colección',
      message: '¿Seguro que quieres eliminar esta colección?',
      buttons: [
        {
          text: 'Cancelar',
          role: 'cancel',
          cssClass: 'secondary',
        },
        {
          text: 'Borrar',
          role: 'confirm',
          handler: () => {
            const id = this.collection().id;
            if (id) {
              this.deleted.emit(id);
            }
          }
        }
      ]
    });
    await alert.present();
  }



  addRecipe() {
    const id = this.collection().id;
    if (!id) return;
    this.modalService.open('add-recipe-to-collection',  id );

  }

  navToRecipe(recipeId:number) {
    this.route.navigate(['/main-recipe', recipeId]).then();
  }


  getRecipeImage(imageUrl?: string | null): string {
    if (!imageUrl) {
      return '/assets/images/recipes/default_profile_image.png';
    }
    console.log(`${this.backendUrl}${imageUrl}`);
    return `${this.backendUrl}${imageUrl}`;
  }

}
