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

@Component({
  selector: 'app-company',
  templateUrl: './company.component.html',
  styleUrls: ['./company.component.scss']
})
export class CompanyComponent implements OnInit {

  deliveryMenu : DeliveryMenuModel;
  otherParams = new Array<any>();
  menuCategory: MenuCategoryModel[];
  categoriesListView: string = 'Показать Все Блюда';
  typesListView: string = 'Показать Все Кухни';
  companies: CompanyShortModel[];




  constructor(private router: Router,private _authService: AuthService, private deliveryMenuService : DeliveryMenuService,
              private companyService: CompanyService, private _globalService: GlobalService) {
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
        }
      }
    }, error => {
      console.log('Error: ' + error);
    });
    this._globalService.dataBusChanged('data-loaded',true);
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

}
