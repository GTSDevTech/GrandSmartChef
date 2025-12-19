import {ToastType} from "../models/Enums/ToastType";


export const TOAST_CONFIG: Record<
  ToastType,
  { message: string; color: string; icon: string }
> = {
  [ToastType.SUCCESS]: {
    message: 'Acción realizada correctamente',
    color: 'success',
    icon: 'checkmark-circle'
  },
  [ToastType.ERROR]: {
    message: 'Ha ocurrido un error',
    color: 'danger',
    icon: 'close-circle'
  },
  [ToastType.WARNING]: {
    message: 'Atención',
    color: 'warning',
    icon: 'alert-circle'
  },
  [ToastType.INFO]: {
    message: 'Información',
    color: 'primary',
    icon: 'information-circle'
  },


  [ToastType.RECIPE_RATED]: {
    message: 'Receta valorada correctamente',
    color: 'success',
    icon: 'star'
  },

  [ToastType.RECIPE_SAVED]: {
    message: 'Receta guardada en favoritos',
    color: 'success',
    icon: 'heart'
  },

  [ToastType.RECIPE_REMOVED]: {
    message: 'Receta eliminada de favoritos',
    color: 'medium',
    icon: 'heart-dislike'
  },

  [ToastType.LOGIN_SUCCESS]: {
    message: 'Bienvenido 👨‍🍳',
    color: 'success',
    icon: 'log-in'
  },

  [ToastType.LOGIN_ERROR]: {
    message: 'Usuario o contraseña incorrectos',
    color: 'danger',
    icon: 'lock-closed'
  },

  [ToastType.PROFILE_UPDATED]: {
    message: 'Perfil actualizado',
    color: 'success',
    icon: 'person-circle'
  },

  [ToastType.PREFERENCES_UPDATED]: {
    message: 'Preferencias guardadas',
    color: 'success',
    icon: 'restaurant'
  }
};
