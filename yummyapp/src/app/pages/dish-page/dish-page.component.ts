import { Component, OnInit } from '@angular/core';
import {GlobalService} from "../../shared/services/global.service";
import {DeliveryMenuModel} from "../../model/delivery-menu.model";
import {CompanyModel} from "../../model/company.model";
import {MenuEntityModel} from "../../model/menu-entity.model";
import {CompanyService} from "../../services/company.service";
import {MenuCategoryModel} from "../../model/menu-category.model";
import swal from "sweetalert2";
import {TripleEntityModel} from "../../model/triple-entity.model";
import {document} from "ngx-bootstrap";
import {Router} from "@angular/router";

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
  fastMenuTop: number = 210;
  toUpIconOpacity: number = 0;
  categoriesListView: string = 'Показать Все Блюда';
  companyListView: string = 'Показать Все Заведения';
  menuEntities: MenuEntityModel[] = new Array<MenuEntityModel>();
  tripleEntities: TripleEntityModel[] = new Array<TripleEntityModel>();
  selectedDish: MenuCategoryModel = new MenuCategoryModel();
  selectedCompanies: number[] =  new Array<number>();
  dishCount: number = 0;
  dishCountStr: string = "";
  showPepsi: boolean = false;
  selectedFastMenu: number;

  constructor( private _globalService: GlobalService, private companyService: CompanyService,
               private router: Router) {
    this.deliveryMenu = new DeliveryMenuModel();
    this.companies =  new Array<CompanyModel>();
    this.selectedDish.displayName='Пицца';
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
    this.loadEntities( 1 );
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
    this.fastMenuTop = 210 - (event.srcElement.scrollTop/2);
    this._globalService.dataBusChanged('fast-menu-pos',this.fastMenuTop);
    if ( event.srcElement.scrollTop > 800 ){
      this.toUpIconOpacity = 1;
    } else {
      this.toUpIconOpacity = 0;
    }
  }

  selectDish( dish ){
    this.selectedDish = dish;
    this.loadEntities( dish.id );
    this.moveToTop();
  }

  selectCompany( company ){
    const index = this.selectedCompanies.indexOf( company.id );
    if (index != -1){
      this.selectedCompanies.splice(index, 1);
    } else {
      this.selectedCompanies.push( company.id );
    }
    let selectedEntities = new Array<MenuEntityModel>();
    if( this.selectedCompanies.length == 0 ){
      selectedEntities = this.menuEntities;
    } else {
       this.menuEntities.forEach( entity =>{
         this.selectedCompanies.forEach( companyId => {
          if( companyId == (+entity.companyId)  ){
            selectedEntities.push( entity );
        }
        });
      });
    }
    this.fillTripleMenuEntity( selectedEntities );
    this.moveToTop();
  }

  moveToTop(){
    document.getElementById("top").scrollIntoView({behavior: "smooth", block: "start"});
  }

  showCompanyDetail(companyId){
    window.localStorage.setItem('companyId',companyId);
    let companyName = this.companyService.getCompanyById( companyId ).companyName;
    this.router.navigate(['pages/company-detail/'+companyName]);
  }

  loadEntities( dishId ){
    this.showPepsi = true;
    this.companyService.getCompanyDishes( this.deliveryCity, dishId ).subscribe(data => {
      this.showPepsi = false;
      if (data.status === 200) {
        this.menuEntities = data.result.dishes;
        this.fillTripleMenuEntity( this.menuEntities );
        this.selectedCompanies =  new Array<number>();
        this.companies.forEach( company =>{
          document.getElementById("company-checkbox-"+company.id).checked = false;
        });
        this._globalService.dataBusChanged( 'fast-menu-select', dishId );
      } else {
        swal({
          type: 'error',
          title: data.status,
          text: data.message,
        });
      }
    });
  }

  fillTripleMenuEntity( sourceEntities ){
    this.dishCount = sourceEntities.length;
    let suffix=" блюд";
    let mod = this.dishCount % 10;
    if ( this.dishCount < 10 || this.dishCount > 15 ){
      if( mod == 1 ){
        suffix = ' блюдо';
      }
      if( mod == 2 || mod == 3 || mod == 4){
        suffix = ' блюда';
      }
    }
    this.dishCountStr = this.selectedDish.displayName+" - "+this.dishCount+suffix;
    this.tripleEntities =  new Array<TripleEntityModel>();
    for( let idx = 0; idx < this.dishCount; idx +=3){
      let tripleEntity = new TripleEntityModel();
      tripleEntity.entityOne = this.getTripleMenuEntity( sourceEntities[idx] );
      tripleEntity.entityTwo = this.getTripleMenuEntity( sourceEntities[idx+1] );
      tripleEntity.entityThree = this.getTripleMenuEntity( sourceEntities[idx+2] );
      this.tripleEntities.push( tripleEntity );
    }
  }

  getTripleMenuEntity( menuEntity ){
    let result = null;
    if( menuEntity != undefined ){
      result = menuEntity;
      this.companies.forEach(company => {
        if ( menuEntity.companyId == company.id ){
          result.companyName = company.displayName;
        }
      });
    } else {
      result = new MenuEntityModel();
    }
    return result;
  }

  selectFastMenu( value ){
    let fastMenu = this.companyService.getFastMenuModel();

    switch ( value ){
      case 1 :
        this.selectedFastMenu = fastMenu.pizzaIds[1];
        break;
      case 2 :
        this.selectedFastMenu = fastMenu.shushiIds[0];
        break;
      case 3 :
        this.selectedFastMenu = fastMenu.burgerIds[0];
        break;
      case 4 :
        this.selectedFastMenu = fastMenu.grillIds[0];
        break;
      case 5 :
        this.selectedFastMenu = fastMenu.wokIds[0];
        break;
      default:
        break;
    }
    let dish = null;
    this.deliveryMenu.menuCategories.forEach(category => {
      if (this.selectedFastMenu == (+category.id) ) {
        dish = category;
      }
    });
    this.selectDish( dish );
    document.getElementById("dish-radio-"+dish.id).checked = true;
  }

  selectPayType(val){

  }

}
