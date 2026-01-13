import {inject, Injectable, signal} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {AuthService} from "../auth/auth.service";
import {environment} from "../../../environments/environment";
import {HistoryDTO} from "../../models/history.model";
import {Observable, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class HistoryService {
  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/history`;

  private _histories = signal<HistoryDTO[]>([]);
  histories = this._histories.asReadonly();

  private lastKey: string | null = null;

  loadHistoryByDate(clientId: number, date: string) {
    const key = `${clientId}_${date}`;
    if (this.lastKey === key) return;

    const params = new HttpParams()
      .set('clientId', clientId)
      .set('date', date);

    this.http.get<HistoryDTO[]>(
      `${this.apiUrl}/recipes/last7days`,
      { params }
    ).subscribe({
      next: data => {
        this._histories.set(data ?? []);
        this.lastKey = key;
      },
      error: () => {
        this._histories.set([]);
        this.lastKey = key;
      }
    });
  }

  saveCookedRecipe(history: HistoryDTO) {
    return this.http.post<HistoryDTO>(
      `${this.apiUrl}/create`,
      history
    );
  }

  countCookedRecipes(clientId: number) {
    return this.http.get<number>(
      `${this.apiUrl}/cooked/count`,
      { params: new HttpParams().set('clientId', clientId) }
    );
  }
}
