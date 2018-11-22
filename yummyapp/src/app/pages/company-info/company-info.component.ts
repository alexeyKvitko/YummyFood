import {Component, OnInit} from '@angular/core';

import {Router} from '@angular/router';

import {CompanyInfoModel} from '../../model/company-info.model';
import {MenuCategoryModel} from '../../model/menu-category.model';
import {CompanyService} from '../../services/company.service';
import {GlobalService} from '../../shared/services/global.service';
import {ParseMenuModel} from "../../model/parse-menu.model";
import {MenuEntityModel} from "../../model/menu-entity.model";
import {MenuTypeModel} from "../../model/menu-type.model";

@Component({
  selector: 'app-company-info',
  templateUrl: './company-info.component.html',
  styleUrls: ['./company-info.component.scss']
})
export class CompanyInfoComponent implements OnInit {
  companyId: string;
  btnBackImgSrc: string = 'assets/images/buttons/back.png';
  logoImgSrc: string = '';
  headerTitle: string;
  companyInfo: CompanyInfoModel = null;
  menuCategoryList: MenuCategoryModel[];
  selMenuType: MenuTypeModel = new MenuTypeModel();
  selMenuCategory: MenuCategoryModel = new MenuCategoryModel();
  loading: boolean = true;
  showIcon: boolean = true;
  parseMenu: ParseMenuModel = null;
  menuEntities : MenuEntityModel[];
  /* pagination Info */
  pageSize = 4;
  pageNumber = 1;

  constructor(private router: Router,
              private companyService: CompanyService, private _globalService: GlobalService) {
    this.companyId = window.localStorage.getItem('companyId');
    this._globalService.dataBusChanged('pageLoading', true);
    this.selMenuType.id ='-1';
    this.selMenuCategory.id = '-1';
  }


  ngOnInit() {
    this.companyService.getCompanyInfo(this.companyId)
      .subscribe(data => {
        this.companyInfo = data;
        this.logoImgSrc = 'assets/images/logos/' + this.companyInfo.companyModel.logo;
        this.headerTitle = this.companyInfo.companyModel.displayName;
        this._globalService.dataBusChanged('pageLoading', false);
        this.loading = false;
      });
  }

  menuTypeSelect( menuType) {
    menuType.menuOpen = !menuType.menuOpen;
    this.selMenuType = menuType;
    this.selMenuCategory =  new MenuCategoryModel();
    this.selMenuCategory.id = '-1';
    this.showIcon = true;
    this.menuEntities = null;
    let menuTypeIdx = -1;
    this.companyInfo.menuTypes.forEach(function ( value,idx ) {
      if ( value.id == menuType.id){
        menuTypeIdx = idx;
      }
    });
    this.menuCategoryList  = this.companyInfo.menuTypes[ menuTypeIdx ].menuCategories;
  }

  menuCategorySelect( menuType, menuCategory) {
    this.selMenuType = menuType;
    this.selMenuCategory = menuCategory;
    this.showIcon = false;
    this.pageNumber = 1;
    this.companyService.getCompanyMenu( this.companyId, menuType.id, menuCategory.id ).subscribe(data => {
      this.parseMenu = data.parseMenu;
      this.menuEntities = data.menuEntities;
    });
  }

  pageChanged(pN: number): void {
    this.pageNumber = pN;
  }

  public back() {
    this.router.navigate(['pages/company']);
  }

}
