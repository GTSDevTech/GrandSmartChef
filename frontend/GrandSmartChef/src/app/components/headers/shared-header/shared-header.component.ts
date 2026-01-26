import {Component, inject, input, OnInit, output} from '@angular/core';
import {IonGrid, IonHeader, IonIcon, IonLabel, IonRow} from "@ionic/angular/standalone";
import {Location} from "@angular/common";

@Component({
    selector: 'app-shared-header',
    templateUrl: './shared-header.component.html',
    styleUrls: ['./shared-header.component.scss'],
    imports: [
        IonGrid,
        IonHeader,
        IonIcon,
        IonLabel,
        IonRow
    ]
})
export class SharedHeaderComponent  implements OnInit {
  label = input<string>();
  icon = input<string>();
  bgColor = input<string>();
  closeMenu = output<void>();

  private location = inject(Location);

  ngOnInit() {
  }

  goToBack() {
    this.closeMenu.emit();
    this.location.back();
  }



}
