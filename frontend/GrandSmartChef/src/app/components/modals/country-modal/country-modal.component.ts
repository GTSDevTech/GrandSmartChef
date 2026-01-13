import {Component, inject, OnInit} from '@angular/core';
import {COUNTRIES} from "../../../models/Enums/countries";
import {ModalService} from "../../../services/modal/modal.service";
import {
  IonContent,
  IonHeader,
  IonItem,
  IonLabel,
  IonList,
  IonModal,
  IonSearchbar,
  IonToolbar
} from "@ionic/angular/standalone";

@Component({
  selector: 'app-country-modal',
  templateUrl: './country-modal.component.html',
  styleUrls: ['./country-modal.component.scss'],
  imports: [
    IonModal,
    IonHeader,
    IonLabel,
    IonToolbar,
    IonSearchbar,
    IonContent,
    IonItem,
    IonList
  ]
})
export class CountryModalComponent  implements OnInit {

  private modalService = inject(ModalService);

  isOpen = this.modalService.isOpen('country-modal');

  countries = COUNTRIES;
  searchText = '';

  ngOnInit() {}


  get filteredCountries() {
      const v = this.searchText.toLowerCase();
      return this.countries.filter(c =>
        c.name.toLowerCase().includes(v)
      );
    }

  open() {
    this.searchText = '';
    this.modalService.open('country-modal');
  }

  select(country: { code: string; name: string }) {
    this.modalService.close('country-modal', country.name);
  }

  close() {
    this.modalService.close('country-modal');
  }

  onSearch(event: Event) {
    this.searchText = (event.target as HTMLIonSearchbarElement).value ?? '';
  }
}


