import {Component, effect, ElementRef, inject, OnInit, signal, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';

import {AuthService} from "../../services/auth/auth.service";
import {ClientService} from "../../services/client/client.service";
import {CameraService} from "../../services/camera/camera.service";
import {Capacitor} from "@capacitor/core";
import {ToastController} from "@ionic/angular";
import {ModalService} from "../../services/modal/modal.service";
import {CountryModalComponent} from "../../components/modals/country-modal/country-modal.component";
import {
  IonButton,
  IonContent,
  IonFab, IonFabButton,
  IonIcon,
  IonImg,
  IonInput,
  IonItem,
  IonLabel,
  IonRow
} from "@ionic/angular/standalone";
import {DateModalComponent} from "../../components/modals/date-modal/date-modal.component";
import {BackHeaderComponent} from "../../components/headers/back-header/back-header.component";


type ProfileFormValue = {
  fullName: string | null;
  email: string | null;
  birthdate: string | null;
  country: string | null;
};

@Component({
  selector: 'app-profile-edit',
  templateUrl: './profile-edit.page.html',
  styleUrls: ['./profile-edit.page.scss'],
  imports: [
    IonRow,
    IonButton,
    CountryModalComponent,
    DateModalComponent,
    IonItem,
    IonIcon,
    IonLabel,
    IonInput,
    IonFab,
    ReactiveFormsModule,
    IonImg,
    IonContent,
    BackHeaderComponent,
    IonFabButton
  ]
})
export class ProfileEditPage implements OnInit {

  private modalService = inject(ModalService);
  protected auth = inject(AuthService);
  private fb = inject(FormBuilder);
  private clientService = inject(ClientService);
  private cameraService = inject(CameraService);
  private toastCtrl = inject(ToastController);

  private initialFormValue: any;
  private hasChanges = false;

  selectedFile: File | null = null;
  previewUrl: string | null = null;

  birthdateDisplay: string | null = null;

  @ViewChild('fileInput')
  fileInput!: ElementRef<HTMLInputElement>;

  @ViewChild(CountryModalComponent)
  countryModal!: CountryModalComponent;

  openCountryModal() {
    this.modalService.open('country-modal');
  }

  openDateModal() {
    this.modalService.open('date-modal');
  }

  formSignal = signal(
    this.fb.group({
      fullName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      birthdate: [''],
      country: ['']
    })
  );

  constructor() {

    effect(() => {
      const country = this.modalService.getData('country-modal')();
      if (country) {
        this.formSignal().get('country')?.setValue(country);
        this.modalService.clearData('country-modal');
      }
    });

    effect(() => {
      const iso = this.modalService.getData('date-modal')();
      if (!iso) return;

      const date = new Date(iso);
      this.birthdateDisplay = date.toLocaleDateString('es-ES');

      const backendDate = date.toISOString().split('T')[0];
      this.formSignal().get('birthdate')?.setValue(backendDate);

      this.modalService.clearData('date-modal');
    });

    this.formSignal().valueChanges.subscribe(() => {
      this.updateHasChanges();
    });

  }

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
    this.birthdateDisplay = user.birthdate ?? '';
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
        this.hasChanges = false
        this.showSuccessToast();
      },
      error: err => {
        console.log('Error during update:');
      }
    });
  }

  unSaveFormChange(): boolean {
    if (!this.initialFormValue) return false;

    const currentValue = this.formSignal().value as ProfileFormValue;

    const keys = Object.keys(currentValue) as (keyof ProfileFormValue)[];
    const formChanged = keys.some(key => currentValue[key] !== this.initialFormValue![key]);

    const photoChanged = this.previewUrl !== this.auth.getCurrentUserPhoto();

    return formChanged || photoChanged;
  }

  private updateHasChanges() {
    this.hasChanges = this.unSaveFormChange();
  }

  async showSuccessToast() {
    const toast = await this.toastCtrl.create({
      message: 'Perfil actualizado correctamente', color: 'success',
      duration: 2000,
      position: 'bottom',
    });
    await toast.present();
  }



}
