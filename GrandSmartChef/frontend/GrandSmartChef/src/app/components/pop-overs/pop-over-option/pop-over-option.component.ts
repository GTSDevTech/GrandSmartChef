import {Component, effect, inject, OnInit, signal} from '@angular/core';
import {IonButton, IonCol, IonContent, IonIcon, IonImg, IonItem, IonList, IonPopover} from "@ionic/angular/standalone";
import {RouterLink} from "@angular/router";
import {AuthService} from "../../../services/auth/auth.service";

@Component({
  selector: 'app-pop-over-option',
  templateUrl: './pop-over-option.component.html',
  styleUrls: ['./pop-over-option.component.scss'],
  standalone:true,
  imports: [
    IonButton,
    IonCol,
    IonContent,
    IonImg,
    IonItem,
    IonList,
    IonPopover,
    IonIcon,
    RouterLink
  ]
})
export class PopOverOptionComponent  implements OnInit {

  protected auth = inject(AuthService);
  isPopoverOpen = false;
  popoverEvent: any;



  constructor() { }

  ngOnInit() {
  }

  openPopover(event: any) {
    this.popoverEvent = event;
    this.isPopoverOpen = true;
  }

  closePopover() {
    this.isPopoverOpen = false;
  }


  logOut(){
    this.auth.logOut();
    this.closePopover();
  }
}
