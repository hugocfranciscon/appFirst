import { Injectable } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor() {}

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {    
    console.log(req.url);
    if (req.url.indexOf('auth/token') == -1) {
      const token = sessionStorage.getItem('token');
      if (token) {      
        const authReq = req.clone({
          headers: req.headers.set('Authorization', 'Bearer ' + token)
        });
        return next.handle(authReq);
      }
    }
    return next.handle(req);
  }
}
