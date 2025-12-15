import {inject, Injectable, signal} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {AuthService} from "../auth/auth.service";
import {environment} from "../../../environments/environment";
import {catchError, Observable, tap, throwError} from "rxjs";
import {FavoriteCollectionDTO} from "../../models/collection.model";
import {ToastController} from "@ionic/angular";

@Injectable({
  providedIn: 'root'
})
export class CollectionService {

  private http = inject(HttpClient);
  private auth = inject(AuthService);
  private toast = inject(ToastController);
  private apiUrl = `${environment.apiUrl}/favorite-collections`;
  collections = signal<FavoriteCollectionDTO[]>([]);

  getCollectionById(id: number): Observable<FavoriteCollectionDTO> {
    const token = this.auth.getToken();
    if (!token) return throwError(() => new Error('No authentication token'));

    const headers = new HttpHeaders({ Authorization: `Bearer ${token}`});
    const url = `${this.apiUrl}/${id}`;

    return this.http.get<FavoriteCollectionDTO>(url, { headers });
  }

  getAllFavoriteCollections(clientId:number): Observable<FavoriteCollectionDTO[]> {
    const token = this.auth.getToken();

    const params = new HttpParams().set('clientId', clientId);
    const headers = new HttpHeaders({ Authorization: `Bearer ${token}`});
    const url = `${this.apiUrl}/collections`;

    return this.http.get<FavoriteCollectionDTO[]>(url, { headers, params }).pipe(
      tap(data => this.collections.set(data)),
      catchError(err => {

        this.collections.set([]);
        return throwError(() => err);
      })
    );
  }

  createCollection(favoriteCollectionDTO: FavoriteCollectionDTO) {
    const token = this.auth.getToken();
    if (!token) return throwError(() => new Error('No authentication token'));

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    return this.http.post<FavoriteCollectionDTO>(
      `${this.apiUrl}/create`,
      favoriteCollectionDTO,
      { headers }
    );
  }

  deleteCollection(id: number) {
    const token = this.auth.getToken();
    if (!token) return throwError(() => new Error('No authentication token'));

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    return this.http.delete<void>(
      `${this.apiUrl}/${id}`,
      { headers }
    );

  }

  addRecipeToCollection(collectionId: number, recipeId: number) {
    const token = this.auth.getToken();
    if (!token) return throwError(() => new Error('No authentication token'));

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    const body = {collectionId,  recipeId };

    return this.http.post<void>(
      `${this.apiUrl}/${collectionId}/add-recipe/${recipeId}`,
      body,
      { headers }
    );
  }

  removeRecipeFromCollection(collectionId: number, recipeId: number) {
    const token = this.auth.getToken();
    if (!token) return throwError(() => new Error('No authentication token'));

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    const options = {
      headers,
      body: {collectionId, recipeId }
    };

    return this.http.delete<void>(
      `${this.apiUrl}/${collectionId}/remove-recipe/${recipeId}`,
      options
    );
  }





}
