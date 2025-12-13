import {Component, effect, inject, OnInit, signal} from '@angular/core';
import {CommonModule, Location} from '@angular/common';
import { FormsModule } from '@angular/forms';
import {HeaderComponent} from "../../components/headers/main-header/header.component";
import {FooterNavComponent} from "../../components/footer-nav/footer-nav.component";
import {IonicModule} from "@ionic/angular";
import {ScrollFooterService} from "../../services/scroll/scroll-footer/scroll-footer.service";
import {CollectionComponent} from "../../components/collection/collection.component";
import {Router, RouterModule} from "@angular/router";
import {ModalService} from "../../services/modal/modal.service";
import {CollectionModalComponent} from "../../components/modals/collection-modal/collection-modal.component";
import {CollectionService} from "../../services/collection/collection.service";
import {FavoriteCollectionDTO} from "../../models/collection.model";
import {AuthService} from "../../services/auth/auth.service";
import {IonButton, IonCol, IonContent, IonGrid, IonIcon, IonRow} from "@ionic/angular/standalone";
import {
  AddRecipeToCollectionSheetComponent
} from "../../components/modals/add-recipe-to-collection-sheet/add-recipe-to-collection-sheet.component";

@Component({
  selector: 'app-favorites',
  templateUrl: './favorites.page.html',
  styleUrls: ['./favorites.page.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule, HeaderComponent, FooterNavComponent, CollectionComponent, RouterModule, CollectionModalComponent, IonContent, IonGrid, IonRow, IonCol, IonIcon, IonButton, AddRecipeToCollectionSheetComponent]
})
export class FavoritesPage implements OnInit {
  private scrollFooter = inject(ScrollFooterService);
  private location = inject(Location);
  private collectionService = inject(CollectionService);
  private modalService = inject(ModalService);
  private auth = inject(AuthService);
  user = this.auth.getCurrentUser();
  collections = this.collectionService.collections;
  collectionModalData = this.modalService.getData('collection');





  constructor() {
    effect(() => {
      const newCollection = this.collectionModalData();
      if (newCollection) {
        this.collections.update(prev => [...prev, newCollection]);
        this.modalService.clearData('collection');
      }
    });


  }

  ngOnInit() {

    const user = this.auth.getCurrentUser();
    if(!user?.id) return;
    this.collectionService.getAllFavoriteCollections(user.id).subscribe();

  }

  openModal() {
    this.modalService.open('collection');
  }

  onCollectionDeleted(id: number) {
    if(!id) return;
    this.collections.update(prev => prev.filter(c => c.id !== id));

  }

  goToBack() {
    this.location.back();
  }

  onRecipesAdded(id: number) {
    this.collectionService.getCollectionById(id).subscribe( updatedCollection => {
      this.collections.update(prev => prev.map(c => c.id === updatedCollection.id ? updatedCollection : c));
      }
    );

  }

  onScroll(event: any) {
    const scrollTop = (event?.detail as any)?.scrollTop;
    if (scrollTop != null) {
      this.scrollFooter.updateScroll(scrollTop);
    }
  }


}
