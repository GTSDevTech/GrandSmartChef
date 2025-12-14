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
export class LoginPage implements OnInit {

  private auth = inject(AuthService);
  private router = inject(Router);
  private clientService = inject(ClientService);

  onLogin(formData: { username: string; password: string; redirectToCreate: boolean }) {
    this.auth.login(formData.username, formData.password).subscribe({
      next: (res) => {
        this.auth.setToken(res.token);  // Guarda el token
        this.clientService.getCurrentClient().subscribe(client => {
          this.auth.setCurrentUser(client);  // Guarda el usuario autenticado

          // Si el usuario quiere ir al home de creación de recetas, redirige allí
          if (formData.redirectToCreate) {
            this.router.navigate([`/recipe-home`]);  // Redirige a la página de crear recetas
          } else {
            this.router.navigate([`/home`]);  // Redirige al home normal
          }
        });
      },
      error: () => alert("Invalid Credentials")  // Muestra un error si no se puede autenticar
    });
  }


  ngOnInit() {


  }

}
