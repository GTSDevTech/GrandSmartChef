import {
  HttpInterceptorFn,
  HttpResponse
} from '@angular/common/http';
import { map } from 'rxjs';

export const apiResponseInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req).pipe(
    map(event => {

      // 🔹 Solo nos interesan respuestas HTTP reales
      if (!(event instanceof HttpResponse)) {
        return event;
      }

      const body = event.body;

      // 🔹 Si no es un objeto, no tocar
      if (!body || typeof body !== 'object') {
        return event;
      }

      // 🔹 Si tiene data, desempaquetamos
      if ('data' in body) {
        return event.clone({
          body: body.data
        });
      }

      // 🔹 Si NO tiene data, lo dejamos tal cual
      return event;
    })
  );
};
