import {Component, OnInit, ViewChild} from '@angular/core';
import {CompanyInfoModel} from "../../model/company-info.model";
import {MenuCategoryModel} from "../../model/menu-category.model";
import {MenuTypeModel} from "../../model/menu-type.model";
import {MenuEntityModel} from "../../model/menu-entity.model";
import {Router} from "@angular/router";
import {CompanyService} from "../../services/company.service";
import {GlobalService} from "../../shared/services/global.service";
import {CompanyShortModel} from "../../model/company-short.model";
import {TripleEntityModel} from "../../model/triple-entity.model";
import {TrackScrollDirective} from "../../directives/track-scroll";

@Component({
  selector: 'app-company-detail',
  templateUrl: './company-detail.component.html',
  styleUrls: ['./company-detail.component.scss']
})
export class CompanyDetailComponent implements OnInit {
  @ViewChild(TrackScrollDirective) scroll: TrackScrollDirective;

  companyId: string;
  logoImgSrc: string = '';
  companyInfo: CompanyInfoModel = null;
  selMenuType: MenuTypeModel = new MenuTypeModel();
  selMenuCategory: MenuCategoryModel = new MenuCategoryModel();
  loading: boolean = true;
  menuEntities: MenuEntityModel[] = new Array<MenuEntityModel>();
  menuTypes: MenuTypeModel[] = new Array<MenuTypeModel>();
  companyShort: CompanyShortModel =  new CompanyShortModel();
  tripleEntities: TripleEntityModel[] = new Array<TripleEntityModel>();

  deliveryCity: string;

  constructor(private router: Router,
              private companyService: CompanyService, private _globalService: GlobalService) {

    this.companyId = window.localStorage.getItem('companyId');
    this.companyShort = this.companyService.getCompanyShortById( this.companyId );
    this.deliveryCity = this.companyService.getDeliveryCity();
    this._globalService.dataBusChanged('pageLoading', true);
    this.selMenuType.id = '-1';
    this.selMenuCategory.id = '-1';
    this.selMenuCategory.displayName = 'Основное меню';
  }

  returnToChoice(){
    let link = 'pages/company';
    this._globalService.dataBusChanged('selected-link', link);
    this.router.navigate([link]);
  }

  ngOnInit() {
    this.companyService.getCompanyInfo(this.companyId)
      .subscribe(data => {
        this.companyInfo = data;
        this.menuTypes = data.menuTypes;
        this.menuEntities = data.menuEntities;
        this.menuEntities.forEach( entity =>{ entity.count = 0 } );
        for( let idx = 0; idx < this.menuEntities.length; idx +=3){
          let tripleEntity = new TripleEntityModel();
          tripleEntity.entityOne = this.menuEntities[idx] != undefined ? this.menuEntities[idx] : new MenuEntityModel();
          tripleEntity.entityTwo = this.menuEntities[idx+1] != undefined ? this.menuEntities[idx+1] : new MenuEntityModel();
          tripleEntity.entityThree = this.menuEntities[idx+2] != undefined ? this.menuEntities[idx+2] : new MenuEntityModel();
          this.tripleEntities.push( tripleEntity );
        }
        this.logoImgSrc = 'assets/images/logos/' + this.companyInfo.companyModel.logo;
        this._globalService.dataBusChanged('selected-link', null);
        this._globalService.dataBusChanged('pageLoading', false);
        this.loading = false;
      });
  }

  selectMenuCategory( menuType: MenuTypeModel, menuCategory: MenuCategoryModel ){
    this.selMenuType = menuType;
    this.selMenuCategory = menuCategory;
    this.tripleEntities = new Array<TripleEntityModel>();
    let selectedEntities = new Array<MenuEntityModel>();
    this.menuEntities.forEach( entity =>{
      if( entity.typeId == menuType.id && entity.categoryId == menuCategory.id ){
        selectedEntities.push( entity );
      }
    });
    for( let idx = 0; idx < selectedEntities.length; idx +=3){
      let tripleEntity = new TripleEntityModel();
      tripleEntity.entityOne = selectedEntities[idx] != undefined ? selectedEntities[idx] : new MenuEntityModel();
      tripleEntity.entityTwo = selectedEntities[idx+1] != undefined ? selectedEntities[idx+1] : new MenuEntityModel();
      tripleEntity.entityThree = selectedEntities[idx+2] != undefined ? selectedEntities[idx+2] : new MenuEntityModel();
      this.tripleEntities.push( tripleEntity );
    }
  }

  isMenuActive( id ){
    let active = false;
    if( this.selMenuCategory.id == id ){
      active = true;
    }
    return active;
  }

  track(value: number): void {
    console.log('NEW VAL',value);
  }

}
