import {Component, inject, OnInit} from '@angular/core';
import {IonImg, IonRow} from "@ionic/angular/standalone";
import {AuthService} from "../../services/auth/auth.service";

@Component({
  selector: 'app-img-profile',
  templateUrl: './img-profile.component.html',
  styleUrls: ['./img-profile.component.scss'],
  imports: [
    IonRow,
    IonImg,
  ]
})
export class ImgProfileComponent  implements OnInit {
  protected auth = inject(AuthService);

  constructor() { }

  ngOnInit() {

  }

}
