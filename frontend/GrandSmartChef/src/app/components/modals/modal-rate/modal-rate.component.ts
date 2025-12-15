import {AfterViewInit, Component, computed, effect, inject, OnInit, signal, ViewChild} from '@angular/core';
import {
  IonAvatar,
  IonButton,
  IonButtons, IonCol,
  IonContent, IonHeader, IonIcon, IonImg,
  IonItem, IonLabel,
  IonList,
  IonModal, IonRange, IonRow, IonTextarea,
  IonTitle,
  IonToolbar
} from "@ionic/angular/standalone";
import {ModalService} from "../../../services/modal/modal.service";

@Component({
  selector: 'app-modal-rate',
  templateUrl: './modal-rate.component.html',
  styleUrls: ['./modal-rate.component.scss'],
  imports: [
    IonButton,
    IonModal,
    IonContent,
    IonToolbar,
    IonTitle,
    IonButtons,
    IonIcon,
    IonLabel,
    IonRow,
    IonCol,
    IonTextarea
  ]
})
export class ModalRateComponent  implements OnInit {

  private modalService = inject(ModalService);
  rating = signal(0);
  hoverRating = signal(0);
  readonly maxStars = 5;
  isOpen = this.modalService.isOpen('rate');
  showComment = signal(false);
  comment = signal('');


  constructor() { }

  ngOnInit() {}

  stars = computed(() => {
    return Array.from({ length: this.maxStars }, (_, i) => {
      const value = this.hoverRating() || this.rating();
      return value >= i + 1 ? 'star' : 'star-outline';
    });
  });

  setRating(index: number) {
    this.rating.set(index + 1);
  }

  setHoverRating(index: number) {
    this.hoverRating.set(index + 1);
  }

  resetHoverRating() {
    this.hoverRating.set(0);
  }

  toggleComment() {
    this.showComment.set(!this.showComment());
  }

  close() {
    this.modalService.close('rate');
  }

  onModalDismiss() {
    this.modalService.close('rate');
  }
}
