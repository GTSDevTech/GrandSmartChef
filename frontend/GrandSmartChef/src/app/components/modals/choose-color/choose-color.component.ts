import {Component, OnInit, output} from '@angular/core';
import {IonButton, IonCol, IonGrid, IonLabel, IonRow} from "@ionic/angular/standalone";

@Component({
    selector: 'app-choose-color',
    templateUrl: './choose-color.component.html',
    styleUrls: ['./choose-color.component.scss'],
    imports: [
        IonButton,
        IonCol,
        IonGrid,
        IonLabel,
        IonRow
    ]
})
export class ChooseColorComponent  implements OnInit {

  colorSelected = output<string>();
  selectedColor: string = '';
  colorPalette = [
    ['#C62828', '#C66A1A', '#C99A1A', '#C9C41A', '#2F4A3B'],
    ['#5F7A6A', '#6FA8D6', '#1F6FB8', '#6A2FB8', '#9A2FB8'],
    ['#3B3799', '#3E55B8', '#C75A9E', '#8E8A8E', '#000000']
  ];
  constructor() { }

  ngOnInit() {}




  selectColor(color: string) {
    this.selectedColor = color;
    this.colorSelected.emit(color);
  }
}
