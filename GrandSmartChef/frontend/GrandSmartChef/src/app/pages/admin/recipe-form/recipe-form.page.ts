import {Component, effect, ElementRef, inject, input, Input, OnInit, signal, ViewChild} from '@angular/core';
import {CommonModule, Location} from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  IonButton,
  IonCol,
  IonContent, IonFab, IonFabButton,
  IonGrid,
  IonIcon, IonImg, IonInput, IonItem, IonLabel, IonList, IonRow, IonTextarea,
} from '@ionic/angular/standalone';
import {ActivatedRoute} from "@angular/router";
import {ToastController} from "@ionic/angular";
import {ScrollFooterService} from "../../../services/scroll/scroll-footer/scroll-footer.service";
import {RecipeDTO} from "../../../models/recipe.model";
import {Camera, CameraResultType, CameraSource} from "@capacitor/camera";
import {CreateRecipe} from "../../../services/createRecipe/create-recipe";

@Component({
  selector: 'app-recipe-form',
  templateUrl: './recipe-form.page.html',
  styleUrls: ['./recipe-form.page.scss'],
  standalone: true,
  imports: [IonContent, CommonModule, FormsModule, IonButton, IonCol, IonGrid, IonIcon, IonItem, IonLabel, IonList, IonRow, IonTextarea, IonInput, IonFab, IonFabButton, IonImg]
})
export class RecipeFormPage implements OnInit {
  private location = inject(Location);
  private route = inject(ActivatedRoute);
  private createRecipeService = inject(CreateRecipe);
  private toast = inject(ToastController);
  private scrollFooter = inject(ScrollFooterService);

  recipe = signal<RecipeDTO | null>(null);
  @ViewChild('fileInput') fileInput!: ElementRef<HTMLInputElement>;

  selectedFile: File | null = null;
  previewUrl: string | null = null;
  constructor() {
  }


  form = {
    name: '',
    prepTime: '',
    servings: '',
    difficulty: '',
    description: '',
    imageUrl: '', // para preview
    tags: [] as string[],
    ingredients: [] as { quantity: string; unit: string; name: string }[],
    steps: [] as string[]
  };
  tagInput = '';
  ingredientQty = '';
  ingredientUnit = '';
  ingredientName = '';
  stepInput = '';

  ngOnInit() {
    const idParam = this.route.snapshot.paramMap.get('id');
    const id = idParam ? Number(idParam) : null;

    if (id && !isNaN(id)) {
      // Es edición: cargar datos de la receta
      this.createRecipeService.getActiveRecipeDetails(id).subscribe({
        next: (data) => {
          if (data) { // <-- comprobación
            this.recipe.set(data);
            this.loadFormFromRecipe(data); // llena el formulario
            if (data?.imageUrl) {
              this.previewUrl = data.imageUrl;
            }
          } else {
            console.warn('No se encontró la receta con id', id);
          }
        },
        error: (err) => console.error('Error cargando receta', err)
      });
    }
    }


  loadFormFromRecipe(rc: RecipeDTO) {
    this.form.name = rc.name || '';
    this.form.prepTime = rc.prepTime?.toString() || '';
    this.form.servings = rc.servings?.toString() || '';
    this.form.difficulty = rc.difficulty || '';
    this.form.description = rc.description || '';

    this.form.tags = rc.tags?.map(t => t.name) || [];

    this.form.ingredients = rc.ingredients?.map(i => ({
      quantity: i.quantity.toString(),
      unit: i.unit,
      name: i.ingredient.name
    })) || [];

    this.form.steps = rc.steps?.map(s => s.instruction) || [];
  }

  onClose() {
    this.location.back();
  }


  onScroll(event?: CustomEvent) {
    const scrollTop = (event?.detail as any)?.scrollTop;
    if (scrollTop != null) {
      this.scrollFooter.updateScroll(scrollTop);
    }
  }
  async pickImage() {
    try {
      const img = await Camera.getPhoto({
        quality: 90,
        resultType: CameraResultType.DataUrl,
        source: CameraSource.Prompt,
        allowEditing: true
      });

      if (img.dataUrl) {
        const blob = await fetch(img.dataUrl).then(r => r.blob());
        this.selectedFile = new File([blob], 'recipe_' + Date.now() + '.png', { type: blob.type });
        this.previewUrl = img.dataUrl;
      }
    } catch {
      this.fileInput.nativeElement.click();
    }
  }

  onFileSelected(ev: Event) {
    const input = ev.target as HTMLInputElement;
    if (!input.files?.length) return;

    this.selectedFile = input.files[0];

    const reader = new FileReader();
    reader.onload = () => this.previewUrl = reader.result as string;
    reader.readAsDataURL(this.selectedFile);
  }

  /* -------- TAGS -------- */
  addTag() {
    if (!this.tagInput.trim()) return;
    this.form.tags.push(this.tagInput.trim());
    this.tagInput = '';
  }

  removeTag(i: number) {
    this.form.tags.splice(i, 1);
  }

  /* -------- INGREDIENTES -------- */
  addIngredient() {
    if (!this.ingredientName.trim()) return;

    this.form.ingredients.push({
      quantity: this.ingredientQty,
      unit: this.ingredientUnit,
      name: this.ingredientName
    });

    this.ingredientQty = '';
    this.ingredientUnit = '';
    this.ingredientName = '';
  }

  removeIngredient(i: number) {
    this.form.ingredients.splice(i, 1);
  }

  /* -------- PASOS -------- */
  addStep() {
    if (!this.stepInput.trim()) return;
    this.form.steps.push(this.stepInput.trim());
    this.stepInput = '';
  }

  removeStep(i: number) {
    this.form.steps.splice(i, 1);
  }

  /* -------- VALIDACIÓN -------- */
  get formValid() {
    return this.form.name.trim() &&
      this.form.prepTime.trim() &&
      this.form.servings.trim() &&
      this.form.difficulty.trim() &&
      this.form.description.trim() &&
      this.form.tags.length > 0 &&
      this.form.ingredients.length > 0 &&
      this.form.steps.length > 0;
  }

  saveLocal() {
    const payload = { ...this.form, image: this.selectedFile };

    if (this.recipe() && this.recipe()?.id) {
      // edición
      console.log('Actualizando receta:', payload);
      this.createRecipeService.updateRecipe(this.recipe()?.id, payload).subscribe({
        next: () => console.log('Receta actualizada'),
        error: (err) => console.error(err)
      });
    } else {
      // creación
      console.log('Creando nueva receta:', payload);
      this.createRecipeService.createRecipe(payload).subscribe({
        next: () => console.log('Receta creada'),
        error: (err) => console.error(err)
      });
    }
  }
}
