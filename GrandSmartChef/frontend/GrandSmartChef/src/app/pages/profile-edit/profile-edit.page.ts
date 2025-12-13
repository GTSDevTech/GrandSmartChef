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
  private  clientService = inject(ClientService);
  private cameraService = inject(CameraService);
  private initialFormValue: any;
  private hasChanges = false;

  selectedFile: File | null = null;
  previewUrl: string | null = null;

  @ViewChild('fileInput') fileInput!: ElementRef<HTMLInputElement>;


  unSaveFormChange(): boolean{
    const currentValue = this.formSignal().value;

    const formChanged = (Object.keys(currentValue) as (keyof typeof currentValue)[])
      .some(key => currentValue[key] !== this.initialFormValue);

    const photoChanged = this.previewUrl !== this.auth.getCurrentUserPhoto();

    return formChanged || photoChanged;
  }


  formSignal = signal(this.fb.group({
    fullName: ['', Validators.required],
    email: ['', [Validators.email, Validators.required]],
    birthdate: [''],
    country: ['']
  }));


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
      // fallback web: abrir input file
      this.fileInput.nativeElement.click();
    } else {
      // móvil: usar cámara o galería
      const file = await this.cameraService.pickImage();
      if (file) {
        this.selectedFile = file;
        const reader = new FileReader();
        reader.onload = () => this.previewUrl = reader.result as string;
        reader.readAsDataURL(file);
      }
    }
  }

  updateProfile(){
    const form: FormGroup = this.formSignal();
    if(form.invalid) return;

    const data = form.value;
    const formData = new FormData();
    Object.entries(data).forEach(([key, value]) => {
      const safeValue = value !== null && value !== undefined ? String(value) : '';
      formData.append(key, safeValue);
    });
    if(this.selectedFile){
      formData.append('photoProfile', this.selectedFile, this.selectedFile.name);
    }
    this.auth.registerStep2(formData).subscribe(() =>{
      this.clientService.getCurrentClient().subscribe(client =>{
        this.auth.setCurrentUser(client);
      })
    })


  }

  ngOnInit() {
    const user = this.auth.getCurrentUser();

    if (user) {
      this.formSignal().patchValue({
        fullName: user.fullName ?? '',
        email: user.email ?? '',
        birthdate: user.birthdate ?? '',
        country: user.country ?? ''
      });
      this.previewUrl = this.auth.getCurrentUserPhoto();
      this.initialFormValue = this.formSignal().value;
    } else {
      // Cargar desde backend si no está en memoria
      this.clientService.getCurrentClient().subscribe({
        next: (client) => {
          this.auth.setCurrentUser(client);
          this.formSignal().patchValue({
            fullName: client.fullName ?? '',
            email: client.email ?? '',
            birthdate: client.birthdate ?? '',
            country: client.country ?? ''
          });
          this.previewUrl = this.auth.getCurrentUserPhoto();
        },
        error: () => console.error('No se pudo obtener el cliente actual.')
      });
    }
  }
}
