import {Component, inject, OnInit} from '@angular/core';
import {Router, RouterModule} from "@angular/router";
import {CommonModule, Location} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {IonButton, IonCol, IonContent, IonGrid, IonIcon, IonLabel, IonRow,} from '@ionic/angular/standalone';
import {HeaderComponent} from "../../components/headers/main-header/header.component";
import {FooterNavComponent} from "../../components/footer-nav/footer-nav.component";
import {ScrollFooterService} from "../../services/scroll/scroll-footer/scroll-footer.service";
import {FilterProfileComponent} from "../../components/filters/filter-profile/filter-profile.component";
import {ImgProfileComponent} from "../../components/img-profile/img-profile.component";
import {AuthService} from "../../services/auth/auth.service";
import {ClientService} from "../../services/client/client.service";
import {CollectionService} from "../../services/collection/collection.service";
import {Observable} from "rxjs";
import {HistoryService} from "../../services/history/history.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.page.html',
  styleUrls: ['./profile.page.scss'],
  standalone: true,
  imports: [CommonModule,
    FormsModule,
    HeaderComponent,
    FooterNavComponent,
    IonContent,
    IonCol, IonGrid, IonIcon, IonRow, IonLabel, IonButton,
    FilterProfileComponent, ImgProfileComponent, RouterModule]
})
export class ProfilePage implements OnInit {
  private scrollFooter = inject(ScrollFooterService);
  private authService = inject(AuthService);
  private clientService = inject(ClientService);
  private location = inject(Location);
  private router = inject(Router);
  private collectionService = inject(CollectionService);
  private historyService = inject(HistoryService);
  user = this.authService.currentUser;
  collectionsCount$!: Observable<number>;
  favoriteRecipesCount$!: Observable<number>;
  cookedRecipesCount$!: Observable<number>;


  onScroll(event: any) {
    this.scrollFooter.updateScroll(event.detail.scrollTop);
  }
  constructor() { }

  ngOnInit() {
    this.authService.ensureCurrentUserLoaded();
    const idUser = this.authService.getCurrentUser()?.id;
    if (idUser) {
      this.cookedRecipesCount$ =
        this.historyService.countCookedRecipes(idUser);

      this.collectionsCount$ =
        this.collectionService.countFavoriteCollectionsAvailables(idUser);

      this.favoriteRecipesCount$ =
      this.collectionService.countFavoriteRecipesAvailables(idUser);
    }
  }

  onPreferencesChange(prefs: { id: number; name: string }[]) {
    this.clientService.updatePreferences(prefs).subscribe();
  }


  onClose() {
    this.location.back();
  }

  goToProfileEdit() {
    this.router.navigate(['/profile-edit']);
  }


}
