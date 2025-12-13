import {Component, inject, OnInit, output} from '@angular/core';
import {IonCol, IonGrid, IonHeader, IonIcon, IonImg, IonLabel, IonRow} from "@ionic/angular/standalone";
import {PopOverOptionComponent} from "../../pop-overs/pop-over-option/pop-over-option.component";
import {outputFromObservable} from "@angular/core/rxjs-interop";
import {Location} from "@angular/common";
import {IngredientOptionsComponent} from "../../ingredient-panel/ingredient-options/ingredient-options.component";

@Component({
    selector: '   app-back-header',
    templateUrl: './back-header.component.html',
    styleUrls: ['./back-header.component.scss'],
  imports: [
    IonGrid,
    IonRow,
    IonIcon,
    IonLabel,
    IonCol,
    IonHeader,
    IngredientOptionsComponent,

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
