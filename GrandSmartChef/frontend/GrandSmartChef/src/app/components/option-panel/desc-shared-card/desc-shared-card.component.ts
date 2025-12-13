import {Component, input, OnInit} from '@angular/core';
import {
  IonButton,
  IonCard,
  IonCardContent,
  IonCardHeader,
  IonCardSubtitle,
  IonCardTitle, IonCol, IonIcon, IonImg, IonItem, IonLabel, IonList, IonNote, IonRow, IonText, IonThumbnail
} from "@ionic/angular/standalone";

@Component({
    selector: 'app-desc-shared-card',
    templateUrl: './desc-shared-card.component.html',
    styleUrls: ['./desc-shared-card.component.scss'],
  imports: [
    IonCard,
    IonCardContent,
    IonCardHeader,
    IonCardSubtitle,
    IonCardTitle,
    IonLabel,
    IonText,
    IonRow,
    IonCol
  ]
})
export class DescSharedCardComponent  implements OnInit {
  bdColor = input<string>();
  label = input<string>();
  description = input<string>();
  constructor() { }

  ngOnInit() {}


}
