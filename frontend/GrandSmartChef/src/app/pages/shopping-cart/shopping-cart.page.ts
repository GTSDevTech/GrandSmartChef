import {Component, effect, inject, OnInit} from '@angular/core';
import {CommonModule, Location} from '@angular/common';
import { FormsModule } from '@angular/forms';
import {HeaderComponent} from "../../components/headers/main-header/header.component";
import {FooterNavComponent} from "../../components/footer-nav/footer-nav.component";
import {ScrollFooterService} from "../../services/scroll/scroll-footer/scroll-footer.service";
import {
  IonButton,
  IonCol,
  IonContent,
  IonGrid,
  IonIcon,
  IonRow
} from "@ionic/angular/standalone";
import {ProgressBarComponent} from "../../components/progress-bar/progress-bar.component";
import {IngredientCardComponent} from "../../components/cards/list-ingredient-card/ingredient-card.component";
import {ShoppingListService} from "../../services/shoppingList/shopping-list.service";
import {AuthService} from "../../services/auth/auth.service";
import {ShoppingListIngredientDTO} from "../../models/shoppingListIngredient.model";
import {ShoppingProgressService} from "../../services/shopping-progress-bar/shopping-progress.service";
import {ActivatedRoute, Router} from "@angular/router";
import {authGuard} from "../../guards/auth-guard/auth-guard";
import {list} from "ionicons/icons";

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.page.html',
  styleUrls: ['./shopping-cart.page.scss'],
  standalone: true,
  imports: [CommonModule, FormsModule, HeaderComponent, FooterNavComponent, IonContent, IonGrid, IonRow, IonCol, IonIcon, ProgressBarComponent, IngredientCardComponent, IonButton]
})
export class ShoppingCartPage implements OnInit {
  private scrollFooter = inject(ScrollFooterService);
  private shoppingListService = inject(ShoppingListService);
  private auth = inject(AuthService);
  private location = inject(Location);
  private progressService = inject(ShoppingProgressService);
  private route = inject(Router);


  shoppingLists = this.shoppingListService.shoppingLists;



  constructor() {
    effect(() => {
      this.progressService.updateProgress(this.shoppingLists());
    });
  }

  ngOnInit(): void {
    const user = this.auth.getCurrentUser();
    if (!user?.id) return;

    this.shoppingListService.getAllShoppingListByUserId(user.id);


  }


  recalculateProgress() {
    const lists = this.shoppingLists();
    this.progressService.updateProgress(lists);
  }

  goToBack() {
    this.location.back();
  }

  onScroll(event?: CustomEvent) {
    const scrollTop = (event?.detail as any)?.scrollTop;
    if (scrollTop != null) {
      this.scrollFooter.updateScroll(scrollTop);
    }
  }

  openSelectorIngredient() {
    this.route.navigate(['/ingredients']);
  }

  deleteCompletedIngredients() {
    const user = this.auth.getCurrentUser();
    if (!user?.id) return;

    this.shoppingListService
      .deleteAllBoughtIngredientsByUser(user.id)
      .subscribe(() => {
        this.recalculateProgress();
      });
  }
}
