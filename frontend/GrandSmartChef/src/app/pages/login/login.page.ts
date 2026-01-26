import {Component, inject, OnInit, signal} from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {Router, RouterModule} from '@angular/router';
import { SessionFormComponent } from 'src/app/components/input-forms/session-form/session-form.component';
import {IonButton, IonContent, IonRow} from "@ionic/angular/standalone";
import {IonImg} from "@ionic/angular/standalone";
import {AuthService} from "../../services/auth/auth.service";
import {ClientService} from "../../services/client/client.service";

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
    SessionFormComponent,
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
