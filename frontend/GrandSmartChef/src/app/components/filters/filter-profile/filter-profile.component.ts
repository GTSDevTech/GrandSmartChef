import {Component, effect, input, OnInit, output, signal} from '@angular/core';
import {
  IonButton, IonCol,
  IonContent,
  IonIcon,
  IonItem,
  IonLabel,
  IonList,
  IonModal,
  IonToggle
} from "@ionic/angular/standalone";
import {PreferenceDTO} from "../../../models/preference.model";
import {USER_OPTIONS} from "../../../models/Enums/userOptions";



@Component({
  selector: 'app-filter-profile',
  templateUrl: './filter-profile.component.html',
  styleUrls: ['./filter-profile.component.scss'],
  standalone: true,
  imports: [IonItem, IonLabel, IonToggle, IonIcon, IonModal, IonContent, IonList, IonButton, IonCol],
})
export class FilterProfileComponent implements OnInit {

  readonly options = USER_OPTIONS;
  initialPreferences = input<PreferenceDTO[] | null>(null);
  private selectedIds = signal<Set<number>>(new Set());
  preferencesChange = output<PreferenceDTO[]>();



  modalOpen = false;

  constructor() {
    effect(() => {
      const prefs = this.initialPreferences();
      if (!prefs) {
        return;
      }

      this.selectedIds.set(new Set(prefs.map(p => p.id)));
    });
  }

  ngOnInit(): void {}

  openPreferencesModal(): void {
    this.modalOpen = true;
  }

  isSelected(id: number): boolean {
    return this.selectedIds().has(id);
  }

  onToggle(id: number, checked: boolean): void {
    const next = new Set(this.selectedIds());

    if (checked) {
      next.add(id);
    } else {
      next.delete(id);
    }

    this.selectedIds.set(next);

    const prefs: PreferenceDTO[] = this.options
      .filter(o => next.has(o.id))
      .map(o => ({
        id: o.id,
        name: o.name,
      }));

    this.preferencesChange.emit(prefs);
  }

  selectedLabel(): string | null {
    const selected = this.options
      .filter(o => this.selectedIds().has(o.id))
      .map(o => o.title);

    if (selected.length === 0) {
      return null;
    }

    if (selected.length === 1) {
      return selected[0];
    }

    return `${selected.length} seleccionadas`;
  }
}
