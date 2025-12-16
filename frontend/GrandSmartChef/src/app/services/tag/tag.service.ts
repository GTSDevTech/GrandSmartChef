import {inject, Injectable, signal} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AuthService} from "../auth/auth.service";
import {environment} from "../../../environments/environment";
import {TagDTO} from "../../models/tag.model";
import {Observable, throwError} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class TagService {

  private auth = inject(AuthService);
  private httpClient = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/tag`;


  tags = signal<TagDTO[]>([]);

  getAllTags(): Observable<TagDTO[]> {
    const token = this.auth.getToken();

    if (!token) {
      return throwError(() => new Error('No authentication token'));
    }

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    return this.httpClient.get<TagDTO[]>(`${this.apiUrl}/all`, { headers });
  }

}
