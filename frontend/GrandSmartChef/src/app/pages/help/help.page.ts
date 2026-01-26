import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {IonContent, IonRow} from '@ionic/angular/standalone';
import {SharedHeaderComponent} from "../../components/headers/shared-header/shared-header.component";
import {DescSharedCardComponent} from "../../components/option-panel/desc-shared-card/desc-shared-card.component";

@Component({
  selector: 'app-help',
  templateUrl: './help.page.html',
  styleUrls: ['./help.page.scss'],
  standalone: true,
  imports: [IonContent, CommonModule, FormsModule, SharedHeaderComponent, DescSharedCardComponent, IonRow]
})
export class HelpPage implements OnInit {

  constructor() { }

  ngOnInit() {
  }

}
