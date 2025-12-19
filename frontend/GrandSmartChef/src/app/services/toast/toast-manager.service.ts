import { inject, Injectable } from '@angular/core';
import { ToastController } from '@ionic/angular';
import {ToastType} from "../../models/Enums/ToastType";
import {TOAST_CONFIG} from "../../config/toast.config";


@Injectable({ providedIn: 'root' })
export class ToastService {
  private toastCtrl = inject(ToastController);

  async show(
    type: ToastType,
    overrideMessage?: string,
    duration = 2000
  ) {
    const config = TOAST_CONFIG[type];

    const toast = await this.toastCtrl.create({
      message: overrideMessage ?? config.message,
      color: config.color,
      duration,
      position: 'bottom',
      icon: config.icon,
      buttons: [{ role: 'cancel', icon: 'close' }]
    });

    await toast.present();
  }
}
