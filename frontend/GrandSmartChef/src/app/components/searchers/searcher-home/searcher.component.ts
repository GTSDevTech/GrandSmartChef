import { Component, OnInit } from '@angular/core';
import {IonSearchbar} from "@ionic/angular/standalone";

@Component({
  selector: 'app-searcher',
  templateUrl: './searcher.component.html',
  styleUrls: ['./searcher.component.scss'],
  standalone: true,
  imports: [
    IonSearchbar
  ]
})
export class SearcherComponent  implements OnInit {

  constructor() { }

  ngOnInit() {}

}
