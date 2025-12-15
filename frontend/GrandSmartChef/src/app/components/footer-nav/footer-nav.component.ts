import {Component, OnInit, effect, inject, signal} from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ScrollFooterService } from '../../services/scroll/scroll-footer/scroll-footer.service';
import {IonButton, IonFooter, IonGrid, IonIcon, IonRow, IonToolbar} from "@ionic/angular/standalone";

@Component({
  selector: 'app-footer-nav',
  standalone: true,
  imports: [CommonModule, RouterModule, IonFooter, IonToolbar, IonGrid, IonRow, IonButton, IonIcon],
  templateUrl: './footer-nav.component.html',
  styleUrls: ['./footer-nav.component.scss']
})
export class FooterNavComponent implements OnInit {
  hidden = false;
  private scrollFooter = inject(ScrollFooterService);
  private isPopoverOpen: true | undefined;

  constructor() {
    effect(() => {
      this.hidden = this.scrollFooter.footerHidden();
    });
  }


  removeFocus(event:Event){
    const element= event.currentTarget as HTMLElement;
    element.blur();
  }


  ngOnInit(): void{

  }

}
