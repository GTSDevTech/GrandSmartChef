import {
  Component,
  ElementRef,
  inject,
  OnInit,
  signal,
  ViewChild
} from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {
  IonButton,
  IonContent, IonFab, IonFabButton,
  IonGrid, IonIcon, IonImg, IonInput, IonItem, IonLabel,
  IonRow,
} from '@ionic/angular/standalone';
import {AuthService} from "../../services/auth/auth.service";
import {Router} from "@angular/router";
import {ClientService} from "../../services/client/client.service";
import {CameraService} from "../../services/camera/camera.service";
import {Capacitor} from "@capacitor/core";
import { FilterProfileComponent } from "src/app/components/filters/filter-profile/filter-profile.component";



@Component({
  selector: 'app-post-create-account',
  templateUrl: './post-create-account.page.html',
  styleUrls: ['./post-create-account.page.scss'],
  standalone: true,
  imports: [IonContent, CommonModule, FormsModule, IonRow,
    IonGrid, ReactiveFormsModule, IonButton, IonFab, IonFabButton, IonIcon, IonImg, IonInput, IonItem, IonLabel, FilterProfileComponent]
})
export class PostCreateAccountPage implements OnInit {

  private auth = inject(AuthService);
  private router = inject(Router);
  private fb = inject(FormBuilder);
  private clientService = inject(ClientService);
  private cameraService = inject(CameraService);

  selectedFile: File | null = null;
  previewUrl: string | null = null;

  @ViewChild('fileInput') fileInput!: ElementRef<HTMLInputElement>;

  formSignal = signal(this.fb.group({
    fullName: ['', Validators.required],
    birthdate: [''],
    country: ['']
  }));


  constructor() {
  }

  ngOnInit() {

    const token = this.auth.getToken();
    if (!token) {
      this.router.navigate(['/register']);
      return;
    }

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


  CompleteRegister() {
    const form: FormGroup = this.formSignal();

    form.get('preferences')?.setValue([
      {id: 1, name: 'Vegetariano'},
    ]);

    if (form.invalid) return;

    const data = form.value;

    const formData = new FormData();

    const isoBirthdate = data.birthdate
      ? data.birthdate.split('/').reverse().join('-')
      : null;

    const profileData = {
      fullName: data.fullName,
      birthdate: isoBirthdate,
      country: data.country,
      preferences: data.preferences ?? [],
    };

    formData.append(
      'profile',
      new Blob([JSON.stringify(profileData)], {type: 'application/json'})
    );

    if (this.selectedFile) {
      formData.append('photoProfile', this.selectedFile, this.selectedFile.name);
    }

    this.auth.registerStep2(formData).subscribe(() => {
      this.clientService.getCurrentClient().subscribe(client => {
        this.auth.setCurrentUser(client);
        this.router.navigate(['/home']);
      });
    });
  }

}
