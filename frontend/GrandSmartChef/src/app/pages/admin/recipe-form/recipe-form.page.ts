import {Component, inject, ElementRef, ViewChild, OnInit, signal, NgIterable, effect} from '@angular/core';
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
  IonInput,
  IonItem,
  IonLabel,
  IonList,
  IonRow,
  IonTextarea,
  IonModal, IonSearchbar, IonSelect, IonSelectOption,
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
import {TagService} from "../../../services/tag/tag.service";
import {ToastController} from "@ionic/angular";
import {environment} from "../../../../environments/environment.prod";
import {UnitDTO} from "../../../models/unit.model";
import {UnitService} from "../../../services/unit/unit.service";
import {
  IngredientModalComponent
} from "../../../components/modals/create-recipe-modal/ingredient-modal/ingredient-modal.component";
import {ModalService} from "../../../services/modal/modal.service";
import {TagModalComponent} from "../../../components/modals/create-recipe-modal/tag-modal/tag-modal.component";
import {TagDTO} from "../../../models/tag.model";
import {UnitsModalComponent} from "../../../components/modals/create-recipe-modal/units-modal/units-modal.component";

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
    IngredientModalComponent,
    TagModalComponent,
    UnitsModalComponent,
  ],
})
  export class RecipeFormPage implements OnInit {


    private location = inject(Location);
    private modalService = inject(ModalService);
    private route = inject(ActivatedRoute);
    private createRecipeService = inject(CreateRecipe);
    private ingredientService = inject(IngredientService);
    private tagService = inject(TagService);
    private cameraService = inject(CameraService);
    private scrollFooter = inject(ScrollFooterService);
    private toastCtrl = inject(ToastController);
    private unitService = inject(UnitService);
    private readonly backendUrl = environment.imageBaseUrl;


    recipe = signal<RecipeDTO | null>(null);
    previewUrl: string | null = null;
    selectedFile: File | null = null;

  @ViewChild(IngredientModalComponent)
  ingredientModal!: IngredientModalComponent;

  @ViewChild(TagModalComponent)
  tagModal!: TagModalComponent;

  @ViewChild(UnitsModalComponent)
  unitModal!: UnitsModalComponent;


  @ViewChild('fileInput')
  fileInput!: ElementRef<HTMLInputElement>;

    units: UnitDTO[] = [];
    ingredientsCatalog: IngredientDTO[] = [];
    tagsCatalog: TagDTO[] = [];

    form = {
      name: '',
      prepTime: '',
      servings: '',
      difficulty: '',
      description: '',
      tags: [] as { id?: number; name: string }[],
      ingredients: [] as {
        quantity: string;
        unit: string;
        ingredientId: number;
        ingredientName: string;
      }[],
      steps: [] as string[],
    };

    ingredientQty = '';
    ingredientUnit = '';
    stepInput = '';


    selectedIngredientId!: number;
    selectedIngredientName: string | null = null;

    selectedTagId!: number;
    selectedTagName: string | null = null;

    filteredIngredients: IngredientDTO[] = [];
    filteredTags: { id: number; name: string }[] = [];



    constructor(){
      effect(() => {
        const unitCode = this.modalService.getData('unit-modal')();
        if (unitCode) {
          this.ingredientUnit = unitCode;
          this.modalService.clearData('unit-modal');
        }
      });


      effect(() => {
        const data = this.modalService.getData('ingredient-modal')();
        if (data) {
          this.selectedIngredientId = data.id;
          this.selectedIngredientName = data.name;
          this.modalService.clearData('ingredient-modal');
        }
      });
      effect(() => {
        const data = this.modalService.getData('tag-modal')();
        if (data) {
          this.selectedTagId = data.id;
          this.selectedTagName = data.name;
          this.modalService.clearData('tag-modal');
        }
      });
    }


    ngOnInit() {

      this.ingredientService.getAllIngredients().subscribe(data => {
        this.ingredientsCatalog = data;
        this.filteredIngredients = data;
      });

      this.tagService.getAllTags().subscribe(tags => {
        this.tagsCatalog = tags;
        this.filteredTags = tags;
      });

      this.unitService.getUnits().subscribe(units => {
        this.units = units;
      });

      const idParam = this.route.snapshot.paramMap.get('id');
      const id = idParam ? Number(idParam) : null;

      if (id && !isNaN(id)) {
        this.createRecipeService.getActiveRecipeDetails(id).subscribe({
          next: data => {
            this.recipe.set(data);
            this.loadFormFromRecipe(data);

          }
        });
      }
    }


    loadFormFromRecipe(rc: RecipeDTO) {
      this.form.name = rc.name;
      this.form.prepTime = rc.prepTime.toString();
      this.form.servings = rc.servings.toString();
      this.form.difficulty = rc.difficulty;
      this.form.description = rc.description;

      this.form.tags = rc.tags.map(t => ({
        id: t.id,
        name: t.name
      }));

      this.form.ingredients = rc.ingredients.map(i => ({
        quantity: i.quantity.toString(),
        unit: i.unit,
        ingredientId: i.ingredient.id,
        ingredientName: i.ingredient.name,
      }));

      this.form.steps = rc.steps.map(s => s.instruction);
    }


    openIngredientModal() {
      const selectedIngrIds = this.form.ingredients.map(ing => ing.ingredientId!);
      this.ingredientModal.open({
          ingredients: this.ingredientsCatalog,
          disabledIds: selectedIngrIds
      });
    }


    openTagModal() {
      const selectedIds = this.form.tags.map(t => t.id!);
      this.tagModal.open({
        tags: this.tagsCatalog,
        disabledIds: selectedIds
      });
    }

    openUnitModal() {
      this.unitModal.open(this.units);
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
      this.selectedIngredientName = null;
      this.ingredientQty = '';
      this.ingredientUnit = '';
    }

    removeIngredient(i: number) {
      this.form.ingredients.splice(i, 1);
    }

    addTag() {
      if (!this.selectedTagId) return;

      const tag = this.tagsCatalog.find(t => t.id === this.selectedTagId);
      if (!tag) return;

      if (this.form.tags.some(t => t.id === tag.id)) return;

      this.form.tags.push({
        id: tag.id,
        name: tag.name
      });

      this.selectedTagId = undefined!;
      this.selectedTagName = null;
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

    saveLocal() {
      if (!this.formValid) return;

      const formData = this.buildFormData();
      const isUpdate = !!this.recipe()?.id;

      const request$ = isUpdate
        ? this.createRecipeService.updateRecipe(formData)
        : this.createRecipeService.createRecipe(formData);

      request$.subscribe({
        next: () => {
          this.showToast(
            isUpdate ? 'Receta actualizada correctamente' : 'Receta creada correctamente',
            'success'
          );
          setTimeout(() => this.location.back(), 500);
        },
        error: () => {
          this.showToast(
            isUpdate ? 'Error al actualizar la receta' : 'Error al crear la receta',
            'danger'
          );
        }
      });
    }

    buildRecipeDTO(): RecipeCreateDTO {
      return {
        id: this.recipe()?.id,
        name: this.form.name,
        difficulty: this.form.difficulty,
        servings: Number(this.form.servings),
        prepTime: Number(this.form.prepTime),
        description: this.form.description,
        imageUrl: this.previewUrl ?? '',
        tags: this.form.tags.map(t => ({ id: t.id, name: t.name })),
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

    buildFormData(): FormData {
      const formData = new FormData();
      formData.append(
        'recipe',
        new Blob([JSON.stringify(this.buildRecipeDTO())], { type: 'application/json' })
      );
      if (this.selectedFile) {
        formData.append('image', this.selectedFile, this.selectedFile.name);
      }
      return formData;
    }


    async pickImage() {
      if (Capacitor.getPlatform() === 'web') {
        this.fileInput.nativeElement.click();
      } else {
        const file = await this.cameraService.pickImage();
        if (!file) return;
        this.selectedFile = file;
        const reader = new FileReader();
        reader.onload = () => (this.previewUrl = reader.result as string);
        reader.readAsDataURL(file);
      }
    }

    onFileSelected(event: Event) {
      const input = event.target as HTMLInputElement;
      if (!input.files?.length) return;
      this.selectedFile = input.files[0];
      const reader = new FileReader();
      reader.onload = () => (this.previewUrl = reader.result as string);
      reader.readAsDataURL(this.selectedFile);
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

    async showToast(message: string, color: 'success' | 'danger' | 'warning' = 'success') {
      const toast = await this.toastCtrl.create({
        message,
        duration: 2500,
        position: 'bottom',
        color,
      });
      await toast.present();
    }

    getRecipeImage(imageUrl?: string | null): string {
      if (!imageUrl) {
        return '/assets/images/recipes/default_profile_image.png';
      }
      return `${this.backendUrl}${imageUrl}`;
    }

  formatUnitLabel(unit: string): string {
    const formatted = unit
      .toLowerCase()
      .replace(/_/g, ' ');

    if(formatted.length > 1) {
      return formatted.charAt(0).toUpperCase() + formatted.slice(1);
    }
      return formatted;
  }

  getSelectedUnitLabel(): string {
    if (!this.ingredientUnit) {
      return 'Unidad';
    }

    const unit = this.units.find(u => u.code === this.ingredientUnit);

    return unit
      ? this.formatUnitLabel(unit.code)
      : this.ingredientUnit;
  }

}



