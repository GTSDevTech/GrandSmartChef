import { CanDeactivateFn } from '@angular/router';
import {ProfileEditPage} from "../../pages/profile-edit/profile-edit.page";

export const profileEditGuard: CanDeactivateFn<ProfileEditPage> = (component, currentRoute, currentState, nextState) => {
  if (component.unSaveFormChange()) {
    return confirm('Tienes cambios sin guardar. ¿Deseas salir?');
  }
  return true;
};
