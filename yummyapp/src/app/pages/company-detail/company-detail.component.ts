import { Component, OnInit } from '@angular/core';
import {CompanyInfoModel} from "../../model/company-info.model";
import {MenuCategoryModel} from "../../model/menu-category.model";
import {MenuTypeModel} from "../../model/menu-type.model";
import {MenuEntityModel} from "../../model/menu-entity.model";
import {Router} from "@angular/router";
import {CompanyService} from "../../services/company.service";
import {GlobalService} from "../../shared/services/global.service";
import {CompanyShortModel} from "../../model/company-short.model";

@Component({
  selector: 'app-company-detail',
  templateUrl: './company-detail.component.html',
  styleUrls: ['./company-detail.component.scss']
})
export class CompanyDetailComponent implements OnInit {

  companyId: string;
  logoImgSrc: string = '';
  companyInfo: CompanyInfoModel = null;
  menuCategoryList: MenuCategoryModel[];
  selMenuType: MenuTypeModel = new MenuTypeModel();
  selMenuCategory: MenuCategoryModel = new MenuCategoryModel();
  loading: boolean = true;
  menuEntities: MenuEntityModel[];
  menuTypes: MenuTypeModel[] = new Array<MenuTypeModel>();
  companyShort: CompanyShortModel =  new CompanyShortModel();
  deliveryCity: string;

  constructor(private router: Router,
              private companyService: CompanyService, private _globalService: GlobalService) {

    this.companyId = window.localStorage.getItem('companyId');
    this.companyShort = this.companyService.getCompanyShortById( this.companyId );
    this.deliveryCity = this.companyService.getDeliveryCity();
    this._globalService.dataBusChanged('pageLoading', true);
    this.selMenuType.id = '-1';
    this.selMenuCategory.id = '-1';

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
        this.logoImgSrc = 'assets/images/logos/' + this.companyInfo.companyModel.logo;
        this._globalService.dataBusChanged('selected-link', null);
        this._globalService.dataBusChanged('pageLoading', false);
        this.loading = false;
      });
  }

}
