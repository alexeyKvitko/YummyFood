import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable()
export class AuthService {

  constructor( private router: Router ) {}

  public isAuthenticated() {

    let token = window.localStorage.getItem('token');
    if ( !token ){
      this.router.navigate(['login']);
    }
    return true;
  }

}
