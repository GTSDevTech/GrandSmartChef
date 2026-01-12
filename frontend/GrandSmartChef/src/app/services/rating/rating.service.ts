import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {AuthService} from "../auth/auth.service";
import {environment} from "../../../environments/environment";
import {throwError} from "rxjs";
import {RatingDTO} from "../../models/recipe-rating";

@Injectable({
  providedIn: 'root'
})
export class RatingService {

  private http = inject(HttpClient);
  private auth = inject(AuthService);
  private apiUrl = `${environment.apiUrl}/rating`;

  ratingRecipe(body: RatingDTO){
    const token = this.auth.getToken();
    if (!token) {
      return throwError(() => new Error('No authentication token'));
    }
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    const url = `${this.apiUrl}/vote`;
    return this.http.post<RatingDTO[]>(url,body, {headers});
  }


}
