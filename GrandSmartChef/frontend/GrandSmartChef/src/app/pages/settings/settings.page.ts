import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  IonButton,
  IonCard, IonCardContent,
  IonCardHeader,
  IonCardSubtitle, IonCardTitle, IonCol,
  IonContent,
  IonHeader, IonIcon, IonItem, IonLabel, IonList,
  IonRow, IonSegment, IonThumbnail,
  IonTitle, IonToggle,
  IonToolbar
} from '@ionic/angular/standalone';
import {SharedHeaderComponent} from "../../components/headers/shared-header/shared-header.component";
import {DescSharedCardComponent} from "../../components/option-panel/desc-shared-card/desc-shared-card.component";

@Component({
  selector: 'app-settings',
  templateUrl: './settings.page.html',
  styleUrls: ['./settings.page.scss'],
  standalone: true,
  imports: [IonContent, IonHeader, IonTitle, IonToolbar, CommonModule, FormsModule, SharedHeaderComponent, DescSharedCardComponent, IonRow, IonCard, IonCardHeader, IonCardSubtitle, IonCardTitle, IonCardContent, IonList, IonItem, IonThumbnail, IonLabel, IonToggle, IonIcon, IonCol, IonButton, IonSegment]
})
export class SettingsPage implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
