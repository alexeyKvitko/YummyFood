import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';

import { CompanyModel } from '../../model/company.model';
import { CompanyService } from '../../services/company.service';
import { GlobalService } from '../../shared/services/global.service';
import { AuthService } from "../../services/auth.service";
import {MenuCategoryModel} from "../../model/menu-category.model";
import {DeliveryMenuService} from "../../services/delivery-menu.service";
import {DeliveryMenuModel} from "../../model/delivery-menu.model";
import {OTHER_PARAMS} from "./const-other-params";
import {CompanyShortModel} from "../../model/company-short.model";
import {MenuEntityModel} from "../../model/menu-entity.model";

@Component({
  selector: 'app-company',
  templateUrl: './company.component.html',
  styleUrls: ['./company.component.scss']
})
export class CompanyComponent implements OnInit {

  deliveryMenu : DeliveryMenuModel;
  userRole: string = null;
  otherParams = new Array<any>();
  menuCategory: MenuCategoryModel[];
  categoriesListView: string = 'Показать Все Блюда';
  typesListView: string = 'Показать Все Кухни';
  companies: CompanyShortModel[];
  deliveryCity: string = "";
  basket: MenuEntityModel[] = new Array<MenuEntityModel>();
  toUpIconOpacity: number = 0;

  constructor(private router: Router,private _authService: AuthService, private deliveryMenuService : DeliveryMenuService,
              private companyService: CompanyService, private _globalService: GlobalService) {
    this.userRole = window.localStorage.getItem('userrole');
    this._authService.isAuthenticated();
    this.deliveryMenu = new DeliveryMenuModel();
    this.companies =  new Array<CompanyShortModel>();
    this.otherParams = OTHER_PARAMS;
  }

  ngOnInit() {
    this._globalService.data$.subscribe(data => {
      if (data.ev === 'data-loaded') {
        if ( data.value ){
          this.deliveryMenu =  this.companyService.getDeliveryMenus();
          this.companies = this.companyService.getCompaniesShort();
          this.deliveryCity = this.companyService.getDeliveryCity();
        }
      }
      if (data.ev === 'add-to-basket' && data.value === 'update') {
        this.basket = this._globalService.getBasket();
      }
    }, error => {
      console.log('Error: ' + error);
    });
    this._globalService.dataBusChanged('data-loaded',true);
    this._globalService.dataBusChanged('logo-opacity',1);
  };

  showCategoryItem( idx: number){
    if ( this.categoriesListView === 'Скрыть Все Блюда' ){
      return true;
    }
    if ( this.categoriesListView === 'Показать Все Блюда' && idx > 8){
      return false;
    }
    return true;
  }

  showTypesItem( idx: number){
    if ( this.typesListView === 'Скрыть Все Кухни' ){
      return true;
    }
    if ( this.typesListView === 'Показать Все Кухни' && idx > 2){
      return false;
    }
    return true;
  }

  showAllCategories(){
    this.categoriesListView = this.categoriesListView === 'Показать Все Блюда' ? 'Скрыть Все Блюда' : 'Показать Все Блюда';
  }

  showAllTypes(){
    this.typesListView = this.typesListView === 'Показать Все Кухни' ? 'Скрыть Все Кухни' : 'Показать Все Кухни';
  }


  showCompanyDetails( companyId ){
    window.localStorage.setItem('companyId',companyId);
    this.router.navigate(['pages/company-info']);
  }

  editCompany( companyId ){
    window.localStorage.setItem('companyId',companyId);
    this.router.navigate(['pages/company-edit']);

  }

  createCompany(){
    window.localStorage.setItem('companyId','-1');
    this.router.navigate(['pages/company-edit']);
  }

  showCompanyDetail(companyId){
    window.localStorage.setItem('companyId',companyId);
    this.router.navigate(['pages/company-detail']);
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

  isRoleAdmin(){
    return this.userRole == 'ROLE_ADMIN';
  }

}
