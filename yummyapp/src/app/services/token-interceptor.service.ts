import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import { Observable } from "rxjs/internal/Observable";
import { Injectable } from "@angular/core";
import { environment } from "../environments/environment.prod";

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  private baseUrl : string = environment.baseApiUrl;

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let token = window.localStorage.getItem('token');
    if ( req.url == 'https://ipinfo.io/json' ){
      return next.handle(req);
    }
    // Add to production
    // req = req.clone({
    //   url: this.baseUrl+req.url
    // });
    if (token ) {
      req = req.clone({
        setHeaders: {
          Authorization: 'Bearer ' + token
        }
      });
    }

    return next.handle(req);
  }
}

