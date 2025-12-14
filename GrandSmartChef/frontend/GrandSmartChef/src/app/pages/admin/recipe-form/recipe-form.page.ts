import { Component, inject, ElementRef, ViewChild, OnInit, signal } from '@angular/core';
import { CommonModule, Location } from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  IonButton,
  IonCol,
  IonContent,
  IonFab,
  IonFabButton,
  IonGrid,
  IonIcon,
  IonImg,
  IonInput,
  IonItem,
  IonLabel,
  IonList,
  IonRow,
  IonTextarea,
  IonSelect,
  IonSelectOption,
} from '@ionic/angular/standalone';
import { ActivatedRoute } from '@angular/router';
import { ScrollFooterService } from '../../../services/scroll/scroll-footer/scroll-footer.service';
import { RecipeDTO } from '../../../models/recipe.model';
import { CreateRecipe } from '../../../services/createRecipe/create-recipe';
import { Capacitor } from '@capacitor/core';
import { CameraService } from '../../../services/camera/camera.service';
import { RecipeCreateDTO } from '../../../models/recipeCreateDTO.model';
import { IngredientDTO } from '../../../models/ingredient.model';
import { IngredientService } from '../../../services/ingredient/ingredient.service';

@Component({
  selector: 'app-recipe-form',
  templateUrl: './recipe-form.page.html',
  styleUrls: ['./recipe-form.page.scss'],
  standalone: true,
  imports: [
    IonContent,
    CommonModule,
    FormsModule,
    IonButton,
    IonCol,
    IonGrid,
    IonIcon,
    IonItem,
    IonLabel,
    IonList,
    IonRow,
    IonTextarea,
    IonInput,
    IonFab,
    IonFabButton,
    IonImg,
    IonSelect,
    IonSelectOption,
  ],
})
export class RecipeFormPage implements OnInit {
  private location = inject(Location);
  private route = inject(ActivatedRoute);
  private createRecipeService = inject(CreateRecipe);
  private ingredientService = inject(IngredientService);
  private scrollFooter = inject(ScrollFooterService);
  private cameraService = inject(CameraService);

  recipe = signal<RecipeDTO | null>(null);

  @ViewChild('fileInput') fileInput!: ElementRef<HTMLInputElement>;

  // Imagen (solo frontend)
  previewUrl: string | null = null;

  // Ingredientes
  ingredientsCatalog: IngredientDTO[] = [];
  selectedIngredientId!: number;

  // FORM
  form = {
    name: '',
    prepTime: '',
    servings: '',
    difficulty: '',
    description: '',
    tags: [] as string[],
    ingredients: [] as {
      quantity: string;
      unit: string;
      ingredientId: number;
      ingredientName: string;
    }[],
    steps: [] as string[],
  };

  tagInput = '';
  ingredientQty = '';
  ingredientUnit = '';
  stepInput = '';

  ngOnInit() {
    this.ingredientService.getAllIngredients().subscribe((data) => {
      this.ingredientsCatalog = data;
    });

    const idParam = this.route.snapshot.paramMap.get('id');
    const id = idParam ? Number(idParam) : null;

    if (id && !isNaN(id)) {
      this.createRecipeService.getActiveRecipeDetails(id).subscribe({
        next: (data) => {
          this.recipe.set(data);
          this.loadFormFromRecipe(data);
          this.previewUrl = data.imageUrl;
        },
      });
    }
  }

  loadFormFromRecipe(rc: RecipeDTO) {
    this.form.name = rc.name;
    this.form.prepTime = rc.prepTime.toString();
    this.form.servings = rc.servings.toString();
    this.form.difficulty = rc.difficulty;
    this.form.description = rc.description;
    this.form.tags = rc.tags.map(t => t.name);
    this.form.ingredients = rc.ingredients.map(i => ({
      quantity: i.quantity.toString(),
      unit: i.unit,
      ingredientId: i.ingredient.id,
      ingredientName: i.ingredient.name,
    }));
    this.form.steps = rc.steps.map(s => s.instruction);
  }

  buildRecipeDTO(): RecipeCreateDTO {
    return {
      id: this.recipe()?.id,
      name: this.form.name,
      difficulty: this.form.difficulty,
      servings: Number(this.form.servings),
      prepTime: Number(this.form.prepTime),
      description: this.form.description,

      // 🔴 OBLIGATORIO PARA BACKEND
      imageUrl: this.previewUrl ?? 'https://ejemplo.com/default.jpg',

      tags: this.form.tags.map(name => ({ name })),

      ingredients: this.form.ingredients.map(i => ({
        quantity: Number(i.quantity),
        unit: i.unit,
        ingredient: { id: i.ingredientId },
      })),

      steps: this.form.steps.map((instruction, index) => ({
        stepNumber: index + 1,
        instruction,
      })),
    };
  }

  saveLocal() {
    if (!this.formValid) return;

    const dto = this.buildRecipeDTO();
    console.log('JSON enviado:', dto);

    if (this.recipe()?.id) {
      this.createRecipeService.updateRecipe(dto).subscribe();
    } else {
      this.createRecipeService.createRecipe(dto).subscribe();
    }
  }

  // ---------- Imagen (solo preview)
  async pickImage() {
    if (Capacitor.getPlatform() === 'web') {
      this.fileInput.nativeElement.click();
    } else {
      const file = await this.cameraService.pickImage();
      if (!file) return;
      const reader = new FileReader();
      reader.onload = () => (this.previewUrl = reader.result as string);
      reader.readAsDataURL(file);
    }
  }

  onFileSelected(event: Event) {
    const input = event.target as HTMLInputElement;
    if (!input.files?.length) return;
    const reader = new FileReader();
    reader.onload = () => (this.previewUrl = reader.result as string);
    reader.readAsDataURL(input.files[0]);
  }

  addIngredient() {
    if (!this.selectedIngredientId || !this.ingredientQty) return;
    const ing = this.ingredientsCatalog.find(i => i.id === this.selectedIngredientId);
    if (!ing) return;

    this.form.ingredients.push({
      quantity: this.ingredientQty,
      unit: this.ingredientUnit,
      ingredientId: ing.id,
      ingredientName: ing.name,
    });

    this.selectedIngredientId = undefined!;
    this.ingredientQty = '';
    this.ingredientUnit = '';
  }

  removeIngredient(i: number) {
    this.form.ingredients.splice(i, 1);
  }

  addTag() {
    if (!this.tagInput.trim()) return;
    this.form.tags.push(this.tagInput.trim());
    this.tagInput = '';
  }

  removeTag(i: number) {
    this.form.tags.splice(i, 1);
  }

  addStep() {
    if (!this.stepInput.trim()) return;
    this.form.steps.push(this.stepInput.trim());
    this.stepInput = '';
  }

  removeStep(i: number) {
    this.form.steps.splice(i, 1);
  }

  onClose() {
    this.location.back();
  }

  onScroll(event: CustomEvent) {
    const scrollTop = (event?.detail as any)?.scrollTop;
    if (scrollTop != null) {
      this.scrollFooter.updateScroll(scrollTop);
    }
  }

  get formValid() {
    return (
      this.form.name.trim() &&
      this.form.prepTime &&
      this.form.servings &&
      this.form.difficulty &&
      this.form.description &&
      this.form.tags.length > 0 &&
      this.form.ingredients.length > 0 &&
      this.form.steps.length > 0
    );
  }
}
