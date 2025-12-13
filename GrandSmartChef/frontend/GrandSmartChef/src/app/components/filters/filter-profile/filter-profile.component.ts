import { Component, OnInit } from '@angular/core';
import {IonItem, IonLabel, IonRow, IonText, IonToggle} from "@ionic/angular/standalone";

@Component({
    selector: 'app-filter-profile',
    templateUrl: './filter-profile.component.html',
    styleUrls: ['./filter-profile.component.scss'],
    standalone:true,
  imports: [
    IonItem,
    IonLabel,
    IonText,
    IonToggle,
    IonRow
  ]
})
export class FilterProfileComponent  implements OnInit {

  
  constructor() { }

  ngOnInit() {}

}
