import {Component, inject, OnInit} from '@angular/core';
import {IonButton, IonDatetime, IonModal} from "@ionic/angular/standalone";
import {ModalService} from "../../../services/modal/modal.service";

@Component({
  selector: 'app-date-modal',
  templateUrl: './date-modal.component.html',
  styleUrls: ['./date-modal.component.scss'],
  imports: [
    IonModal,
    IonDatetime,
    IonButton
  ]
})
export class DateModalComponent  implements OnInit {


  private modalService= inject(ModalService);
  isOpen = this.modalService.isOpen("date-modal");
  today = new Date().toISOString().split('T')[0];

  private selectedIso: string | null = null;


  ngOnInit(): void {

  }

  onDateChange(event: CustomEvent) {
    this.selectedIso = event.detail.value as string;
  }

  confirm() {
    if (this.selectedIso) {
      this.modalService.close('date-modal', this.selectedIso);
    } else {
      this.modalService.close('date-modal');
    }
  }

  close() {
    this.modalService.close('date-modal');
  }
}
