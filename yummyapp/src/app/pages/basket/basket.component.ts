import { Component, OnInit } from '@angular/core';
import {GlobalService} from "../../shared/services/global.service";
import {MenuEntityModel} from "../../model/menu-entity.model";
import {CompanyService} from "../../services/company.service";
import {CompanyModel} from "../../model/company.model";
import {BasketModel} from "../../model/basket.model";
import {Router} from "@angular/router";
import {ClientService} from "../../services/client.service";
import {OurClientModel} from "../../model/our-client";
import {formatDate} from '@angular/common';
import { sha256 } from 'js-sha256';
import {ClientOrderModel} from "../../model/client-order.model";


@Component({
  selector: 'app-basket',
  templateUrl: './basket.component.html',
  styleUrls: ['./basket.component.scss']
})
export class BasketComponent implements OnInit {

  payWalletUrl: string = 'https://payeer.com/merchant/?m_shop=792221744&m_curr=RUB&lang=ru';
  shop: string ='792221744';
  secretKey: string = '123';
  mOrderIdParam: string ='&m_orderid=';
  mAmountParam: string ='&m_amount=';
  mDescParam: string ='&m_desc=';
  mSignParam: string ='&m_sign=';
  basketPrice : number = 0;
  customerBasket: BasketModel[] = new Array<BasketModel>();
  clientOrder: ClientOrderModel;
  toUpIconOpacity: number = 0;
  enableOrder: boolean;
  showFinishOrder: boolean = false;
  ourClient: OurClientModel = new OurClientModel();
  orderOpen: boolean = true;

  constructor(private  globalService : GlobalService, private  clientService : ClientService,
              private companyService: CompanyService,
              private router: Router) { }

  ngOnInit() {
    this.initBasket();

    this.globalService.data$.subscribe(data => {
      if (data.ev === 'add-to-basket' && data.value === 'update') {
        this.initBasket();
      }
    }, error => {
      console.log('Error: ' + error);
    });
  }

  initBasket(){
    this.customerBasket = new Array<BasketModel>();
    this.basketPrice = this.globalService.getBasketPrice();
    if ( this.basketPrice == 0 ){
      this.showFinishOrder = false;
    }
    let simpleBasket = this.globalService.getBasket();
    let companies = this.companyService.getCompaniesModel();
    simpleBasket.forEach( menuEntity => {
      let company = this.getCompanyById( +menuEntity.companyId, companies );
      this.addBasketModel( company, menuEntity );
    });
  }

  getCompanyById( companyId , companies: CompanyModel[]){
    let result = null;
    companies.forEach( company => {
      if( result == null && company.id == companyId ){
        result  = company;
      }
    });
    return result;
  }

  addBasketModel( company: CompanyModel, menuEntity: MenuEntityModel ){
    let resultBasketModel = null;
    this.enableOrder = true;
    this.customerBasket.forEach( basketModel => {
      if ( resultBasketModel == null && basketModel.company.id == company.id ){
        resultBasketModel = basketModel;
      }
    });
    if( resultBasketModel == null ){
      resultBasketModel = new BasketModel();
      resultBasketModel.company = company;
      this.customerBasket.push( resultBasketModel );
    }
    resultBasketModel.price += this.globalService.calculatePrice( menuEntity );
    resultBasketModel.basket.push( menuEntity );
    if ( resultBasketModel.price > resultBasketModel.company.delivery ){
      resultBasketModel.orderPosible = true;
    } else {
      this.enableOrder = false;
    }
  }

  showCompanyDetail(companyId){
    window.localStorage.setItem('companyId',companyId);
    let companyName = this.companyService.getCompanyById( companyId ).companyName;
    this.router.navigate(['pages/company-detail/'+companyName]);
  }

  finishOrder(){
    let uuid = window.localStorage.getItem("our-client");
    if( uuid != null ){
      this.clientService.getClientInfo( uuid ).subscribe(data => {
        if (data.status == 200) {
          this.ourClient = data.result;
        }
        this.showFinishOrder = true;
      });
    } else {
      this.showFinishOrder = true;
    }
    document.getElementById("finish-order-id").scrollIntoView({behavior: "smooth", block: "start"});
  }

  showCompanies(){
    this.router.navigate(['pages/company']);
  }

  onScrollDiv(event: UIEvent): void {
    if ( event.srcElement.scrollTop > 800 ){
      this.toUpIconOpacity = 1;
    } else {
      this.toUpIconOpacity = 0;
    }
  }

  closeOrder( clientOrder ){
    document.getElementById("top").scrollIntoView({behavior: "smooth", block: "start"});
    this.orderOpen = false;
    this.clientOrder = clientOrder;
  }

  moveToTop(){
    document.getElementById("top").scrollIntoView({behavior: "smooth", block: "start"});
  }

  goHomePage(){
    let link = 'pages/home';
    this.globalService.dataBusChanged("selected-link",link);
    this.router.navigate([link]);
  }

  payFromWallet(){
    let now = formatDate(new Date(), 'dd-MM-yyyy', 'en');
    let desc = this.utf8_to_b64('Оплата заказа №'+this.clientOrder.id+', от '+now);
    this.basketPrice = 10;
    let params:any[] = [this.shop,this.clientOrder.id,this.basketPrice+".00",'RUB',desc,this.secretKey ];
    let paramAsString = params.join(':');
    let sign = sha256( paramAsString ).toUpperCase();
    let url = this.payWalletUrl+this.mOrderIdParam+this.clientOrder.id+this.mAmountParam+this.basketPrice+
                this.mDescParam+desc+this.mSignParam+sign;
    (window as any).open(url,'_blank');
  }

   utf8_to_b64( str ) {
    return window.btoa(unescape(encodeURIComponent( str )));
  }

  ngOnDestroy(){
    if ( !this.orderOpen ){
      this.globalService.clearBasket();
      this.companyService.removeCompaniesFromBasket();
      this.globalService.dataBusChanged("add-to-basket","update");
    }
  }

}

