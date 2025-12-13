import {Component, EventEmitter, inject, OnInit, Output, signal} from '@angular/core';
import {Router, RouterModule, ROUTES} from '@angular/router';
import {
  IonButton,
  IonCheckbox,
  IonGrid,
  IonImg,
  IonInput,
  IonItem,
  IonLabel,
  IonRouterLink, IonRow
} from '@ionic/angular/standalone';
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-session-form',
  templateUrl: './session-form.component.html',
  styleUrls: ['./session-form.component.scss'],
  standalone: true,
  imports: [RouterModule, IonImg, IonItem, IonLabel, IonInput, IonCheckbox, IonButton, FormsModule, IonGrid, IonRow]
})
export class SessionFormComponent  implements OnInit {
  private routes = inject(Router)

  username = signal('');
  password = signal('');

  @Output() formSubmit = new EventEmitter<{username:string; password:string}>();

  submit(){
    if(this.username() && this.password()){
      this.formSubmit.emit({
        username: this.username(),
        password:this.password()
        });
    }else {
      console.warn('Rellene todos los campos')
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


  ngOnInit() {

  }

  goToCreateRecipe() {
    this.routes.navigate(['/recipe-home']);
  }
}
