import {Component, inject, OnInit, output} from '@angular/core';
import {IonGrid, IonIcon, IonRow} from "@ionic/angular/standalone";

import {Location} from "@angular/common";


@Component({
    selector: '   app-back-header',
    templateUrl: './back-header.component.html',
    styleUrls: ['./back-header.component.scss'],
  imports: [
    IonGrid,
    IonRow,
    IonIcon
  ]
})
export class BackHeaderComponent  implements OnInit {
  backMenu = output<void>();
  private location = inject(Location);
  constructor() { }

  ngOnInit() {}

  onBack() {
    this.location.back();
    this.backMenu.emit();

  }
}
