import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  IonButton,
  IonCard, IonCardContent,
  IonCol,
  IonContent,
  IonIcon, IonItem, IonLabel, IonList,
  IonRow,
  IonToggle,
} from '@ionic/angular/standalone';
import {SharedHeaderComponent} from "../../components/headers/shared-header/shared-header.component";

@Component({
  selector: 'app-settings',
  templateUrl: './settings.page.html',
  styleUrls: ['./settings.page.scss'],
  standalone: true,
  imports: [IonContent,
    CommonModule, FormsModule,
    SharedHeaderComponent,
    IonRow, IonCard,
    IonCardContent, IonList, IonItem,
    IonLabel, IonToggle,
    IonIcon, IonCol, IonButton
  ]
})
export class SettingsPage implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
