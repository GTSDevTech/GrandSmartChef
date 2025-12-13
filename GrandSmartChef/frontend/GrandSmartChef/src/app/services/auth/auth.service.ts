import {inject, Injectable, signal} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from "@angular/common/http";
import {environment} from "../../../environments/environment.prod";
import {tap} from "rxjs";
import {ClientDTO} from "../../models/client.model";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly backendUrl = `${environment.imageBaseUrl}`;
  private readonly apiUrl = `${environment.apiUrl}/auth`;

  token = signal<string | null>(localStorage.getItem("token"));
  currentUser = signal<ClientDTO | null>( JSON.parse(localStorage.getItem("user") || "null"));
  private http = inject(HttpClient);
  private router = inject(Router);


  getToken(): string | null {
    return this.token();
  }

  setToken(token: string) {
    localStorage.setItem("token", token);
    this.token.set(token);
  }

  setCurrentUser(user:ClientDTO){
    this.currentUser.set(user);
    localStorage.setItem("user", JSON.stringify(user));
  }

  getCurrentUser(){
    return this.currentUser();
  }


  login(username:string, password:string){

    return this.http.post<{ token: string, message: string }>(
      `${this.apiUrl}/login`,
      { username, password }
    );
  }

  registerStep1(username:string, email: string, password:string){
    const body = {username, email, password}
    return this.http.post<{ token: string, message: string}>(
      `${this.apiUrl}/register-step1`,
      body
    );

  }

  registerStep2(formData: FormData){
    const token = localStorage.getItem("token");
    if (!token) throw new Error('No authentication token found');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
    });
    return this.http.post<{ token: string, message: string}>(
      `${this.apiUrl}/register-step2`,
      formData,
      {headers}
    );
  }

  getCurrentUserPhoto(): string {
    const user = this.currentUser();
    if (!user || !user.photoProfile) {
      return '/assets/images/users/default_profile_image.png';
    }
    return `${this.backendUrl}${user.photoProfile}`;
  }



  logOut(){
    this.token.set(null);
    this.currentUser.set(null);
    localStorage.removeItem("token");
    localStorage.removeItem("user");
    this.router.navigate(['/login']);

  }

  isLoggedIn(){
    return !!this.token();
  }
}
