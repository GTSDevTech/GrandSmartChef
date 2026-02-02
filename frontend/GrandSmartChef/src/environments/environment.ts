// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  apiUrl: '/api',
  imageBaseUrl: '/api/uploads/profile/'
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.

Desarrollo con Conexión desde el emulador Android y Capacitor + WEB:
const API_WEB = '/api';
const API_RENDER = 'https://grandsmartchef-app.onrender.com/api';

export const environment = {
  production: false,
  apiUrl: Capacitor.isNativePlatform() ? API_RENDER : API_WEB,
  imageBaseUrl: Capacitor.isNativePlatform()
    ? 'https://grandsmartchef-app.onrender.com'
    : ''
};


 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
