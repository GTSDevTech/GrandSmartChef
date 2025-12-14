import {Component, ElementRef, inject, OnInit, signal, ViewChild} from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {
  IonButton,
  IonContent, IonFab, IonFabButton,
  IonIcon, IonImg, IonInput, IonItem,
  IonLabel,
  IonRow,

} from '@ionic/angular/standalone';

import {BackHeaderComponent} from "../../components/headers/back-header/back-header.component";
import {AuthService} from "../../services/auth/auth.service";
import {ClientService} from "../../services/client/client.service";
import {CameraService} from "../../services/camera/camera.service";
import {Capacitor} from "@capacitor/core";
import {ToastController} from "@ionic/angular";

@Component({
  selector: 'app-profile-edit',
  templateUrl: './profile-edit.page.html',
  styleUrls: ['./profile-edit.page.scss'],
  standalone: true,
  imports: [IonContent, CommonModule, FormsModule,
    BackHeaderComponent, IonIcon, IonRow, IonLabel,
    IonButton, IonInput, IonItem, ReactiveFormsModule,
    IonFab, IonFabButton, IonImg]
})
export class ProfileEditPage implements OnInit {
  protected auth = inject(AuthService);
  private fb = inject(FormBuilder);
  private clientService = inject(ClientService);
  private cameraService = inject(CameraService);
  private toastCtrl = inject(ToastController);
  private initialFormValue: any;

  private hasChanges = false;

  selectedFile: File | null = null;
  previewUrl: string | null = null;

  @ViewChild('fileInput') fileInput!: ElementRef<HTMLInputElement>;




  formSignal = signal(
    this.fb.group({
      fullName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      birthdate: [''],
      country: ['']
    })
  );

  ngOnInit() {
    const user = this.auth.getCurrentUser();

    if (user) {
      this.loadFormFromUser(user);
    } else {
      this.clientService.getCurrentClient().subscribe({
        next: client => {
          this.auth.setCurrentUser(client);
          this.loadFormFromUser(client);
        },
        error: () => console.error('No se pudo cargar el usuario')
      });
    }
  }

  private loadFormFromUser(user: any) {
    this.formSignal().patchValue({
      fullName: user.fullName ?? '',
      email: user.email ?? '',
      birthdate: user.birthdate ?? '',
      country: user.country ?? ''
    });

    this.previewUrl = this.auth.getCurrentUserPhoto();
    this.initialFormValue = structuredClone(this.formSignal().value);
  }

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (!input.files?.length) return;

    this.selectedFile = input.files[0];

    const reader = new FileReader();
    reader.onload = () => {
      this.previewUrl = reader.result as string;
    };
    reader.readAsDataURL(this.selectedFile);
  }


  async pickImage() {
    if (Capacitor.getPlatform() === 'web') {
      this.fileInput.nativeElement.click();
    } else {
      const file = await this.cameraService.pickImage();
      if (file) {
        this.selectedFile = file;
        const reader = new FileReader();
        reader.onload = () => this.previewUrl = reader.result as string;
        reader.readAsDataURL(file);
      }
    }
  }

  updateProfile() {
    const form: FormGroup = this.formSignal();
    if (form.invalid) return;

    const data = form.value;
    const currentUser = this.auth.getCurrentUser();
    if (!currentUser) return;

    const isoBirthdate = data.birthdate
      ? data.birthdate.split('/').reverse().join('-')
      : currentUser.birthdate
        ? currentUser.birthdate.split('/').reverse().join('-')
        : null;

    const profileData = {
      fullName: data.fullName || currentUser.fullName,
      email: data.email || currentUser.email,
      country: data.country || currentUser.country,
      birthdate: isoBirthdate || currentUser.birthdate
    };

    const formData = new FormData();
    formData.append(
      'profile',
      new Blob([JSON.stringify(profileData)], { type: 'application/json' })
    );

    if (this.selectedFile) {
      formData.append('photoProfile', this.selectedFile, this.selectedFile.name);
    }

    this.auth.registerStep2(formData).subscribe({
      next: updatedClient => {
        this.auth.setCurrentUser(updatedClient);
        this.initialFormValue = structuredClone(this.formSignal().value);
        this.showSuccessToast();
      },
      error: (err) => {
        console.log('Error during update:');
        console.log('Error status:', err.status);
        console.log('Error statusText:', err.statusText);
        console.log('Error URL:', err.url);
        console.log('Error body:', err.error);
      }
    });
  }

  unSaveFormChange(): boolean{
    const currentValue = this.formSignal().value;

    const formChanged = (Object.keys(currentValue) as (keyof typeof currentValue)[])
      .some(key => currentValue[key] !== this.initialFormValue);

    const photoChanged = this.previewUrl !== this.auth.getCurrentUserPhoto();

    return formChanged || photoChanged;
  }



  async showSuccessToast() {
    const toast = await this.toastCtrl.create({
      message: 'Perfil actualizado correctamente',
      duration: 2000,
      position: 'bottom',
    });
    await toast.present();
  }
}
