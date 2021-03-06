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
import {CopyParseDataModel} from "../../model/copy-parse-data.model";
import {ParseResultModel} from "../../model/parse-result";

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
  copyParseData: CopyParseDataModel = new CopyParseDataModel();
  parseForm: FormGroup;
  menuEntities: MenuEntityModel[];
  testEntities: MenuEntityModel[];
  dishes =  new Array<any>();
  selectedDish: string;
  saveParseMenuDisabled: boolean = true;
  showDialog: boolean = false;
  showParseTab: boolean = false;
  testDialogHeader: string;

  pageNumber = 1;
  notiOptions = {
    position: ["top", "right"],
    timeOut: 1500,
    showProgressBar: false,
    lastOnBottom: true
  };
   directions = [
     { display:'ВПД', value:'f' },
     { display:'НЗД', value:'b' },
     { display:'ТЧН', value:'i' },
   ];

   entries = [1,2,3,4,5,6,7,8,9,10];

  constructor(private router: Router, private formBuilder: FormBuilder,private _clipboardService: ClipboardService,
              private _authService: AuthService,private _notificationsService: NotificationsService,
              private companyService: CompanyService, private _globalService: GlobalService) {
    this._authService.isAuthenticated();
    this.companyId = window.localStorage.getItem('companyId');
    this._globalService.dataBusChanged('pageLoading', true);
    this.selMenuType.id = '-1';
    this.selMenuCategory.id = '-1';
    this.parseMenu.parseResult = new ParseResultModel();
  }


  ngOnInit() {
    this.companyService.getCompanyInfo(this.companyId)
      .subscribe(data => {
        this.companyInfo = data;
        this.companyInfo.menuTypes.forEach( menuType =>{
          menuType.menuCategories.forEach( menuCategory => {
            this.dishes.push({
              menuType: menuType,
              menuCategory: menuCategory
            });
          });
        });
        let dish = this.dishes[0];
        this.selectedDish = dish.menuType.id+"/"+dish.menuCategory.id+"/"
                              +dish.menuType.displayName+"/"+dish.menuCategory.displayName;
        this.logoImgSrc = 'assets/images/logos/' + this.companyInfo.companyModel.logo;
        this._globalService.dataBusChanged('headerTitle', this.companyInfo.companyModel.displayName);
        this._globalService.dataBusChanged('companyUrl', this.companyInfo.companyModel.url);
        this._globalService.dataBusChanged('pageLoading', false);
        this.loading = false;
        this.fillForm();
        this.showParseTab = true;
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
    this.showParseTab = true;

    this.pageNumber = 1;
    this.companyService.getCompanyMenu(this.companyId, menuType.id, menuCategory.id).subscribe(data => {
      this.parseMenu = data.parseMenu;
      this.copyParseData.companyId = this.companyId;
      this.copyParseData.toMenuTypeId = this.parseMenu.typeId;
      this.copyParseData.toMenuCategoryId = this.parseMenu.categoryId;
      this.updateParseMenu.id = this.parseMenu.id;
      this.updateParseMenu.companyId = this.parseMenu.companyId;
      this.updateParseMenu.typeId = this.parseMenu.typeId;
      this.updateParseMenu.categoryId = this.parseMenu.categoryId;
      this.menuEntities = data.menuEntities;
      this.testEntities = [];
      this.fillForm();
    });
  }

  fillForm(){
    this.saveParseMenuDisabled = true;
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
      parseUrlTwo: [{value: this.parseMenu.parseUrlTwo, disabled: true}, Validators.compose([Validators.required])],
      parseUrlThree: [{value: this.parseMenu.parseUrlThree, disabled: true}, Validators.compose([Validators.required])],
      parseUrlFour: [{value: this.parseMenu.parseUrlFour, disabled: true}, Validators.compose([Validators.required])],
      tagTrash: [{value: this.parseMenu.tagTrash, disabled: true}, Validators.compose([Validators.required])],
      tagEndSection: [{value: this.parseMenu.tagEndSection,disabled: true}, Validators.compose([Validators.required])],
      htmlResponse: [{value: this.parseMenu.htmlResponse,disabled: true}, Validators.compose([Validators.required])],
      errorSection: [{value: this.parseMenu.parseResult.section,disabled: true}, Validators.compose([Validators.required])],

      tagNameStart: [{value: tagNameSplitted[0], disabled: true}, Validators.compose([Validators.required])],
      tagNameEnd:[{value: tagNameSplitted[1], disabled: true}, Validators.compose([Validators.required])],
      tagNameDirection:[{value: tagNameSplitted[2], disabled: true}],
      tagNameEntry:[{value: tagNameSplitted[3], disabled: true}],

      tagDescStart: [{value: tagDescSplitted[0], disabled: true}, Validators.compose([Validators.required])],
      tagDescEnd:[{value: tagDescSplitted[1], disabled: true}, Validators.compose([Validators.required])],
      tagDescDirection:[{value: tagDescSplitted[2], disabled: true}],
      tagDescEntry:[{value: tagDescSplitted[3], disabled: true}],

      tagImgUrlStart: [{value: tagImgUrlSplitted[0], disabled: true}, Validators.compose([Validators.required])],
      tagImgUrlEnd:[{value: tagImgUrlSplitted[1], disabled: true}, Validators.compose([Validators.required])],
      tagImgUrlDirection:[{value: tagImgUrlSplitted[2], disabled: true}],
      tagImgUrlEntry:[{value: tagImgUrlSplitted[3], disabled: true}],

      tagWeightOneStart: [{value: tagWeightOneSplitted[0], disabled: true}, Validators.compose([Validators.required])],
      tagWeightOneEnd:[{value: tagWeightOneSplitted[1], disabled: true}, Validators.compose([Validators.required])],
      tagWeightOneDirection:[{value: tagWeightOneSplitted[2], disabled: true}],
      tagWeightOneEntry:[{value: tagWeightOneSplitted[3], disabled: true}],
      tagSizeOneStart: [{value: tagSizeOneSplitted[0], disabled: true}, Validators.compose([Validators.required])],
      tagSizeOneEnd:[{value: tagSizeOneSplitted[1], disabled: true}, Validators.compose([Validators.required])],
      tagSizeOneDirection:[{value: tagSizeOneSplitted[2], disabled: true}],
      tagSizeOneEntry:[{value: tagSizeOneSplitted[3], disabled: true}],
      tagPriceOneStart: [{value: tagPriceOneSplitted[0], disabled: true}, Validators.compose([Validators.required])],
      tagPriceOneEnd:[{value: tagPriceOneSplitted[1], disabled: true}, Validators.compose([Validators.required])],
      tagPriceOneDirection:[{value: tagPriceOneSplitted[2], disabled: true}],
      tagPriceOneEntry:[{value: tagPriceOneSplitted[3], disabled: true}],

      tagWeightTwoStart: [{value: tagWeightTwoSplitted[0], disabled: true}],
      tagWeightTwoEnd:[{value: tagWeightTwoSplitted[1], disabled: true}],
      tagWeightTwoDirection:[{value: tagWeightTwoSplitted[2], disabled: true}],
      tagWeightTwoEntry:[{value: tagWeightTwoSplitted[3], disabled: true}],
      tagSizeTwoStart: [{value: tagSizeTwoSplitted[0], disabled: true}],
      tagSizeTwoEnd:[{value: tagSizeTwoSplitted[1], disabled: true}],
      tagSizeTwoDirection:[{value: tagSizeTwoSplitted[2], disabled: true}],
      tagSizeTwoEntry:[{value: tagSizeTwoSplitted[3], disabled: true}],
      tagPriceTwoStart: [{value: tagPriceTwoSplitted[0], disabled: true}],
      tagPriceTwoEnd:[{value: tagPriceTwoSplitted[1], disabled: true}],
      tagPriceTwoDirection:[{value: tagPriceTwoSplitted[2], disabled: true}],
      tagPriceTwoEntry:[{value: tagPriceTwoSplitted[3], disabled: true}],

      tagWeightThreeStart: [{value: tagWeightThreeSplitted[0], disabled: true}],
      tagWeightThreeEnd:[{value: tagWeightThreeSplitted[1], disabled: true}],
      tagWeightThreeDirection:[{value: tagWeightThreeSplitted[2], disabled: true}],
      tagWeightThreeEntry:[{value: tagWeightThreeSplitted[3], disabled: true}],
      tagSizeThreeStart: [{value: tagSizeThreeSplitted[0], disabled: true}],
      tagSizeThreeEnd:[{value: tagSizeThreeSplitted[1], disabled: true}],
      tagSizeThreeDirection:[{value: tagSizeThreeSplitted[2], disabled: true}],
      tagSizeThreeEntry:[{value: tagSizeThreeSplitted[3], disabled: true}],
      tagPriceThreeStart: [{value: tagPriceThreeSplitted[0], disabled: true}],
      tagPriceThreeEnd:[{value: tagPriceThreeSplitted[1], disabled: true}],
      tagPriceThreeDirection:[{value: tagPriceThreeSplitted[2], disabled: true}],
      tagPriceThreeEntry:[{value: tagPriceThreeSplitted[3], disabled: true}],

      tagWeightFourStart: [{value: tagWeightFourSplitted[0], disabled: true}],
      tagWeightFourEnd:[{value: tagWeightFourSplitted[1], disabled: true}],
      tagWeightFourDirection:[{value: tagWeightFourSplitted[2], disabled: true}],
      tagWeightFourEntry:[{value: tagWeightFourSplitted[3], disabled: true}],
      tagSizeFourStart: [{value: tagSizeFourSplitted[0], disabled: true}],
      tagSizeFourEnd:[{value: tagSizeFourSplitted[1], disabled: true}],
      tagSizeFourDirection:[{value: tagSizeFourSplitted[2], disabled: true}],
      tagSizeFourEntry:[{value: tagSizeFourSplitted[3], disabled: true}],
      tagPriceFourStart: [{value: tagPriceFourSplitted[0], disabled: true}],
      tagPriceFourEnd:[{value: tagPriceFourSplitted[1], disabled: true}],
      tagPriceFourDirection:[{value: tagPriceFourSplitted[2], disabled: true}],
      tagPriceFourEntry:[{value: tagPriceFourSplitted[3], disabled: true}],
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

  isControlHidden(controlName) {
    let exist = false;
    for (let field in this.parseForm.controls) {
      if( field === controlName ){
        exist = true;
      }
    }
    if (!exist){
      controlName = controlName+'Start';
    }
    return this.parseForm.get(controlName).status === 'DISABLED';
  }

  isForwardDirection(controlName) {
    return this.parseForm.get(controlName).value === 'f';
  }

  tagSelect(controlName,value){
    this.parseForm.get(controlName).setValue( value );
  }

  undoControl( controlName ){
    return this.parseForm.get(controlName).setValue( '' );
  }

  undoTag( controlName ){
    if ( this.getByName( controlName) == null || this.getByName( controlName) === ''){
      return;
    }
    // let undoValSplitted = (this.getByName( controlName) ).split('~');
    this.parseForm.get(controlName+'Start').setValue( '' );
    this.parseForm.get(controlName+'End').setValue( '' );
    this.parseForm.get(controlName+'Direction').setValue( 'f' );
    this.parseForm.get(controlName+'Entry').setValue( 1 );
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
    var status = null;
    if ( isSplit === 'split' ){
      status = this.parseForm.get( controlName+'Start' ).status;
    } else {
      status = this.parseForm.get( controlName ).status;
    }
    for (let field in this.parseForm.controls) {
      this.parseForm.get(field).disable();
    }
    if (status === 'DISABLED') {
      if ( isSplit === 'split' ){
        this.parseForm.get(controlName+'Start').enable();
        this.parseForm.get(controlName+'End').enable();
        this.parseForm.get(controlName+'Direction').enable();
        this.parseForm.get(controlName+'Entry').enable();
      } else {
        this.parseForm.get(controlName).enable();
      }
    }
  }

  convertFormToModel(){
    this.updateParseMenu.prefixUrl = this.parseForm.get('prefixUrl').value;
    this.updateParseMenu.parseUrl = this.parseForm.get('parseUrl').value;
    this.updateParseMenu.parseUrlTwo = this.parseForm.get('parseUrlTwo').value;
    this.updateParseMenu.parseUrlThree = this.parseForm.get('parseUrlThree').value;
    this.updateParseMenu.parseUrlFour = this.parseForm.get('parseUrlFour').value;
    this.updateParseMenu.tagTrash = this.parseForm.get('tagTrash').value;
    this.updateParseMenu.tagEndSection = this.parseForm.get('tagEndSection').value;
    
    this.updateParseMenu.tagName = this.concatTagValues('tagName');
    this.updateParseMenu.tagDescription = this.concatTagValues('tagDesc');
    this.updateParseMenu.tagImageUrl = this.concatTagValues('tagImgUrl');
    this.updateParseMenu.tagWeightOne = this.concatTagValues('tagWeightOne');
    this.updateParseMenu.tagSizeOne = this.concatTagValues('tagSizeOne');
    this.updateParseMenu.tagPriceOne = this.concatTagValues('tagPriceOne');
    this.updateParseMenu.tagWeightTwo = this.concatTagValues('tagWeightTwo');
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
    let val = null;
    let entry = this.parseForm.get(tag+'Entry').value == null ? '1' : this.parseForm.get(tag+'Entry').value;
    if ( this.parseForm.get( tag+'Start').value.trim().length > 0){
      val = this.parseForm.get( tag+'Start').value+"~"+
        this.parseForm.get( tag+'End').value+"~"+
        this.parseForm.get(tag+'Direction').value+"~"+entry;
    }
    return val;
  }

  testParseModel() {
    document.getElementById("top-parsing").scrollIntoView({behavior: "smooth", block: "start"});
    this._globalService.dataBusChanged('pageLoading', true);
    this.convertFormToModel();
    this.companyService.testMenuPage(this.updateParseMenu).subscribe(data => {
      this._globalService.dataBusChanged('pageLoading', false);
      this.parseMenu = data.result.parseMenu;
      if ( data.status === 200 ){
        this.testDialogHeader = 'Тестирование успешно';
        this.saveParseMenuDisabled = false;
        this.parseForm.get('htmlResponse').setValue( data.result.parseMenu.htmlResponse );
        this.testEntities = data.result.menuEntities;
      } else {
        this.fillForm();
        this.testDialogHeader = 'Ошибка при тестировании';
      }
      this.showDialog = true;

    });
  }

  saveParseModel(){
    document.getElementById("top-parsing").scrollIntoView({behavior: "smooth", block: "start"});
    this._globalService.dataBusChanged('pageLoading', true);
    this.convertFormToModel();
    this.companyService.saveParseModel(this.updateParseMenu).subscribe(data => {
      this.menuCategorySelect( this.selMenuType, this.selMenuCategory );
      if ( data.status === 200 ){
        swal('Обновление данных, успешно');
        this._globalService.dataBusChanged('activateTab',0);
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
      case 'parseUrlTwo': {
        value = this.parseMenu.parseUrlTwo;
        break;}
      case 'parseUrlThree': {
        value = this.parseMenu.parseUrlThree;
        break;}
      case 'parseUrlFour': {
        value = this.parseMenu.parseUrlFour;
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
      case 'tagDesc': {
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

  copyParseMenu(){
    document.getElementById("top-parsing").scrollIntoView({behavior: "smooth", block: "start"});
    let dishArray = this.selectedDish.split("/");
    this.copyParseData.fromMenuTypeId = dishArray[0];
    this.copyParseData.fromMenuCategoryId = dishArray[1];
    swal({
      title: 'Подтверждение...',
      text: "Скопировать данные из : '"+ dishArray[2]+"/"+dishArray[3]+"' ?",
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#16be9a',
      cancelButtonColor: '#ff7403',
      confirmButtonText: 'Да, скопировать',
      cancelButtonText: 'Отмена'
    }).then((result) => {
      if (result.value) {
        this._globalService.dataBusChanged('pageLoading', true);
        this.companyService.copyParseModel( this.copyParseData ).subscribe(data => {
          this._globalService.dataBusChanged('pageLoading', false);
          if ( data.status === 200 ){
            swal('Копирование успешно');
            this.parseMenu = data.result;
            this.fillForm();
          } else {
            swal({
              type: 'error',
              title: data.status,
              text: data.message,
            });
          }
        });
      }
    })
  }

  dishSelect( dish ){
    this.selectedDish = dish;
  }

  backToDetails(){
    let companyName = this.companyService.getCompanyById( this.companyId ).companyName;
    this.router.navigate(['pages/company-detail/'+companyName]);
  }

  isParseTabShow(){
    return this.showParseTab && this.selMenuCategory.id != '-1';
  }


}
