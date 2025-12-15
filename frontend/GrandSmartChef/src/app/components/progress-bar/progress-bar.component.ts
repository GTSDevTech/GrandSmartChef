import {Component, inject, OnInit} from '@angular/core';
import {IonCol, IonItem, IonLabel, IonProgressBar, IonRow} from "@ionic/angular/standalone";
import {ShoppingProgressService} from "../../services/shopping-progress-bar/shopping-progress.service";

@Component({
    selector: 'app-progress-bar',
    templateUrl: './progress-bar.component.html',
    styleUrls: ['./progress-bar.component.scss'],
    imports: [
        IonCol,
        IonItem,
        IonLabel,
        IonProgressBar,
        IonRow
    ]
})
export class ProgressBarComponent  implements OnInit {


  private progressService = inject(ShoppingProgressService);

  progress = this.progressService.progress;
  totalItems = this.progressService.totalItems;
  boughtItems = this.progressService.boughtItems;

  constructor() { }

  ngOnInit() {}

}
