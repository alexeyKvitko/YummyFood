import { Component, OnInit } from '@angular/core';
import {GlobalService} from "../../shared/services/global.service";
import {DeliveryMenuModel} from "../../model/delivery-menu.model";
import {CompanyModel} from "../../model/company.model";
import {MenuEntityModel} from "../../model/menu-entity.model";
import {CompanyService} from "../../services/company.service";
import {MenuCategoryModel} from "../../model/menu-category.model";

@Component({
  selector: 'app-dish-page',
  templateUrl: './dish-page.component.html',
  styleUrls: ['./dish-page.component.scss']
})
export class DishPageComponent implements OnInit {

  deliveryCity: string = "";
  deliveryMenu : DeliveryMenuModel;
  companies: CompanyModel[];
  basket: MenuEntityModel[] = new Array<MenuEntityModel>();
  fastMenuTop: number = 290;
  toUpIconOpacity: number = 0;
  categoriesListView: string = 'Показать Все Блюда';
  companyListView: string = 'Показать Все Заведения';
  menuEntities: MenuEntityModel[] = new Array<MenuEntityModel>();
  selectedDish: MenuCategoryModel;
  selectedCompany: CompanyModel;

  constructor( private _globalService: GlobalService, private companyService: CompanyService) {
    this.deliveryMenu = new DeliveryMenuModel();
    this.companies =  new Array<CompanyModel>();
  }

  ngOnInit() {
    this._globalService.data$.subscribe(data => {
      if (data.ev === 'data-loaded') {
        if ( data.value ){
          this.deliveryMenu =  this.companyService.getDeliveryMenus();
          this.companies = this.companyService.getCompaniesModel();
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
  }

  showCategoryItem( idx: number){
    if ( this.categoriesListView === 'Скрыть Все Блюда' ){
      return true;
    }
    if ( this.categoriesListView === 'Показать Все Блюда' && idx > 8){
      return false;
    }
    return true;
  }

  showCompanyItem( idx: number){
    if ( this.companyListView === 'Скрыть Все Заведения' ){
      return true;
    }
    if ( this.companyListView === 'Показать Все Заведения' && idx > 2){
      return false;
    }
    return true;
  }

  showAllCompanies(){
    this.companyListView = this.companyListView === 'Показать Все Заведения' ? 'Скрыть Все Заведения' : 'Показать Все Заведения';
  }

  showAllCategories(){
    this.categoriesListView = this.categoriesListView === 'Показать Все Блюда' ? 'Скрыть Все Блюда' : 'Показать Все Блюда';
  }

  showClearFilter(): boolean{
    return true;
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

  selectDish( dish ){
    this.selectedDish = dish;
  }

  selectCompany( company ){
    this.selectedCompany = company;
  }

  loadEntities(){

  }

}
