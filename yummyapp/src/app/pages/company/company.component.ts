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
import {MenuEntityModel} from "../../model/menu-entity.model";
import {UtilsService} from "../../services/utils.service";
import {document} from "ngx-bootstrap";

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
  companies: CompanyModel[];
  filteredCompanies: CompanyModel[];
  deliveryCity: string = "";
  basket: MenuEntityModel[] = new Array<MenuEntityModel>();
  toUpIconOpacity: number = 0;
  selectedDishes: number[] =  new Array<number>();
  selectedKitches: number[] =  new Array<number>();
  selectedPayTypes: number[] =  new Array<number>();
  fastMenuTop: number = 290;

  constructor(private router: Router,private _authService: AuthService, private deliveryMenuService : DeliveryMenuService,
              private companyService: CompanyService, private _globalService: GlobalService, private utilService :UtilsService) {
    this.userRole = window.localStorage.getItem('userrole');
    this._authService.isAuthenticated();
    this.deliveryMenu = new DeliveryMenuModel();
    this.companies =  new Array<CompanyModel>();
    this.otherParams = OTHER_PARAMS;
  }

  ngOnInit() {
    this._globalService.data$.subscribe(data => {
      if (data.ev === 'data-loaded') {
        if ( data.value ){
          this.deliveryMenu =  this.companyService.getDeliveryMenus();
          this.companies = this.companyService.getCompaniesModel();
          this.filteredCompanies = this.companies;
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
    this.fastMenuTop = 290 - (event.srcElement.scrollTop/2);
    this._globalService.dataBusChanged('fast-menu-pos',this.fastMenuTop);
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

  selectDish( dish ){
    const index = this.selectedDishes.indexOf( dish.id );
    if (index != -1){
      this.selectedDishes.splice(index, 1);
    } else {
      this.selectedDishes.push( dish.id );
    }
    this.filterCompanies();
  }

  selectKitchen( kitchen ){
    const index = this.selectedKitches.indexOf( kitchen.id );
    if (index != -1){
      this.selectedKitches.splice(index, 1);
    } else {
      this.selectedKitches.push( kitchen.id );
    }
    this.filterCompanies();
  }

  selectPayType( id ){
    const index = this.selectedPayTypes.indexOf( id );
    if (index != -1){
      this.selectedPayTypes.splice(index, 1);
    } else {
      this.selectedPayTypes.push( id );
    }
    this.filterCompanies();
  }


  filterCompanies(){
    this.filteredCompanies = new Array<CompanyModel>();
    this.companies.forEach( company => {
      let canAdd = true;
      // FILTER DISH
      if ( this.selectedDishes.length > 0 ){
        canAdd = this.utilService.isArrayСrossed( company.menuCategoiesIds, this.selectedDishes );
      }
      // FILTER KITCHEN
      if ( canAdd && this.selectedKitches.length > 0 ){
        canAdd =  this.utilService.isArrayСrossed( company.menuTypeIds, this.selectedKitches );
      }
      // FILTER PAY TYPES
      if ( canAdd && this.selectedPayTypes.length > 0 ){
        canAdd = this.utilService.isPayTypeEnabled(this.selectedPayTypes, company);
      }
      if( canAdd ){
        this.filteredCompanies.push( company );
      }
    })
  }

  clearFilters(){
    this.selectedDishes =  new Array<number>();
    this.selectedKitches =  new Array<number>();
    this.selectedPayTypes =  new Array<number>();
    this.filterCompanies();
    for( let idx = 0; idx < this.deliveryMenu.menuCategories.length; idx++ ){
      document.getElementById("dish-checkbox-"+idx).checked = false;
    }
    for( let idx = 0; idx < this.deliveryMenu.menuTypes.length; idx++ ){
      document.getElementById("kitchen-checkbox-"+idx).checked = false;
    }
    for( let idx = 1; idx < 4; idx++ ){
      document.getElementById("pay-checkbox-"+idx).checked = false;
    }
    for( let idx = 0; idx < this.otherParams.length; idx++ ){
      document.getElementById("other-checkbox-"+idx).checked = false;
    }
    this.moveToTop();
  }

  showClearFilter(): boolean{
    return this.selectedPayTypes.length > 0 ||
              this.selectedDishes.length > 0 ||
                this.selectedKitches.length > 0;
  }

}
