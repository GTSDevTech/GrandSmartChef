import { Component, EventEmitter, inject, OnInit, Output, signal } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { IonButton, IonCheckbox, IonGrid, IonImg, IonInput, IonItem, IonLabel, IonRow } from '@ionic/angular/standalone';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-session-form',
  templateUrl: './session-form.component.html',
  styleUrls: ['./session-form.component.scss'],
  standalone: true,
  imports: [RouterModule, IonImg, IonItem, IonLabel, IonInput, IonCheckbox, IonButton, FormsModule, IonGrid, IonRow]
})
export class SessionFormComponent implements OnInit {
  private routes = inject(Router);

  username = signal('');
  password = signal('');

  @Output() formSubmit = new EventEmitter<{ username: string; password: string; redirectToCreate: boolean }>();

  // Variable para saber si se quiere redirigir al home de creación de recetas
  redirectToCreate = false;

  submit() {
    if (this.username() && this.password()) {
      this.formSubmit.emit({
        username: this.username(),
        password: this.password(),
        redirectToCreate: this.redirectToCreate  // Le pasamos el valor de la redirección
      });
    } else {
      console.warn('Rellene todos los campos');
    }
  }

  onUsernameChange(event: any) {
    const value = event.detail?.value || '';
    this.username.set(value);
  }

  onPasswordChange(event: any) {
    const value = event.detail?.value || '';
    this.password.set(value);
  }


  onCreateRecipeRedirect() {
    this.redirectToCreate = true;  // Marcamos que la redirección es hacia el home de recetas
    this.submit();  // Hacemos el submit con la nueva redirección
  }

  ngOnInit() {}
}
