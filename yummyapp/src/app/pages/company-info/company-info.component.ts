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
import { ClipboardService } from 'ngx-clipboard'
import swal from 'sweetalert2';
import {AuthService} from "../../services/auth.service";
import {NotificationsService} from "angular2-notifications";

@Component({
  selector: 'app-company-info',
  templateUrl: './company-info.component.html',
  styleUrls: ['./company-info.component.scss']
})
export class CompanyInfoComponent implements OnInit {
  companyId: string;
  logoImgSrc: string = '';
  companyInfo: CompanyInfoModel = null;
  menuCategoryList: MenuCategoryModel[];
  selMenuType: MenuTypeModel = new MenuTypeModel();
  selMenuCategory: MenuCategoryModel = new MenuCategoryModel();
  loading: boolean = true;
  parseMenu: ParseMenuModel = new ParseMenuModel();
  updateParseMenu: ParseMenuModel = new ParseMenuModel();
  parseForm: FormGroup;
  menuEntities: MenuEntityModel[];
  testEntities: MenuEntityModel[];
  /* pagination Info */
  pageSize = 4;
  pageNumber = 1;
  notiOptions = {
    position: ["top", "right"],
    timeOut: 1500,
    showProgressBar: false,
    lastOnBottom: true
  };

  constructor(private router: Router, private formBuilder: FormBuilder,private _clipboardService: ClipboardService,
              private _authService: AuthService,private _notificationsService: NotificationsService,
              private companyService: CompanyService, private _globalService: GlobalService) {
    this._authService.isAuthenticated();
    this.companyId = window.localStorage.getItem('companyId');
    this._globalService.dataBusChanged('pageLoading', true);
    this.selMenuType.id = '-1';
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

  menuTypeSelect(menuType) {
    menuType.menuOpen = !menuType.menuOpen;
    this.selMenuType = menuType;
    this.selMenuCategory = new MenuCategoryModel();
    this.selMenuCategory.id = '-1';
    this._globalService.dataBusChanged('showIcon', true);
    this.menuEntities = null;
    let menuTypeIdx = -1;
    this.companyInfo.menuTypes.forEach(function (value, idx) {
      if (value.id == menuType.id) {
        menuTypeIdx = idx;
      }
    });
    this.menuCategoryList = this.companyInfo.menuTypes[menuTypeIdx].menuCategories;
  }

  menuCategorySelect(menuType, menuCategory) {
    this._globalService.dataBusChanged('menuType', menuType.displayName);
    this._globalService.dataBusChanged('menuCategory', menuCategory.displayName);
    this._globalService.dataBusChanged('showIcon', false);
    this.selMenuType = menuType;
    this.selMenuCategory = menuCategory;

    this.pageNumber = 1;
    this.companyService.getCompanyMenu(this.companyId, menuType.id, menuCategory.id).subscribe(data => {
      this.parseMenu = data.parseMenu;
      this.updateParseMenu.id = this.parseMenu.id;
      this.updateParseMenu.companyId = this.parseMenu.companyId;
      this.updateParseMenu.typeId = this.parseMenu.typeId;
      this.updateParseMenu.categoryId = this.parseMenu.categoryId;
      this.menuEntities = data.menuEntities;
      let tagNameSplitted = this.splitValue( this.parseMenu.tagName );
      let tagDescSplitted = this.splitValue( this.parseMenu.tagDescription );
      let tagImgUrlSplitted = this.splitValue( this.parseMenu.tagImageUrl );
      let tagWeightOneSplitted = this.splitValue( this.parseMenu.tagWeightOne );
      let tagSizeOneSplitted = this.splitValue( this.parseMenu.tagSizeOne );
      let tagPriceOneSplitted = this.splitValue( this.parseMenu.tagPriceOne );
      let tagWeightTwoSplitted = this.splitValue( this.parseMenu.tagWeightTwo );
      let tagSizeTwoSplitted = this.splitValue( this.parseMenu.tagSizeTwo );
      let tagPriceTwoSplitted = this.splitValue( this.parseMenu.tagPriceTwo );
      let tagWeightThreeSplitted = this.splitValue( this.parseMenu.tagWeightThree );
      let tagSizeThreeSplitted = this.splitValue( this.parseMenu.tagSizeThree );
      let tagPriceThreeSplitted = this.splitValue( this.parseMenu.tagPriceThree );
      let tagWeightFourSplitted = this.splitValue( this.parseMenu.tagWeightFour );
      let tagSizeFourSplitted = this.splitValue( this.parseMenu.tagSizeFour );
      let tagPriceFourSplitted = this.splitValue( this.parseMenu.tagPriceFour );

      this.parseForm = this.formBuilder.group({
        prefixUrl: [{value: this.parseMenu.prefixUrl, disabled: true}],
        parseUrl: [{value: this.parseMenu.parseUrl, disabled: true}, Validators.compose([Validators.required])],
        tagTrash: [{value: this.parseMenu.tagTrash, disabled: true}, Validators.compose([Validators.required])],
        tagEndSection: [{value: this.parseMenu.tagEndSection,disabled: true}, Validators.compose([Validators.required])],
        htmlResponse: [{value: this.parseMenu.htmlResponse,disabled: true}, Validators.compose([Validators.required])],
        
        tagNameStart: [{value: tagNameSplitted[0], disabled: true}, Validators.compose([Validators.required])],
        tagNameEnd:[{value: tagNameSplitted[1], disabled: true}, Validators.compose([Validators.required])],
        tagNameDirection:[{value: tagNameSplitted[2], disabled: true}],

        tagDescStart: [{value: tagDescSplitted[0], disabled: true}, Validators.compose([Validators.required])],
        tagDescEnd:[{value: tagDescSplitted[1], disabled: true}, Validators.compose([Validators.required])],
        tagDescDirection:[{value: tagDescSplitted[2], disabled: true}],

        tagImgUrlStart: [{value: tagImgUrlSplitted[0], disabled: true}, Validators.compose([Validators.required])],
        tagImgUrlEnd:[{value: tagImgUrlSplitted[1], disabled: true}, Validators.compose([Validators.required])],
        tagImgUrlDirection:[{value: tagImgUrlSplitted[2], disabled: true}],
        
        tagWeightOneStart: [{value: tagWeightOneSplitted[0], disabled: true}, Validators.compose([Validators.required])],
        tagWeightOneEnd:[{value: tagWeightOneSplitted[1], disabled: true}, Validators.compose([Validators.required])],
        tagWeightOneDirection:[{value: tagWeightOneSplitted[2], disabled: true}],
        tagSizeOneStart: [{value: tagSizeOneSplitted[0], disabled: true}, Validators.compose([Validators.required])],
        tagSizeOneEnd:[{value: tagSizeOneSplitted[1], disabled: true}, Validators.compose([Validators.required])],
        tagSizeOneDirection:[{value: tagSizeOneSplitted[2], disabled: true}],
        tagPriceOneStart: [{value: tagPriceOneSplitted[0], disabled: true}, Validators.compose([Validators.required])],
        tagPriceOneEnd:[{value: tagPriceOneSplitted[1], disabled: true}, Validators.compose([Validators.required])],
        tagPriceOneDirection:[{value: tagPriceOneSplitted[2], disabled: true}],

        tagWeightTwoStart: [{value: tagWeightTwoSplitted[0], disabled: true}],
        tagWeightTwoEnd:[{value: tagWeightTwoSplitted[1], disabled: true}],
        tagWeightTwoDirection:[{value: tagWeightTwoSplitted[2], disabled: true}],
        tagSizeTwoStart: [{value: tagSizeTwoSplitted[0], disabled: true}],
        tagSizeTwoEnd:[{value: tagSizeTwoSplitted[1], disabled: true}],
        tagSizeTwoDirection:[{value: tagSizeTwoSplitted[2], disabled: true}],
        tagPriceTwoStart: [{value: tagPriceTwoSplitted[0], disabled: true}],
        tagPriceTwoEnd:[{value: tagPriceTwoSplitted[1], disabled: true}],
        tagPriceTwoDirection:[{value: tagPriceTwoSplitted[2], disabled: true}],

        tagWeightThreeStart: [{value: tagWeightThreeSplitted[0], disabled: true}],
        tagWeightThreeEnd:[{value: tagWeightThreeSplitted[1], disabled: true}],
        tagWeightThreeDirection:[{value: tagWeightThreeSplitted[2], disabled: true}],
        tagSizeThreeStart: [{value: tagSizeThreeSplitted[0], disabled: true}],
        tagSizeThreeEnd:[{value: tagSizeThreeSplitted[1], disabled: true}],
        tagSizeThreeDirection:[{value: tagSizeThreeSplitted[2], disabled: true}],
        tagPriceThreeStart: [{value: tagPriceThreeSplitted[0], disabled: true}],
        tagPriceThreeEnd:[{value: tagPriceThreeSplitted[1], disabled: true}],
        tagPriceThreeDirection:[{value: tagPriceThreeSplitted[2], disabled: true}],

        tagWeightFourStart: [{value: tagWeightFourSplitted[0], disabled: true}],
        tagWeightFourEnd:[{value: tagWeightFourSplitted[1], disabled: true}],
        tagWeightFourDirection:[{value: tagWeightFourSplitted[2], disabled: true}],
        tagSizeFourStart: [{value: tagSizeFourSplitted[0], disabled: true}],
        tagSizeFourEnd:[{value: tagSizeFourSplitted[1], disabled: true}],
        tagSizeFourDirection:[{value: tagSizeFourSplitted[2], disabled: true}],
        tagPriceFourStart: [{value: tagPriceFourSplitted[0], disabled: true}],
        tagPriceFourEnd:[{value: tagPriceFourSplitted[1], disabled: true}],
        tagPriceFourDirection:[{value: tagPriceFourSplitted[2], disabled: true}],
      });

    });
  }

  splitValue( val ){
    let splitted = null;
    if ( val != null ){
      splitted = val.split('~');
    } else {
      splitted = ['','','f'];
    }
    return splitted;
  }

  pageChanged(pN: number): void {
    this.pageNumber = pN;
  }

  isControlHidden(controlName) {
    return this.parseForm.get(controlName).status === 'DISABLED';
  }

  isForwardDirection(controlName) {
    return this.parseForm.get(controlName).value === 'f';
  }

  changeDirection(controlName){
    let val = this.parseForm.get(controlName).value;
    this.parseForm.get(controlName).setValue( val === 'f' ? 'b' : 'f');
  }

  undoControl( controlName ){
    return this.parseForm.get(controlName).setValue( this.getByName( controlName) );
  }

  undoTag( controlName ){
    if ( this.getByName( controlName) == null || this.getByName( controlName) === ''){
      return;
    }
    let undoValSplitted = (this.getByName( controlName) ).split('~');
    this.parseForm.get(controlName+'Start').setValue( undoValSplitted[0] );
    this.parseForm.get(controlName+'End').setValue( undoValSplitted[1] );
    this.parseForm.get(controlName+'Direction').setValue( undoValSplitted[2] );
  }

  copyControl(controlName){
    let txt = this.parseForm.get(controlName).value;
    this._clipboardService.copyFromContent( txt );
    this._notificationsService.success('Скопировано( '+txt.substr(1,10)+'... )');
  }

  copyTag(controlName){
    let txt = this.concatTagValues(controlName);
    this._clipboardService.copyFromContent( txt );
    this._notificationsService.success('Скопировано( '+txt.substr(1,10)+'... )');
  }

  inputControlClick(controlName,isSplit) {
    for (let field in this.parseForm.controls) {
      this.parseForm.get(field).disable();
    }
    if ( isSplit === 'split' ){
      this.parseForm.get(controlName+'Start').enable();
      this.parseForm.get(controlName+'End').enable();
    } else {
      this.parseForm.get(controlName).enable();
    }

  }

  convertFormToModel(){
    this.updateParseMenu.prefixUrl = this.parseForm.get('prefixUrl').value;
    this.updateParseMenu.parseUrl = this.parseForm.get('parseUrl').value;
    this.updateParseMenu.tagTrash = this.parseForm.get('tagTrash').value;
    this.updateParseMenu.tagEndSection = this.parseForm.get('tagEndSection').value;
    
    this.updateParseMenu.tagName = this.concatTagValues('tagName');
    this.updateParseMenu.tagDescription = this.concatTagValues('tagDesc');
    this.updateParseMenu.tagImageUrl = this.concatTagValues('tagImgUrl');
    this.updateParseMenu.tagWeightOne = this.concatTagValues('tagWeightOne');
    this.updateParseMenu.tagSizeOne = this.concatTagValues('tagSizeOne');
    this.updateParseMenu.tagPriceOne = this.concatTagValues('tagPriceOne');
    this.updateParseMenu.tagSizeTwo = this.concatTagValues('tagSizeTwo');
    this.updateParseMenu.tagPriceTwo = this.concatTagValues('tagPriceTwo');
    this.updateParseMenu.tagWeightThree = this.concatTagValues('tagWeightThree');
    this.updateParseMenu.tagSizeThree = this.concatTagValues('tagSizeThree');
    this.updateParseMenu.tagPriceThree = this.concatTagValues('tagPriceThree');
    this.updateParseMenu.tagWeightFour = this.concatTagValues('tagWeightFour');
    this.updateParseMenu.tagSizeFour = this.concatTagValues('tagSizeFour');
    this.updateParseMenu.tagPriceFour = this.concatTagValues('tagPriceFour');
  }
  
  concatTagValues( tag ){
    let val = this.parseForm.get( tag+'Start').value+"~"+
      this.parseForm.get( tag+'End').value+"~"+
      this.parseForm.get(tag+'Direction').value;
    return val
  }

  testParseModel() {
    this._globalService.dataBusChanged('pageLoading', true);
    this.convertFormToModel();
    this.companyService.testMenuPage(this.updateParseMenu).subscribe(data => {
      this.parseForm.get('htmlResponse').setValue( data.parseMenu.htmlResponse );
      this.testEntities = data.menuEntities;
      this._globalService.dataBusChanged('pageLoading', false);
    });
  }

  saveParseModel(){
    this._globalService.dataBusChanged('pageLoading', true);
    this.convertFormToModel();
    this.companyService.saveParseModel(this.updateParseMenu).subscribe(data => {
      this.menuCategorySelect( this.selMenuType, this.selMenuCategory );
      if ( data.status === 200 ){
        swal('Обновление данных, успешно');
      } else {
        swal({
          type: 'error',
          title: data.status,
          text: data.message,
        });
      }

      this._globalService.dataBusChanged('pageLoading', false);
    });
  }

  public getByName( name: string): string {
    let value = null;
    switch ( name ) {
      case 'id': {
        value = this.parseMenu.id;
        break;}
      case 'companyId': {
        value = this.parseMenu.companyId;
        break; }
      case 'typeId': {
        value = this.parseMenu.typeId;
        break;}
      case 'categoryId': {
        value = this.parseMenu.categoryId;
        break;}
      case 'htmlResponse': {
        value = this.parseMenu.htmlResponse;
        break;}
      case 'parseUrl': {
        value = this.parseMenu.parseUrl;
        break;}
      case 'prefixUrl': {
        value = this.parseMenu.prefixUrl;
        break;}
      case 'tagTrash': {
        value = this.parseMenu.tagTrash;
        break;}
      case 'tagEndSection': {
        value = this.parseMenu.tagEndSection;
        break;}
      case 'tagName': {
        value = this.parseMenu.tagName;
        break;}
      case 'tagDescription': {
        value = this.parseMenu.tagDescription;
        break;}
      case 'tagImageUrl': {
        value = this.parseMenu.tagImageUrl;
        break;}
      case 'tagWeightOne': {
        value = this.parseMenu.tagWeightOne;
        break;}
      case 'tagSizeOne': {
        value = this.parseMenu.tagSizeOne;
        break;}
      case 'tagPriceOne': {
        value = this.parseMenu.tagPriceOne;
        break;}
      case 'tagWeightTwo': {
        value = this.parseMenu.tagWeightTwo;
        break;}
      case 'tagSizeTwo': {
        value = this.parseMenu.tagSizeTwo;
        break;}
      case 'tagPriceTwo': {
        value = this.parseMenu.tagPriceTwo;
        break;}
      case 'tagWeightThree': {
        value = this.parseMenu.tagWeightThree;
        break;}
      case 'tagSizeThree': {
        value = this.parseMenu.tagSizeThree;
        break;}
      case 'tagPriceThree': {
        value = this.parseMenu.tagPriceThree;
        break;}
      case 'tagWeightFour': {
        value = this.parseMenu.tagWeightFour;
        break;}
      case 'tagSizeFour': {
        value = this.parseMenu.tagSizeFour;
        break;}
      case 'tagPriceFour': {
        value = this.parseMenu.tagPriceFour;
        break;}
      default:
        break;
    }
    return value;
  }


}
