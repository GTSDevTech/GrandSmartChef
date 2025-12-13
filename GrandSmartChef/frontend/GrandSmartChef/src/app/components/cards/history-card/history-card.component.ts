import { Component, OnInit } from '@angular/core';
import {IonCheckbox, IonIcon, IonImg, IonItem, IonItemDivider, IonLabel, IonList} from "@ionic/angular/standalone";

@Component({
  selector: 'app-history-card',
  templateUrl: './history-card.component.html',
  styleUrls: ['./history-card.component.scss'],
  imports: [
    IonList,
    IonItemDivider,
    IonLabel,
    IonItem,
    IonIcon,
    IonImg
  ]
})
export class HistoryCardComponent  implements OnInit {

  constructor() { }

  ngOnInit() {}

}
