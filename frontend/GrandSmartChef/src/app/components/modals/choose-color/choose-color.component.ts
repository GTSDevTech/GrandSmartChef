import {Component, inject, input, Input, OnInit, output, signal} from '@angular/core';
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
    ['#FF0000', '#EF7C14', '#FFBB00', '#FFF600', '#2B523C'],
    ['#6C8C79', '#7FC1FE', '#0082FD', '#8800FF', '#D400FF'],
    ['#3F37C9', '#4361EE', '#FF68C3', '#989298', '#000000']
  ];
  constructor() { }

  ngOnInit() {}




  selectColor(color: string) {
    this.selectedColor = color;
    this.colorSelected.emit(color);
  }
}
