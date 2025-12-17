import {Component, effect, input, OnInit, output, signal} from '@angular/core';
import {
  IonButton, IonCol,
  IonContent,
  IonIcon,
  IonItem,
  IonLabel,
  IonList,
  IonModal,
  IonRow,
  IonText,
  IonToggle
} from "@ionic/angular/standalone";

type PreferenceDTO = { id: number; name: string };

type PreferenceOption = {
  id: number;
  name: string;
  title: string;
  description: string;
};

@Component({
  selector: 'app-filter-profile',
  templateUrl: './filter-profile.component.html',
  styleUrls: ['./filter-profile.component.scss'],
  standalone: true,
  imports: [IonItem, IonLabel, IonToggle, IonIcon, IonModal, IonContent, IonList, IonButton, IonCol],
})
export class FilterProfileComponent implements OnInit {

  initialPreferences = input<PreferenceDTO[] | null>(null);
  private selectedIds = signal<Set<number>>(new Set());
  preferencesChange = output<PreferenceDTO[]>();

  readonly options = [
    { id: 1,  name: 'Vegetariano', title: 'Vegetariano', description: 'Evitar recetas con carne o pescado' },
    { id: 12, name: 'Sin Gluten',  title: 'Sin Gluten',  description: 'Evitar productos con gluten' },
    { id: 13, name: 'Rápida',      title: 'Rápidas',     description: 'Recetas rápidas < 30 min' },
    { id: 14, name: 'Económica',   title: 'Económicas',  description: 'Recetas baratas con coste < 10€' },
  ];


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
