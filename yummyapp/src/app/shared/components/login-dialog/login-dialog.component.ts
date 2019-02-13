import { Component, OnInit } from '@angular/core';
import {GlobalService} from "../../services/global.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {OurClientModel} from "../../../model/our-client";
import {ClientService} from "../../../services/client.service";
import swal from "sweetalert2";

@Component({
  selector: 'login-dialog',
  templateUrl: './login-dialog.component.html',
  styleUrls: ['./login-dialog.component.scss']
})
export class LoginDialogComponent implements OnInit {

  authForm: FormGroup;
  showLoginDialog: boolean = false;
  loginType: boolean = true;
  ourClient: OurClientModel = new OurClientModel();
  emailError: string = null;
  phoneError: string = null;
  authError: string = null;
  interval;


  constructor( private _globalService: GlobalService,private formBuilder: FormBuilder,
               private router: Router, private clientService: ClientService) {
    this._globalService.data$.subscribe(data => {
      if (data.ev === 'show-login-dialog') {
        if ( data.value ){
          this.showLoginDialog = true;
        }
      }
    }, error => {
      console.log('Error: ' + error);
    });
  }

  ngOnInit() {
    this.authForm = this.formBuilder.group({
      email: [''],
      password: [''],
      rememberMe: [false]
    });
  }

  onSubmit(){
    this.ourClient.email = this.authForm.controls.email.value;
    this.ourClient.password = this.authForm.controls.password.value;
    let accepted = true;
    if( !this.loginType &&
      this.ourClient.email.length > 0 && !this.emailIsValid( this.ourClient.email.toLowerCase() ) ){
      this.emailError = "Hекорректный электронный адрес!";
      accepted = false;
    }
    if( this.loginType && accepted && ( this.ourClient.phone == null ||
      this.ourClient.phone.length < 10 ) ){
      this.phoneError = "Hекорректный номер телефона!";
      accepted = false;
    }

    if ( !accepted ){
      this.showErrorMsg();
      return;
    }
    if( this.loginType ){
      this.ourClient.email = null;
    } else {
      this.ourClient.phone = null;
    }
    this.clientService.authorizationOurClient( this.ourClient ).subscribe(data => {
      if (data.status == 200) {
        window.localStorage.setItem("our-client",data.result );
        let homeLink = 'pages/home-page';
        let rememberMe = this.authForm.controls.rememberMe.value ? 'true':'false';
        window.localStorage.setItem("remember-me", rememberMe);
        this._globalService.dataBusChanged("selected-link",homeLink);
        this.router.navigate([homeLink]);
        this.showLoginDialog = false;
      } else {
        this.authError = data.message;
        this.showErrorMsg();
      }
    });
  }

  showErrorMsg(){
    this.interval = setInterval(() => {
      this.emailError = null;
      this.phoneError = null;
      this.authError = null;
      clearInterval(this.interval);
    },3000);
  }

  changePhone( phone ){
    this.ourClient.phone = phone;
  }

  emailIsValid (email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)
  }

  switchValue( val ){
    this.loginType = val;
  }


}
