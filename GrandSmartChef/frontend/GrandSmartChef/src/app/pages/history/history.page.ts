import {Component, inject, OnInit} from '@angular/core';
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

@Component({
  selector: 'app-history',
  templateUrl: './history.page.html',
  styleUrls: ['./history.page.scss'],
  standalone: true,
  imports: [IonContent, CommonModule,
    FormsModule, HeaderComponent, FooterNavComponent, IonCol, IonGrid, IonIcon, IonRow,
    IonInput, HistoryCardComponent, DataHistoryUserComponent, RouterModule, IonButton, IonItem]
})
export class HistoryPage implements OnInit {

  private scrollFooter = inject(ScrollFooterService);
  constructor() { }

  ngOnInit() {
  }

  onScroll(event: any) {
    this.scrollFooter.updateScroll(event.detail.scrollTop);
  }

  createHistory() {

  }
}
