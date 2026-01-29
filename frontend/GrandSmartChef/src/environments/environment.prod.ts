import { Capacitor } from '@capacitor/core';

const API_WEB = '/api';
const API_RENDER = 'https://grandsmartchef-app.onrender.com/api';

export const environment = {
  production: false,
  apiUrl: Capacitor.isNativePlatform() ? API_RENDER : API_WEB,
  imageBaseUrl: Capacitor.isNativePlatform()
    ? 'https://grandsmartchef-app.onrender.com'
    : ''
};



/*
export const environment = {
  production: true,
  apiUrl: '/api',
  imageBaseUrl: 'http://localhost:8080/api/uploads/profile/'
};
*/
// export const environment = {
//   production: true,
//   apiUrl: '/api',
//   // imageBaseUrl: 'http://localhost:8080/api/uploads/profile/'
//
//   imageBaseUrl: 'https://grandsmartchef-app.onrender.com/api/uploads/profile/'
// }
