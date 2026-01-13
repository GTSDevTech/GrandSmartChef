import {Component, effect, inject, input, OnInit, output, signal} from '@angular/core';
import {
  IonButton, IonCol,
  IonIcon,
  IonItem,
  IonLabel,
  IonModal,
  IonRow,
  IonToggle
} from "@ionic/angular/standalone";
import {ModalService} from "../../../services/modal/modal.service";
import {PreferenceDTO} from "../../../models/preference.model";
import {USER_OPTIONS} from "../../../models/Enums/userOptions";

@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.scss'],
  imports: [
    IonModal,
    IonIcon,
    IonRow,
    IonCol,
    IonLabel,
    IonItem,
    IonToggle,
  ]
})
export class FilterComponent  implements OnInit {
  private modalService = inject(ModalService);
  isOpen = this.modalService.isOpen('recipe-filter');
  initialPreferences = input<PreferenceDTO[] | null>(null);


  readonly options = USER_OPTIONS;
  private selectedIds = signal<Set<number>>(new Set());
  preferencesChange = output<PreferenceDTO[]>();

  constructor() {
    effect(() => {
      const prefs = this.initialPreferences() ?? [];
      this.selectedIds.set(new Set(prefs.map(p => p.id)));
    });
  }



  ngOnInit() {

  }

  onModalDismiss() {
    this.modalService.close('recipe-filter');
  }

  onClose(){
    this.modalService.close('recipe-filter');
  }

  isSelected(id: number): boolean {
    return this.selectedIds().has(id);
  }

  onToggle(id: number, checked: boolean): void {
    const next = new Set(this.selectedIds());
    checked ? next.add(id) : next.delete(id);
    this.selectedIds.set(next);

    const prefs: PreferenceDTO[] = this.options
      .filter(o => next.has(o.id))
      .map(o => ({ id: o.id, name: o.name }));

    this.preferencesChange.emit(prefs);
  }

}
