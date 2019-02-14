import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {OurClientModel} from "../../../model/our-client";
import {ClientOrderModel} from "../../../model/client-order.model";
import {BasketModel} from "../../../model/basket.model";

@Component({
  selector: 'finish-order',
  templateUrl: './finish-order.component.html',
  styleUrls: ['./finish-order.component.scss']
})
export class FinishOrderComponent implements OnInit {

  @Input()
  ourClient: OurClientModel;

  @Input()
  orderBasket: BasketModel[];

  clientOrder: ClientOrderModel = new ClientOrderModel();
  changedPhone: string = null;

  orderForm: FormGroup;
  nickNameError: string = null;
  emailError: string = null;
  phoneError: string = null;
  cityError: string = null;
  streetError: string = null;
  buildingError: string = null;
  interval;

  constructor(private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.initForm();
  }

  initForm(){
    this.clientOrder.payType = 'CASH';
    this.orderForm = this.formBuilder.group({
      nickName: [''],
      email: [this.ourClient.email],
      city: [window.localStorage.getItem("delivery-city")],
      street: [''],
      building: [''],
      flat: [''],
      floor: [''],
      entry: [''],
      intercom: [''],
      needChange: [''],
      comment: ['']
     });
  }

  selectPayType( payType ){
    this.clientOrder.payType = payType;
  }

  changePhone( val ){
    this.changedPhone = val;
  }

  onSubmit(){
    this.clientOrder.id = -1;
    this.clientOrder.nickName = this.orderForm.controls.nickName.value;
    this.clientOrder.email = this.orderForm.controls.email.value;
    this.clientOrder.phone = this.changedPhone != null ? this.changedPhone : this.ourClient.phone;
    this.clientOrder.city = this.orderForm.controls.city.value;
    this.clientOrder.street = this.orderForm.controls.street.value;
    this.clientOrder.building = this.orderForm.controls.building.value;
    this.clientOrder.entry = this.orderForm.controls.entry.value;
    this.clientOrder.floor = this.orderForm.controls.floor.value;
    this.clientOrder.flat = this.orderForm.controls.flat.value;
    this.clientOrder.intercom = this.orderForm.controls.intercom.value;
    this.clientOrder.needChange = this.orderForm.controls.needChange.value;
    this.clientOrder.comment = this.orderForm.controls.comment.value;
    this.clientOrder.orders = this.orderBasket;

    let accepted = true;
    if( ( this.clientOrder.nickName == null ||
      this.clientOrder.nickName.trim().length == 0 ) ){
      this.nickNameError = "Обращение - обязательное поле!";
      accepted = false;
    }
    if( accepted && this.clientOrder.email.length > 0 && !this.emailIsValid( this.ourClient.email.toLowerCase() ) ){
      this.emailError = "Hекорректный электронный адрес!";
      accepted = false;
    }
    if( accepted && ( this.clientOrder.phone == null ||
      this.clientOrder.phone.length < 10 ) ){
      this.phoneError = "Hекорректный номер телефона!";
      accepted = false;
    }
    if( accepted && ( this.clientOrder.city == null ||
                                    this.clientOrder.city.trim().length == 0 ) ){
      this.cityError = "Город - обязательное поле!";
      accepted = false;
    }
    if( accepted && ( this.clientOrder.street == null ||
      this.clientOrder.street.trim().length == 0 ) ){
      this.streetError = "Улица - обязательное поле!";
      accepted = false;
    }
    if( accepted && ( this.clientOrder.building == null ||
      this.clientOrder.building.trim().length == 0 ) ){
      this.buildingError = "Дом - обязательное поле!";
      accepted = false;
    }

    if ( !accepted ){
      this.interval = setInterval(() => {
        this.emailError = null;
        this.phoneError = null;
        this.nickNameError = null;
        this.cityError = null;
        this.streetError = null;
        this.buildingError = null;
        clearInterval(this.interval);
      },3000);
      return;
    }
    console.log("ClientOrder", this.clientOrder );
    // this.clientService.registerOurClient( this.ourClient ).subscribe(data => {
    //   if (data.status == 200) {
    //     window.localStorage.setItem("our-client",data.result );
    //     let homeLink = 'pages/home-page';
    //     this.router.navigate([homeLink]);
    //     this.globalService.dataBusChanged("selected-link",homeLink);
    //   }
    //   this.showHttpActionMessage(data);
    // });
  }

  emailIsValid (email) {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)
  }

}
