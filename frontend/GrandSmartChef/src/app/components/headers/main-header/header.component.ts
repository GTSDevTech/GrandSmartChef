import { Component, OnInit } from '@angular/core';
import {RouterModule} from "@angular/router";
import {CommonModule} from "@angular/common";
import {
  IonCol,
  IonGrid,
  IonHeader,
  IonImg,
} from "@ionic/angular/standalone";
import {PopOverOptionComponent} from "../../pop-overs/pop-over-option/pop-over-option.component";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
  standalone: true,
  imports: [RouterModule, CommonModule, IonHeader, IonGrid, IonCol, IonImg, PopOverOptionComponent]
})
export class HeaderComponent  implements OnInit {

  constructor() { }

  ngOnInit() {

  }

}
