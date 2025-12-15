import {Component, inject, OnInit} from '@angular/core';
import {Router, RouterModule} from '@angular/router';
import { FormsModule } from '@angular/forms';
import {IonContent, IonImg, IonRow} from "@ionic/angular/standalone";



@Component({
  selector: 'app-starter',
  templateUrl: './starter.page.html',
  styleUrls: ['./starter.page.scss'],
  standalone: true,
  imports: [
    IonContent,
    IonImg,
    FormsModule,
    RouterModule,
    IonRow
  ]
})
export class StarterPage implements OnInit {

  private router = inject(Router);

  ngOnInit() {
    setTimeout(() => {
      this.router.navigate(['/login']);
    },2000);
  }


}
