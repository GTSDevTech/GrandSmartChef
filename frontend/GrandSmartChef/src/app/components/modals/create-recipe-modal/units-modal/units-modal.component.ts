import {Component, computed, inject, OnInit, signal} from '@angular/core';
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
import {ModalService} from "../../../../services/modal/modal.service";
import {UnitDTO} from "../../../../models/unit.model";

@Component({
  selector: 'app-units-modal',
  templateUrl: './units-modal.component.html',
  styleUrls: ['./units-modal.component.scss'],
  imports: [
    IonModal,
    IonHeader,
    IonToolbar,
    IonLabel,
    IonSearchbar,
    IonContent,
    IonList,
    IonItem
  ]
})
export class UnitsModalComponent  implements OnInit {

  private modalService = inject(ModalService);

  isOpen = this.modalService.isOpen('unit-modal');

  units = signal<UnitDTO[]>([]);
  searchText = signal('');

  filteredUnits = computed(() => {
    const value = this.searchText().toLowerCase();
    return this.units().filter(u =>
      u.unit.toLowerCase().includes(value)
    );
  });

  ngOnInit(): void {

  }

  open(data: UnitDTO[]) {
    this.units.set(data);
    this.searchText.set('');
    this.modalService.open('unit-modal');
  }

  select(unit: UnitDTO) {
    this.modalService.close('unit-modal', unit.code);
  }

  close() {
    this.modalService.close('unit-modal');
  }

  onSearch(event: Event) {
    const value = (event.target as HTMLIonSearchbarElement).value || '';
    this.searchText.set(value);
  }

  formatUnitLabel(unit: string): string {
    const formatted = unit.toLowerCase().replace(/_/g, ' ');
    return formatted.charAt(0).toUpperCase() + formatted.slice(1);
  }


}
