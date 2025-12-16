import {Component, output} from '@angular/core';
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
export class SearcherComponent  {


  searchChange = output<string>();

  onSearch(event: Event) {
    const value = (event.target as HTMLIonSearchbarElement).value || '';
    this.searchChange.emit(value);
  }
}
