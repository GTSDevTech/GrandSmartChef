import {Component, effect, inject, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  IonButton,
  IonCol,
  IonContent,
  IonDatetime, IonDatetimeButton,
  IonGrid,
  IonIcon,
  IonImg, IonInput,
  IonItem,
  IonLabel, IonModal,
  IonRow
} from '@ionic/angular/standalone';
import {HeaderComponent} from "../../components/headers/main-header/header.component";
import {FooterNavComponent} from "../../components/footer-nav/footer-nav.component";
import {ScrollFooterService} from "../../services/scroll/scroll-footer/scroll-footer.service";
import {HistoryCardComponent} from "../../components/cards/history-card/history-card.component";
import {DataHistoryUserComponent} from "../../components/data-history-user/data-history-user.component";
import {RouterModule} from "@angular/router";
import {HistoryService} from "../../services/history/history.service";
import {AuthService} from "../../services/auth/auth.service";
import {ModalService} from "../../services/modal/modal.service";
import {DateModalComponent} from "../../components/modals/date-modal/date-modal.component";

@Component({
  selector: 'app-history',
  templateUrl: './history.page.html',
  styleUrls: ['./history.page.scss'],
  standalone: true,
  imports: [IonContent, CommonModule,
    FormsModule, FooterNavComponent, IonCol, IonGrid, IonIcon, IonRow,
    HistoryCardComponent, RouterModule, IonButton, IonItem, IonLabel
  ]
})
export class HistoryPage implements OnInit {

  private modalService = inject(ModalService);
  private scrollFooter = inject(ScrollFooterService);
  private historyService = inject(HistoryService);
  private auth = inject(AuthService);

  histories = this.historyService.histories;

  selectedDate = this.today();
  selectedDateDisplay: string | null = null;


  constructor() {

    effect(() => {
      const iso = this.modalService.getData('date-modal')();
      if (!iso) return;

      const date = new Date(iso);

      this.selectedDateDisplay = date.toLocaleDateString('es-ES');
      this.selectedDate = date.toISOString().split('T')[0];

      const user = this.auth.getCurrentUser();
      if (user?.id) {
        this.historyService.loadHistoryByDate(user.id, this.selectedDate);
      }

      this.modalService.clearData('date-modal');
    });
  }


  ngOnInit() {
    const user = this.auth.getCurrentUser();
    if (!user?.id) return;

    this.historyService.loadHistoryByDate(user.id, this.selectedDate);

  }


  protected today(): string {
    return new Date().toISOString().split('T')[0];
  }

  onScroll(event: any) {
    this.scrollFooter.updateScroll(event.detail.scrollTop);
  }

  createHistory() {

  }

  openDateModal() {
    this.modalService.open('date-modal');
  }
}
