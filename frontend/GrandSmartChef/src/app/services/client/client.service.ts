import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {ClientDTO} from "../../models/client.model";
import {AuthService} from "../auth/auth.service";
import {tap, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/clients`;
  private auth = inject(AuthService);

  getCurrentClient() {
    return this.http.get<ClientDTO>(`${this.apiUrl}/profile`);
  }

  updatePreferences(prefs: { id: number; name: string }[]) {
    return this.http.patch<ClientDTO>(
      `${this.apiUrl}/preferences`,
      { preferences: prefs }
    ).pipe(
      tap(updatedUser => {
        this.auth.setCurrentUser(updatedUser);
      })
    );
  }
}
