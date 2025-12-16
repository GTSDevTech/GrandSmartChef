import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {ClientDTO} from "../../models/client.model";
import {AuthService} from "../auth/auth.service";
import {throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  private http = inject(HttpClient);
  private auth = inject(AuthService);
  private apiUrl = `${environment.apiUrl}/clients`;

  getCurrentClient(){
    const token = this.auth.getToken();
    if (!token) {
      console.error('No hay token disponible');
      return throwError(() => new Error('No authentication token'));
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    const url = `${this.apiUrl}/profile`;

    return this.http.get<ClientDTO>(url, { headers });

  }

  updatePreferences(prefs: { id: number; name: string }[]) {
    const token = this.auth.getToken();
    if (!token) {
      console.error('No hay token disponible');
      return throwError(() => new Error('No authentication token'));
    }

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    return this.http.patch<ClientDTO>(
      `${this.apiUrl}/preferences`,
      { preferences: prefs },
      { headers }
    );
  }


}
