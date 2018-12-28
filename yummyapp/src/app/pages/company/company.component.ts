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

@Component({
  selector: 'app-company',
  templateUrl: './company.component.html',
  styleUrls: ['./company.component.scss']
})
export class CompanyComponent implements OnInit {

  deliveryMenu : DeliveryMenuModel;
  otherParams: OTHER_PARAMS;
  menuCategory: MenuCategoryModel[];
  categoriesListView: string = 'Показать Все Блюда';
  typesListView: string = 'Показать Все Кухни';
  companies: CompanyModel[];
  loading: boolean = false;




  constructor(private router: Router,private _authService: AuthService, private deliveryMenuService : DeliveryMenuService,
              private companyService: CompanyService, private _globalService: GlobalService) {
    this._authService.isAuthenticated();
    this.deliveryMenu = new DeliveryMenuModel();
    this.otherParams = OTHER_PARAMS;
    console.log(this.otherParams, OTHER_PARAMS);
    // this.loading = true;
    // this._globalService.dataBusChanged('pageLoading', true);
  }

  ngOnInit() {
    this.deliveryMenu =  this.deliveryMenuService.getDeliveryMenus();
    // this.companyService.getCompanies()
    //   .subscribe( data => {
    //     this.companies = data;
    //     this._globalService.dataBusChanged('pageLoading', false);
    //     this.loading = false;
    //   });
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
