import {Component, OnInit} from '@angular/core';

import {Router} from '@angular/router';

import {CompanyInfoModel} from '../../model/company-info.model';
import {MenuCategoryModel} from '../../model/menu-category.model';
import {CompanyService} from '../../services/company.service';
import {GlobalService} from '../../shared/services/global.service';
import {ParseMenuModel} from "../../model/parse-menu.model";
import {MenuEntityModel} from "../../model/menu-entity.model";
import {MenuTypeModel} from "../../model/menu-type.model";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-company-info',
  templateUrl: './company-info.component.html',
  styleUrls: ['./company-info.component.scss']
})
export class CompanyInfoComponent implements OnInit {
  companyId: string;
  btnBackImgSrc: string = 'assets/images/buttons/back.png';
  logoImgSrc: string = '';
  companyInfo: CompanyInfoModel = null;
  menuCategoryList: MenuCategoryModel[];
  selMenuType: MenuTypeModel = new MenuTypeModel();
  selMenuCategory: MenuCategoryModel = new MenuCategoryModel();
  loading: boolean = true;
  parseMenu: ParseMenuModel = null;
  updateParseMenu: ParseMenuModel = new ParseMenuModel();
  parseForm: FormGroup;
  menuEntities : MenuEntityModel[];
  /* pagination Info */
  pageSize = 4;
  pageNumber = 1;

  constructor(private router: Router,private formBuilder: FormBuilder,
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
        this._globalService.dataBusChanged('headerTitle', this.companyInfo.companyModel.displayName);
        this._globalService.dataBusChanged('companyUrl', this.companyInfo.companyModel.url);
        this._globalService.dataBusChanged('pageLoading', false);

        this.loading = false;
      });
  }

  menuTypeSelect( menuType) {
    menuType.menuOpen = !menuType.menuOpen;
    this.selMenuType = menuType;
    this.selMenuCategory =  new MenuCategoryModel();
    this.selMenuCategory.id = '-1';
    this._globalService.dataBusChanged('showIcon', true);
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
    this._globalService.dataBusChanged('menuType', menuType.displayName);
    this._globalService.dataBusChanged('menuCategory', menuCategory.displayName);
    this._globalService.dataBusChanged('showIcon', false);
    this.selMenuType = menuType;
    this.selMenuCategory = menuCategory;

    this.pageNumber = 1;
    this.companyService.getCompanyMenu( this.companyId, menuType.id, menuCategory.id ).subscribe(data => {
      this.parseMenu = data.parseMenu;
      this.updateParseMenu.id = this.parseMenu.id;
      this.updateParseMenu.companyId = this.parseMenu.companyId;
      this.updateParseMenu.typeId = this.parseMenu.typeId;
      this.updateParseMenu.categoryId = this.parseMenu.categoryId;
      this.menuEntities = data.menuEntities;
      this.parseForm = this.formBuilder.group({
        parseUrl: [ { value:this.parseMenu.parseUrl, disabled: true},Validators.compose([Validators.required])],
        tagTrash: [{value: this.parseMenu.tagTrash, disabled: true}, Validators.compose([Validators.required])],
        tagEndSection: [{value: this.parseMenu.tagEndSection, disabled: true}, Validators.compose([Validators.required])],
        tagName: [{value: this.parseMenu.tagName, disabled: true}, Validators.compose([Validators.required])],
        tagDescription: [{value: this.parseMenu.tagDescription,disabled: true}, Validators.compose([Validators.required])],
        tagImageUrl: [{value: this.parseMenu.tagImageUrl, disabled: true}, Validators.compose([Validators.required])],
        tagWeightOne: [{value: this.parseMenu.tagWeightOne, disabled: true}, Validators.compose([Validators.required])],
        tagSizeOne: [{value: this.parseMenu.tagSizeOne, disabled: true}, Validators.compose([Validators.required])],
        tagPriceOne: [{value: this.parseMenu.tagPriceOne, disabled: true}, Validators.compose([Validators.required])],
        tagWeightTwo: [{value: this.parseMenu.tagWeightTwo, disabled: true}],
        tagSizeTwo: [{value: this.parseMenu.tagSizeTwo, disabled: true}],
        tagPriceTwo: [{value: this.parseMenu.tagPriceTwo, disabled: true}],
        tagWeightThree: [{value: this.parseMenu.tagWeightThree,disabled: true}],
        tagSizeThree: [{value: this.parseMenu.tagSizeThree, disabled: true}],
        tagPriceThree: [{value: this.parseMenu.tagPriceThree, disabled: true}],
        tagWeightFour: [{value: this.parseMenu.tagWeightFour, disabled: true}],
        tagSizeFour: [{value: this.parseMenu.tagSizeFour, disabled: true}],
        tagPriceFour: [{value: this.parseMenu.tagPriceFour, disabled: true}]
      });
    });
  }

  pageChanged(pN: number): void {
    this.pageNumber = pN;
  }

  public back() {
    this.router.navigate(['pages/company']);
  }

  isControlHidden( controlName){
    return this.parseForm.get( controlName ).status === 'DISABLED';
  }

  inputControlClick( controlName ){
    for (let field in this.parseForm.controls) {
      this.parseForm.get(field).disable();
    }
    this.parseForm.get( controlName ).enable();
  }

  testParseModel(){
    this.updateParseMenu.parseUrl= this.parseForm.get('parseUrl').value;
      this.updateParseMenu.tagTrash= this.parseForm.get('tagTrash').value;
      this.updateParseMenu.tagEndSection= this.parseForm.get('tagEndSection').value;
      this.updateParseMenu.tagName= this.parseForm.get('tagName').value;
      this.updateParseMenu.tagDescription= this.parseForm.get('tagDescription').value;
      this.updateParseMenu.tagImageUrl= this.parseForm.get('tagImageUrl').value;
      this.updateParseMenu.tagWeightOne= this.parseForm.get('tagWeightOne').value;
      this.updateParseMenu.tagSizeOne= this.parseForm.get('tagSizeOne').value;
      this.updateParseMenu.tagPriceOne= this.parseForm.get('tagPriceOne').value;
      this.updateParseMenu.tagWeightTwo= this.parseForm.get('tagWeightTwo').value;
      this.updateParseMenu.tagSizeTwo= this.parseForm.get('tagSizeTwo').value;
      this.updateParseMenu.tagPriceTwo= this.parseForm.get('tagPriceTwo').value;
      this.updateParseMenu.tagWeightThree= this.parseForm.get('tagWeightThree').value;
      this.updateParseMenu.tagSizeThree= this.parseForm.get('tagSizeThree').value;
      this.updateParseMenu.tagPriceThree= this.parseForm.get('tagPriceThree').value;
      this.updateParseMenu.tagWeightFour= this.parseForm.get('tagWeightFour').value;
      this.updateParseMenu.tagSizeFour= this.parseForm.get('tagSizeFour').value;
      this.updateParseMenu.tagPriceFour= this.parseForm.get('tagPriceFour').value;
      this.companyService.testMenuPage( this.updateParseMenu ).subscribe(data => {
        console.log(data);
    });
  }

}
