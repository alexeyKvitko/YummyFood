import { Component, OnInit } from '@angular/core';
import {GlobalService} from "../../shared/services/global.service";
import {MenuEntityModel} from "../../model/menu-entity.model";
import {CompanyService} from "../../services/company.service";
import {CompanyModel} from "../../model/company.model";

@Component({
  selector: 'app-basket',
  templateUrl: './basket.component.html',
  styleUrls: ['./basket.component.scss']
})
export class BasketComponent implements OnInit {

  basketPrice : number = 0;
  customerBasket: BasketModel[] = new Array<BasketModel>();

  constructor(private  globalService : GlobalService, private companyService: CompanyService) { }

  ngOnInit() {
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
  }

}

class BasketModel {
  company: CompanyModel;
  basket: MenuEntityModel[] = new Array<MenuEntityModel>();
  price: number = 0;
}
