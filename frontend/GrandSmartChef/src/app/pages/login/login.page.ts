import {Component, inject} from '@angular/core';
import { FormsModule } from '@angular/forms';
import {Router, RouterModule} from '@angular/router';
import {IonButton, IonContent, IonRow} from "@ionic/angular/standalone";
import {IonImg} from "@ionic/angular/standalone";
import {AuthService} from "../../services/auth/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
  standalone: true,
  imports: [
    IonContent,
    IonImg,
    FormsModule,
    RouterModule,
    IonButton,
    IonRow,
    ]
})
export class LoginPage {

  private auth = inject(AuthService);
  private router = inject(Router);

  onLogin(formData: {
    username: string;
    password: string;
    redirectToCreate: boolean;
  }) {
    this.auth.login(
      formData.username,
      formData.password
    ).subscribe({
      next: () => {
        this.router.navigate([
          formData.redirectToCreate ? '/recipe-home' : '/home'
        ]);
      },
      error: () => alert('Credenciales inválidas')
    });
  }
}
