import {Component, inject, OnInit, signal} from '@angular/core';
import {
  IonButton,
  IonButtons,
  IonCol,
  IonContent,
  IonIcon,
  IonInput,
  IonModal,
  IonRow, IonTitle, IonToolbar
} from "@ionic/angular/standalone";
import {ModalService} from "../../../services/modal/modal.service";
import {ChooseColorComponent} from "../choose-color/choose-color.component";
import {AuthService} from "../../../services/auth/auth.service";
import {FavoriteCollectionDTO} from "../../../models/collection.model";
import {CollectionService} from "../../../services/collection/collection.service";

@Component({
    selector: 'app-collection-modal',
    templateUrl: './collection-modal.component.html',
    styleUrls: ['./collection-modal.component.scss'],
    standalone: true,
  imports: [
    IonButton,
    IonButtons,
    IonCol,
    IonContent,
    IonIcon,
    IonModal,
    IonRow,
    IonTitle,
    IonToolbar,
    IonInput,
    ChooseColorComponent
  ]
})
export class CollectionModalComponent  implements OnInit {

  private auth = inject(AuthService);
  private collectionService = inject(CollectionService);
  private modalService = inject(ModalService);
  isOpen = this.modalService.isOpen('collection');
  collectionTitle = signal<string>('');
  selectedColor = signal<string>('')


  constructor() {
  }

  ngOnInit() {
  }

  saveCollection() {
    const user = this.auth.getCurrentUser();
    if (!user?.id) return;
    const dto: FavoriteCollectionDTO = {
      title: this.collectionTitle(),
      color: this.selectedColor(),
      clientId: user.id
    };
    this.collectionService.createCollection(dto).subscribe({
      next: (newCollection) => {
        this.modalService.close('collection', newCollection);
        this.collectionTitle.set('');
      }
    });

  }

  onModalDismiss() {
    this.modalService.close('collection');
  }


  updateTitle(event: Event) {
    const input = event.target as HTMLInputElement;
    this.collectionTitle.set(input.value || '');
    console.log("titulo recibido: ", input.value)
  }

  onClose(){
    this.modalService.close('collection');
  }

}

