import {Component, effect, inject, OnInit} from '@angular/core';
import {CommonModule, Location} from '@angular/common';
import { FormsModule } from '@angular/forms';
import {HeaderComponent} from "../../components/headers/main-header/header.component";
import {FooterNavComponent} from "../../components/footer-nav/footer-nav.component";
import {ScrollFooterService} from "../../services/scroll/scroll-footer/scroll-footer.service";
import {CollectionComponent} from "../../components/collection/collection.component";
import {RouterModule} from "@angular/router";
import {ModalService} from "../../services/modal/modal.service";
import {CollectionModalComponent} from "../../components/modals/collection-modal/collection-modal.component";
import {CollectionService} from "../../services/collection/collection.service";
import {AuthService} from "../../services/auth/auth.service";
import {IonButton, IonCol, IonContent, IonGrid, IonIcon, IonRow} from "@ionic/angular/standalone";
import {
  AddRecipeToCollectionModalComponent
} from "../../components/modals/add-recipe-to-collection-modal/add-recipe-to-collection-modal.component";

@Component({
  selector: 'app-favorites',
  templateUrl: './favorites.page.html',
  styleUrls: ['./favorites.page.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule, HeaderComponent, FooterNavComponent, CollectionComponent, RouterModule, CollectionModalComponent, IonContent, IonGrid, IonRow, IonCol, IonIcon, IonButton, AddRecipeToCollectionModalComponent]
})
export class FavoritesPage implements OnInit {
  private scrollFooter = inject(ScrollFooterService);
  private location = inject(Location);
  private collectionService = inject(CollectionService);
  private modalService = inject(ModalService);
  private auth = inject(AuthService);
  user = this.auth.getCurrentUser();
  collections = this.collectionService.collections;


  constructor() {
    effect(() => {
      const newCollection = this.modalService.getData('collection')();
      if (newCollection) {
        this.modalService.clearData('collection');
      }
    });

  }

  ngOnInit() {

    const user = this.auth.getCurrentUser();
    if (!user?.id) return;

    this.collectionService.loadCollections(user.id);
    console.log(this.collections());

  }

  openModal() {
    this.modalService.open('collection');
  }

  onCollectionDeleted(id: number) {
    if (!id) return;

    this.collectionService.deleteCollection(id).subscribe();
  }

  goToBack() {
    this.location.back();
  }

  onRecipesAdded(id: number) {
    this.collectionService.refreshCollection(id);

  }

  onScroll(event: any) {
    const scrollTop = (event?.detail as any)?.scrollTop;
    if (scrollTop != null) {
      this.scrollFooter.updateScroll(scrollTop);
    }
  }


}
