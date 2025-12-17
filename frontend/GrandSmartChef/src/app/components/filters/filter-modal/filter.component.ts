import {Component, inject, OnInit, signal} from '@angular/core';
import {
  IonButton, IonCol,
  IonIcon,
  IonItem,
  IonLabel,
  IonModal,
  IonRow,
  IonToggle
} from "@ionic/angular/standalone";
import {ModalService} from "../../../services/modal/modal.service";

@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.scss'],
  imports: [
    IonButton,
    IonModal,
    IonIcon,
    IonRow,
    IonCol,
    IonLabel,
    IonItem,
    IonToggle,
  ]
})
export class FilterComponent  implements OnInit {
  private modalService = inject(ModalService);

  isOpen = this.modalService.isOpen('recipe-filter');
  constructor() { }

  ngOnInit() {}

  onModalDismiss() {
    this.modalService.close('recipe-filter');
  }

  onClose(){
    this.modalService.close('recipe-filter');
  }

}
