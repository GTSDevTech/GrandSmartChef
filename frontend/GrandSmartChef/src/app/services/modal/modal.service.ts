import {computed, Injectable, signal} from '@angular/core';
import {FavoriteCollectionDTO} from "../../models/collection.model";

@Injectable({
  providedIn: 'root'
})
export class ModalService {
  modals = signal<Record<string, boolean>>({});
  data = signal<Record<string, any>>({});

  open(modalId: string, data?: any) {
    this.modals.update(state => ({ ...state, [modalId]: true }));
    if (data) {
      this.data.update(state => ({ ...state, [modalId]: data }));
    }
  }

  close(modalId: string, data?: any) {
    this.modals.update(state => ({ ...state, [modalId]: false }));
    if (data) {
      this.data.update(state => ({ ...state, [modalId]: data }));
    }
  }

  isOpen(modalId: string) {
    return computed(() => this.modals()[modalId] ?? false);
  }

  getData(modalId: string) {
    return computed(() => this.data()[modalId] ?? null);
  }

  clearData(modalId: string) {
    this.data.update(state => {
      const newState = { ...state };
      delete newState[modalId];
      return newState;
    });
  }


}
