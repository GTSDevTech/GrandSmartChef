import {Component, input, OnInit} from '@angular/core';
import {
  IonCard,
  IonCardContent,
  IonCardHeader,
  IonCardSubtitle,
  IonCardTitle,
  IonCol,
  IonLabel,
  IonText
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
