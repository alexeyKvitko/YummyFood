import { Component, OnInit } from '@angular/core';
import { Router} from "@angular/router";
import {LoginService} from "../../services/login.service";

@Component({
  selector: 'app-login',
  template: ''
})
export class LoginComponent implements OnInit {

  // loginForm: FormGroup;
  invalidLogin: boolean = false;

  constructor( private router: Router, private loginService: LoginService) {}

  onSubmit() {
    const loginPayload = {
      username: "guest",
      password: "1111"
    };

    this.loginService.login(loginPayload).subscribe(data => {
      if(data.status === 200) {
        window.localStorage.setItem('token', data.result.token);
        window.localStorage.setItem('userrole', data.result.userRole);
        this.router.navigate(['pages/home-page']);
      }else {
        this.invalidLogin = true;
        alert(data.message);
      }
    });
  }

  ngOnInit() {
    window.localStorage.removeItem('token');
    this.onSubmit();
  }

}
