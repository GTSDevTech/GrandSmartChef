import { Component, OnInit } from '@angular/core';
import {IonCol, IonImg, IonLabel, IonRow} from "@ionic/angular/standalone";

@Component({
  selector: 'app-data-history-user',
  templateUrl: './data-history-user.component.html',
  styleUrls: ['./data-history-user.component.scss'],
  imports: [
    IonRow,
    IonCol,
    IonImg,
    IonLabel
  ]
})
export class DataHistoryUserComponent  implements OnInit {

  constructor() { }

  ngOnInit() {}

}
