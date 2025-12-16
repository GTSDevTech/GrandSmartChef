import { inject, Injectable, signal } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { environment } from '../../../environments/environment.prod';
import { ClientDTO } from '../../models/client.model';
import { ClientLoginDTO } from '../../models/client-login.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private readonly backendUrl = environment.imageBaseUrl;
  private readonly authApiUrl = `${environment.apiUrl}/auth`;
  private readonly clientApiUrl = `${environment.apiUrl}/clients`;

  private http = inject(HttpClient);
  private router = inject(Router);

  token = signal<string | null>(
    localStorage.getItem('token')
  );


  currentUser = signal<ClientDTO | null>(
    JSON.parse(localStorage.getItem('user') || 'null')
  );


  getToken(): string | null {
    return this.token();
  }

  setToken(token: string) {
    this.token.set(token);
    localStorage.setItem('token', token);
  }

  login(username: string, password: string) {
    return this.http.post<{ token: string }>(
      `${this.authApiUrl}/login`,
      { username, password }
    );
  }

  registerStep1(username: string, email: string, password: string) {
    return this.http.post<{ token: string }>(
      `${this.authApiUrl}/register-step1`,
      { username, email, password }
    );
  }

  registerStep2(formData: FormData) {
    const token = this.getToken();
    if (!token) {
      throw new Error('No authentication token found');
    }

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });

    return this.http.put<ClientDTO>(
      `${this.authApiUrl}/register-step2`,
      formData,
      { headers }
    );
  }


  getAuthUser() {
    return this.http.get<ClientLoginDTO>(
      `${this.clientApiUrl}/me`
    );
  }

  updatePreferences(prefs: { id: number; name: string }[]) {
    return this.http.patch<ClientDTO>(
      `${this.clientApiUrl}/profile/preferences`,
      prefs
    );
  }


  ensureCurrentUserLoaded() {
    if (this.currentUser()) return;

    this.getAuthUser().subscribe({
      next: () => {
        // Usa el endpoint real que ya tengas para perfil completo
        this.http.get<ClientDTO>(`${this.clientApiUrl}/profile`)
          .subscribe({
            next: client => this.setCurrentUser(client),
            error: () => this.logOut()
          });
      },
      error: () => this.logOut()
    });
  }

  setCurrentUser(user: ClientDTO) {
    this.currentUser.set(user);
    localStorage.setItem('user', JSON.stringify(user));
  }

  getCurrentUser() {
    return this.currentUser();
  }

  getCurrentUserPhoto(): string {
    const user = this.currentUser();
    if (!user || !user.photoProfile) {
      return '/assets/images/users/default_profile_image.png';
    }
    return `${this.backendUrl}${user.photoProfile}`;
  }


  logOut() {
    this.token.set(null);
    this.currentUser.set(null);
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    this.router.navigate(['/login']);
  }

  isLoggedIn(): boolean {
    return !!this.token();
  }
}
