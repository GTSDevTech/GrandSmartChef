import {Component, EventEmitter, inject, OnInit, Output} from '@angular/core';
import {FormBuilder,FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {
  IonButton,
  IonIcon,
  IonInput,
  IonItem,
  IonRow
} from "@ionic/angular/standalone";

@Component({
    selector: 'app-register-form',
    templateUrl: './register-form.component.html',
    styleUrls: ['./register-form.component.scss'],
  standalone: true,
  imports: [
    FormsModule,
    IonButton,
    IonInput,
    IonItem,
    IonRow,
    ReactiveFormsModule,
    IonIcon,
  ]
})
export class RegisterFormComponent  implements OnInit {

  private fb = inject(FormBuilder);

  form = this.fb.group({
    username: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    rawPassword: ['', Validators.required],
    passwordRepeat: ['', Validators.required],
  });

  @Output() formSubmit = new EventEmitter<{username:string; email:string; password:string}>();

  constructor() { }

  ngOnInit() {}



  submit() {
   if(this.form.invalid) return;

   const data = this.form.value;
   if(data.rawPassword != data.passwordRepeat) return;
   this.formSubmit.emit({
      username: data.username!,
      email: data.email!,
      password: data.rawPassword!,
     });

  }


}
