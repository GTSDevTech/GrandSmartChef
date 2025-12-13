import {Component, inject, OnInit} from '@angular/core';
import {CommonModule, Location} from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  IonCol,
  IonContent,
  IonGrid,
  IonHeader,
  IonIcon,
  IonImg,
  IonRow, IonText,
  IonTitle,
  IonToolbar
} from '@ionic/angular/standalone';
import {IonicModule} from "@ionic/angular";
import {RegisterFormComponent} from "../../components/input-forms/register-form/register-form.component";
import {Router, RouterLink} from "@angular/router";
import {AuthService} from "../../services/auth/auth.service";

@Component({
  selector: 'app-register',
  templateUrl: './register.page.html',
  styleUrls: ['./register.page.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule, RegisterFormComponent, IonCol, IonGrid, IonIcon, IonRow, IonImg, IonContent, RouterLink, IonText]
})
export class RegisterPage implements OnInit {
  private auth = inject(AuthService);
  private router = inject(Router);
  private location = inject(Location);
  constructor() { }

  ngOnInit() {
  }

  createClient(formData: { username: string; email: string; password: string }) {
    this.auth.registerStep1(formData.username, formData.email, formData.password)
      .subscribe({
        next: (response) => {
          this.auth.setToken(response.token);
          this.router.navigate(['/post-create-account']);
        },
        error: (err) => console.error("Error al registrar usuario")
      });
  }

  onClose() {
    this.location.back();
  }
}
