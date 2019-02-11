import {Component, OnInit} from '@angular/core';
import {GlobalService} from "../../shared/services/global.service";
import {CompanyService} from "../../services/company.service";
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {OurClientModel} from "../../model/our-client";
import {ClientService} from "../../services/client.service";
import swal from "sweetalert2";

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {

  toUpIconOpacity: number = 0;
  registrationForm: FormGroup;
  ourClient: OurClientModel = new OurClientModel();
  emailError: string;
  phoneError: string = null;
  passwordError: string = null;
  interval;


  constructor(private  globalService: GlobalService, private companyService: CompanyService, private router: Router
              ,private formBuilder: FormBuilder, private clientService: ClientService) {
  }

  ngOnInit() {
    this.registrationForm = this.formBuilder.group({
      id: [{ value: this.ourClient.id, disabled: true}],
      email: ['', Validators.required],
      password: ['', Validators.compose([Validators.required])],
      confirm: ['', Validators.compose([Validators.required])]
    });

  }

  onScrollDiv(event: UIEvent): void {
    if ( event.srcElement.scrollTop > 800 ){
      this.toUpIconOpacity = 1;
    } else {
      this.toUpIconOpacity = 0;
    }
  }

  moveToTop(){
    document.getElementById("top").scrollIntoView({behavior: "smooth", block: "start"});
  }

  onSubmit(){
    this.ourClient.email = this.registrationForm.controls.email.value;
    this.ourClient.password = this.registrationForm.controls.password.value;
    this.ourClient.confirm = this.registrationForm.controls.confirm.value;
    let accepted = true;
    if( this.ourClient.email.length > 0 && !this.emailIsValid( this.ourClient.email.toLowerCase() ) ){
      this.emailError = "Hекорректный электронный адрес!";
      accepted = false;
    }
    if( accepted && ( this.ourClient.phone == null ||
       this.ourClient.phone.length < 10 ) ){
      this.phoneError = "Hекорректный номер телефона!";
      accepted = false;
    }
    if( accepted && this.ourClient.password != null &&
      this.ourClient.password.length < 8 ){
      this.passwordError = "Длина пароля должна быть не менее 8 символов!";
      accepted = false;
    }
    if( accepted && this.ourClient.password != null &&
          this.ourClient.password != this.ourClient.confirm ){
      this.passwordError = "Не совпадают введенные пароли!";
      accepted = false;
    }
    if ( !accepted ){
      this.interval = setInterval(() => {
        this.emailError = null;
        this.phoneError = null;
        this.passwordError = null;
        clearInterval(this.interval);
      },3000);
      return;
    }
    this.clientService.registerOurClient( this.ourClient ).subscribe(data => {
      if (data.status == 200) {
        window.localStorage.setItem("our-client",this.ourClient.phone );
      }
      this.showHttpActionMessage(data);
    });
  }

  changePhone( phone ){
    this.ourClient.phone = phone;
  }

  emailIsValid (email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)
  }


  showHttpActionMessage(data) {
    if (data.status === 200) {
      swal('Регистрация данных, успешна');
    } else {
      swal({
        type: 'error',
        title: data.status,
        text: data.message,
      });
    }
  }

}
