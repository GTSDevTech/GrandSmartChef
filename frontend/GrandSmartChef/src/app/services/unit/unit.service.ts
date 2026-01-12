import {inject, Injectable} from '@angular/core';
import {UnitDTO} from "../../models/unit.model";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AuthService} from "../auth/auth.service";
import {environment} from "../../../environments/environment";
import {throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UnitService {

  private http = inject(HttpClient);
  private auth = inject(AuthService);
  private apiUrl = `${environment.apiUrl}/units`;


  getUnits() {
    const token = this.auth.getToken();
    if (!token) {
      return throwError(() => new Error('No authentication token'));
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<UnitDTO[]>(`${this.apiUrl}`, {headers});
  }


}
