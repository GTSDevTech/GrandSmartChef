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

      if (
        body &&
        typeof body === 'object' &&
        'success' in body &&
        'data' in body
      ) {
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
