import { Component, OnInit } from '@angular/core';
import {GlobalService} from "../../shared/services/global.service";
import {MenuEntityModel} from "../../model/menu-entity.model";
import {CompanyService} from "../../services/company.service";
import {CompanyModel} from "../../model/company.model";
import {BasketModel} from "../../model/basket.model";
import {Router} from "@angular/router";
import {ClientService} from "../../services/client.service";
import {OurClientModel} from "../../model/our-client";

@Component({
  selector: 'app-basket',
  templateUrl: './basket.component.html',
  styleUrls: ['./basket.component.scss']
})
export class BasketComponent implements OnInit {

  basketPrice : number = 0;
  customerBasket: BasketModel[] = new Array<BasketModel>();
  toUpIconOpacity: number = 0;
  enableOrder: boolean;
  showFinishOrder: boolean = false;
  ourClient: OurClientModel = new OurClientModel();
  orderOpen: boolean = true;
  orderNum: number;

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
    this.router.navigate(['pages/company-detail']);
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
    document.getElementById("finish-order-id").scrollIntoView({behavior: "smooth", block: "start"});
    this.orderOpen = false;
    this.orderNum = clientOrder.id;
    document.getElementById("issued-order").scrollIntoView({behavior: "smooth", block: "start"});
  }

  moveToTop(){
    document.getElementById("top").scrollIntoView({behavior: "smooth", block: "start"});
  }

  goHomePage(){
    let link = 'pages/home';
    this.globalService.dataBusChanged("selected-link",link);
    this.router.navigate([link]);
  }

  ngOnDestroy(){
    if ( !this.orderOpen ){
      this.globalService.clearBasket();
      this.companyService.removeCompaniesFromBasket();
      this.globalService.dataBusChanged("add-to-basket","update");
    }
  }

}

