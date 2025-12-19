import {
  AfterViewInit,
  Component,
  computed,
  effect,
  inject,
  input,
  OnInit,
  output,
  signal,
  ViewChild
} from '@angular/core';
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
import {RatingService} from "../../../services/rating/rating.service";
import {AuthService} from "../../../services/auth/auth.service";
import {ToastController} from "@ionic/angular";
import {ToastType} from "../../../models/Enums/ToastType";
import {ToastService} from "../../../services/toast/toast-manager.service";
import {error} from "@angular/compiler-cli/src/transformers/util";

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
  private ratingService = inject(RatingService);
  private auth = inject(AuthService);
  private toast = inject(ToastService);

  hoverRating = signal(0);
  readonly maxStars = 5;
  isOpen = this.modalService.isOpen('rate');

  rating = signal(0);
  showComment = signal(false);
  comment = signal('');
  recipeId = input<number>();
  ratingSubmitted = output<void>();

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

  submitRating() {
    const user = this.auth.currentUser();
    const recipeId = this.recipeId();
    console.log(user, recipeId)
    if (!user || recipeId === undefined) {

      this.toast.show(ToastType.ERROR, 'Error, No se pudo valorar la receta');
      return;
    }

    if (this.rating() === 0 && this.comment() === '') {
      this.toast.show(ToastType.ERROR, 'Debes indicar una puntuación o comentario');
      return;
    }

    const body = {

      recipeId: recipeId,
      clientId: user.id,
      rating: this.rating(),
      review: this.comment()
    };
    console.log(body)
    this.ratingService.ratingRecipe(body).subscribe({
      next: () => {

        this.toast.show(ToastType.RECIPE_RATED);
        this.ratingSubmitted.emit();
        this.modalService.close('rate');

      },
      error: () => {
        console.log(body)
        this.toast.show(ToastType.ERROR, 'No se pudo valorar la receta');
      }
    });
  }




}
