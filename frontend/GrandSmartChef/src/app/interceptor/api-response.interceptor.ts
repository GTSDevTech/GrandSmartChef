import {
  HttpInterceptorFn,
  HttpResponse
} from '@angular/common/http';
import { map } from 'rxjs';

export const apiResponseInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req).pipe(
    map(event => {

      if (!(event instanceof HttpResponse)) {
        return event;
      }

      const body = event.body;

      // Solo si es ApiResponseDTO REAL
      if (
        body &&
        typeof body === 'object' &&
        'success' in body &&
        'data' in body
      ) {
        // ⚠️ Si data es null, NO tocar
        if (body.data === null || body.data === undefined) {
          return event;
        }

        return event.clone({
          body: body.data
        });
      }

      return event;
    })
  );
};
