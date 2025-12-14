import {Component, inject, OnInit} from '@angular/core';
import {RouterModule} from "@angular/router";
import {CommonModule, Location} from '@angular/common';
import { FormsModule } from '@angular/forms';
import {
  IonButton,
  IonCol,
  IonContent,
  IonGrid,
  IonIcon,
  IonLabel,
  IonRouterLink,
  IonRow,
} from '@ionic/angular/standalone';
import {HeaderComponent} from "../../components/headers/main-header/header.component";
import {FooterNavComponent} from "../../components/footer-nav/footer-nav.component";
import {ScrollFooterService} from "../../services/scroll/scroll-footer/scroll-footer.service";
import {FilterProfileComponent} from "../../components/filters/filter-profile/filter-profile.component";
import {ImgProfileComponent} from "../../components/img-profile/img-profile.component";
import {AuthService} from "../../services/auth/auth.service";

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
    FilterProfileComponent, ImgProfileComponent, IonRouterLink,
    RouterModule]
})
export class ProfilePage implements OnInit {
  private scrollFooter = inject(ScrollFooterService);
  private authService = inject(AuthService);
  private location = inject(Location);
  user = this.authService.currentUser;


  onScroll(event: any) {
    this.scrollFooter.updateScroll(event.detail.scrollTop);
  }
  constructor() { }

  ngOnInit() {
    this.authService.ensureCurrentUserLoaded();

  }


  onClose() {
    this.location.back();
  }
}
