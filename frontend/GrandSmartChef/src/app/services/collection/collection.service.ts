import {inject, Injectable, signal} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {environment} from "../../../environments/environment";
import {FavoriteCollectionDTO} from "../../models/collection.model";
import {Observable, tap} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CollectionService {

  private http = inject(HttpClient);
  private apiUrl = `${environment.apiUrl}/favorite-collections`;

  private _collections = signal<FavoriteCollectionDTO[]>([]);
  collections = this._collections.asReadonly();

  private loaded = false;


  loadCollections(clientId: number) {
    if (this.loaded) return;

    this.http.get<FavoriteCollectionDTO[]>(
      `${this.apiUrl}/collections`,
      { params: new HttpParams().set('clientId', clientId) }
    ).subscribe({
      next: data => {
        this._collections.set(data ?? []);
        this.loaded = true;
      },
      error: () => {
        this._collections.set([]);
        this.loaded = true;
      }
    });
  }

  getCollectionById(id: number): Observable<FavoriteCollectionDTO> {
    return this.http.get<FavoriteCollectionDTO>(`${this.apiUrl}/${id}`);
  }

  createCollection(dto: FavoriteCollectionDTO) {
    return this.http.post<FavoriteCollectionDTO>(
      `${this.apiUrl}/create`,
      dto
    ).pipe(
      tap(created => {
        this._collections.update(cols => [...cols, created]);
      })
    );
  }

  deleteCollection(id: number) {
    return this.http.delete<void>(
      `${this.apiUrl}/${id}`
    ).pipe(
      tap(() => {
        this._collections.update(cols =>
          cols.filter(c => c.id !== id)
        );
      })
    );
  }


  addRecipeToCollection(collectionId: number, recipeId: number) {
    return this.http.post<void>(
      `${this.apiUrl}/${collectionId}/add-recipe/${recipeId}`,
      {}
    ).pipe(
      tap(() => {
        this._collections.update(cols =>
          cols.map(c =>
            c.id !== collectionId
              ? c
              : {
                ...c,
                recipes: [...(c.recipes ?? []), { id: recipeId } as any]
              }
          )
        );
      })
    );
  }

  removeRecipeFromCollection(collectionId: number, recipeId: number) {
    return this.http.delete<void>(
      `${this.apiUrl}/${collectionId}/remove/${recipeId}`
    ).pipe(
      tap(() => {
        this._collections.update(cols =>
          cols.map(c =>
            c.id !== collectionId
              ? c
              : {
                ...c,
                recipes: c.recipes?.filter(r => r.id !== recipeId) ?? []
              }
          )
        );
      })
    );
  }

  countFavoriteCollectionsAvailables(clientId: number): Observable<number> {
    return this.http.get<number>(
      `${this.apiUrl}/count`,
      { params: new HttpParams().set('clientId', clientId) }
    );
  }

  countFavoriteRecipesAvailables(clientId: number): Observable<number> {
    return this.http.get<number>(
      `${this.apiUrl}/recipes/count`,
      { params: new HttpParams().set('clientId', clientId) }
    );
  }

  refreshCollection(id: number) {
    this.getCollectionById(id).subscribe({
      next: col => {
        this._collections.update(cols =>
          cols.map(c => c.id === id ? col : c)
        );
      },
      error: err => {
        console.error('Error refreshing collection', err);
      }
    });
  }


}
