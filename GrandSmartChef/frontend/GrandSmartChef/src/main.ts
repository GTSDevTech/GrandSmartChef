import { bootstrapApplication } from '@angular/platform-browser';
import { provideRouter, withPreloading, PreloadAllModules } from '@angular/router';
import { provideIonicAngular } from '@ionic/angular/standalone';
import {provideHttpClient, withInterceptors} from "@angular/common/http";
import { routes } from './app/app.routes';
import { AppComponent } from './app/app.component';
import {apiResponseInterceptor} from "./app/interceptor/api-response.interceptor";

bootstrapApplication(AppComponent, {
  providers: [
    provideIonicAngular({}),
    provideRouter(routes, withPreloading(PreloadAllModules)),
    provideHttpClient(
      withInterceptors([apiResponseInterceptor])
    )
  ],
}).catch(err => console.error('Error iniciando aplicación:', err));
