import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../services/auth.service";
import {CompanyService} from "../../services/company.service";
import {GlobalService} from "../../shared/services/global.service";
import {CompanyEditModel} from "../../model/company-edit.model";
import {MenuTypeModel} from "../../model/menu-type.model";
import {MenuCategoryModel} from "../../model/menu-category.model";
import {CompanyModel} from "../../model/company.model";
import {WORK_DAY_START} from "./const-workdaystart";
import {WORK_DAY_END} from "./const-workdayend";
import swal from "sweetalert2";
import {Router} from "@angular/router";

@Component({
  selector: 'app-company-edit',
  templateUrl: './company-edit.component.html',
  styleUrls: ['./company-edit.component.scss']
})
export class CompanyEditComponent implements OnInit {

  loading: boolean = true;
  logoImgSrc: string = '';
  companyId: string;
  companyEdit: CompanyEditModel = null;
  companyModel: CompanyModel = new CompanyModel();
  selMenuType: MenuTypeModel = new MenuTypeModel();
  selMenuCategory: MenuCategoryModel = new MenuCategoryModel();
  companyForm: FormGroup;
  deliveryCity: string = "";
  selectedOptionType: string = null;
  selectedOptionCategory: string = null;
  workDayStartValues = WORK_DAY_START;
  workDayEndValues = WORK_DAY_END;



  constructor(private router: Router,private formBuilder: FormBuilder, private _authService: AuthService,
              private companyService: CompanyService, private _globalService: GlobalService) {
    this._authService.isAuthenticated();
    this.companyId = window.localStorage.getItem('companyId');
    this.selMenuType.id = '-1';
    this.selMenuCategory.id = '-1';
    this._globalService.dataBusChanged('pageLoading', true);
  }

  ngOnInit() {
    this.companyService.getCompanyEdit(this.companyId).subscribe(data => {
      this.deliveryCity = this.companyService.getDeliveryCity();
      this.updateCompanyEdit(data);
      this.initForm();
      this.logoImgSrc = 'assets/images/logos/' + this.companyEdit.companyModel.logo;
      this._globalService.dataBusChanged('headerTitle', this.companyEdit.companyModel.displayName);
      this._globalService.dataBusChanged('companyUrl', this.companyEdit.companyModel.url);
    });
  }

  initForm() {
    this.companyForm = this.formBuilder.group({
      id: [{value: this.companyModel.id, disabled: true}],
      companyName: [{value: this.companyModel.companyName,disabled: true}, Validators.compose([Validators.required])],
      displayName: [{value: this.companyModel.displayName,disabled: true}, Validators.compose([Validators.required])],
      city: [{value: this.companyModel.city.name,disabled: true}, Validators.compose([Validators.required])],
      url: [{value: this.companyModel.url, disabled: true}],
      email: [{value: this.companyModel.email, disabled: true}],
      phoneOne: [{value: this.companyModel.phoneOne,disabled: true}, Validators.compose([Validators.required])],
      phoneTwo: [{value: this.companyModel.phoneTwo, disabled: true}],
      phoneThree: [{value: this.companyModel.phoneThree, disabled: true}],
      logo: [{value: this.companyModel.logo, disabled: true}, Validators.compose([Validators.required])],
      minOrder: [{value: this.companyModel.delivery, disabled: true}, Validators.compose([Validators.required])],
      reviewNum: [{value: this.companyModel.commentCount, disabled: true}, Validators.compose([Validators.required])],
      workDayStart: [{value: this.getWorkDisplayByValue(this.companyModel.weekdayStart, this.workDayStartValues) ,
                        disabled: true}, Validators.compose([Validators.required])],
      workDayEnd: [{value: this.getWorkDisplayByValue(this.companyModel.weekdayEnd, this.workDayEndValues)
                            ,disabled: true}, Validators.compose([Validators.required])],
      workDayByStr: [{value: this.companyModel.weekdayWork,disabled: true}, Validators.compose([Validators.required])],
      dayOffStart: [{value: this.getWorkDisplayByValue(this.companyModel.dayoffStart, this.workDayStartValues)
                        ,disabled: true}, Validators.compose([Validators.required])],
      dayOffEnd: [{value: this.getWorkDisplayByValue(this.companyModel.dayoffEnd, this.workDayEndValues)
                      ,disabled: true}, Validators.compose([Validators.required])],
      dayOffByStr: [{value: this.companyModel.dayoffWork,disabled: true}, Validators.compose([Validators.required])],
      payCash: [{value: this.companyModel.payTypeCash,disabled: true}, Validators.compose([Validators.required])],
      payCard: [{value: this.companyModel.payTypeCard,disabled: true}, Validators.compose([Validators.required])],
      payWallet: [{value: this.companyModel.payTypeWallet,disabled: true}, Validators.compose([Validators.required])]
    });
    this._globalService.dataBusChanged('pageLoading', false);
    this.loading = false;
  }

  isControlHidden(controlName) {
    return this.companyForm.get(controlName).status === 'DISABLED';
  }

  undoControl(controlName) {
    return this.companyForm.get(controlName).setValue(this.getByName(controlName));
  }

  menuTypeSelect(menuType) {
    if (menuType.menuCategories == null) {
      return;
    }
    menuType.menuOpen = !menuType.menuOpen;
    this.selMenuType = menuType;
    this.selMenuCategory = new MenuCategoryModel();
    this.selMenuCategory.id = '-1';
    this._globalService.dataBusChanged('showIcon', true);
  }

  menuCategorySelect(menuType, menuCategory) {
    this._globalService.dataBusChanged('menuType', menuType.displayName);
    this._globalService.dataBusChanged('menuCategory', menuCategory.displayName);
    this._globalService.dataBusChanged('showIcon', false);
    this.selMenuType = menuType;
    this.selMenuCategory = menuCategory;
  }

  inputControlClick(controlName) {
    var status = this.companyForm.get(controlName).status;
    for (let field in this.companyForm.controls) {
      this.companyForm.get(field).disable();
    }
    if (status === 'DISABLED') {
      this.companyForm.get(controlName).enable();
    }
    this.logoImgSrc = 'assets/images/logos/' + this.companyForm.get('logo').value;
  }

  isSaveBtnHidden(): boolean {
    let hidden = true;
    for (let field in this.companyForm.controls) {
      hidden = hidden && this.companyForm.get(field).value === this.getByName(field);
    }
    return hidden;
  }

  citySelect(val) {
    this.companyEdit.cities.forEach(city => {
      if (city.id.toString() === val) {
        this.companyForm.get('city').setValue(city.displayName);
        this.companyForm.get('city').disable();
      }
    });
  }

  workDayStartSelect( workStart ){
    this.companyForm.get('workDayStart').setValue( workStart );
    this.companyForm.get('workDayStart').disable();
  }

  workDayEndSelect( workEnd ){
    this.companyForm.get('workDayEnd').setValue( workEnd );
    this.companyForm.get('workDayEnd').disable();
  }

  dayOffStartSelect( workStart ){
    this.companyForm.get('dayOffStart').setValue( workStart );
    this.companyForm.get('dayOffStart').disable();
  }

  dayOffEndSelect( workEnd ){
    this.companyForm.get('dayOffEnd').setValue( workEnd );
    this.companyForm.get('dayOffEnd').disable();
  }

  switchValue( el, val ){
    this.companyForm.get(el).setValue( val );
  }

  showHttpActionMessage(data) {
    if (data.status === 200) {
      swal('Обновление данных, успешно');
    } else {
      swal({
        type: 'error',
        title: data.status,
        text: data.message,
      });
    }
  }

  saveCompanyModel() {
    let companyModel = new CompanyModel();
    companyModel.id = this.companyId;
    companyModel.companyName = this.companyForm.get('companyName').value;
    companyModel.displayName = this.companyForm.get('displayName').value;
    let cityVal = this.companyForm.get('city').value;
    this.companyEdit.cities.forEach(city => {
      if (city.displayName === cityVal) {
        companyModel.city = {
          id: city.id
        };
      }
    });
    companyModel.url = this.companyForm.get('url').value;
    companyModel.email = this.companyForm.get('email').value;
    companyModel.phoneOne = this.companyForm.get('phoneOne').value;
    companyModel.phoneTwo = this.companyForm.get('phoneTwo').value;
    companyModel.phoneThree = this.companyForm.get('phoneThree').value;
    companyModel.logo = this.companyForm.get('logo').value;

    companyModel.delivery = this.companyForm.get('minOrder').value;
    companyModel.commentCount = this.companyForm.get('reviewNum').value;
    companyModel.payTypeCash = this.companyForm.get('payCash').value;
    companyModel.payTypeCard = this.companyForm.get('payCard').value;
    companyModel.payTypeWallet = this.companyForm.get('payWallet').value;
    companyModel.weekdayStart = this.getWorkStartValueByDisplayVal( this.companyForm.get('workDayStart').value );
    companyModel.weekdayEnd = this.getWorkEndValueByDisplayVal( this.companyForm.get('workDayEnd').value );
    companyModel.weekdayWork = this.companyForm.get('workDayByStr').value;
    companyModel.dayoffStart = this.getWorkStartValueByDisplayVal(this.companyForm.get('dayOffStart').value );
    companyModel.dayoffEnd = this.getWorkEndValueByDisplayVal( this.companyForm.get('dayOffEnd').value );
    companyModel.dayoffWork = this.companyForm.get('dayOffByStr').value;
    if ( this.validateCompanyModel( companyModel ) != null ){
      return;
    }
    this.companyService.saveCompanyModelAndInfo( companyModel ).subscribe(data => {
      if (data.status == 200) {
        this.updateCompanyEdit(data.result);
      }
      this.showHttpActionMessage(data);
    });
  }

  validateCompanyModel( companyModel : CompanyModel ) : string{
    let result = "";
    if ( companyModel.companyName == null ||  companyModel.companyName.trim().length == 0 ){
      result = result + "Ключ компании; ";
    }
    if ( companyModel.displayName == null ||  companyModel.displayName.trim().length == 0 ){
      result = result + "Наименование компании; ";
    }
    if ( companyModel.url == null ||  companyModel.url.trim().length == 0 ){
      result = result + "Сайт компании; ";
    }
    if ( companyModel.phoneOne == null ||  companyModel.phoneOne.trim().length == 0 ){
      result = result + "Телефон; ";
    }
    if ( companyModel.delivery == null ||  companyModel.delivery.trim().length == 0 ){
      result = result + "Минимальная доставка; ";
    }
    if ( companyModel.commentCount == null ||  companyModel.commentCount.trim().length == 0 ){
      result = result + "Кол-во отзывов; ";
    }

    if ( companyModel.weekdayStart == null){
      result = result + "Начало работы будни; ";
    }
    if ( companyModel.weekdayEnd == null){
      result = result + "Окончание работы будни; ";
    }
    if ( companyModel.weekdayWork == null ||  companyModel.weekdayWork.trim().length == 0 ){
      result = result + "Работа будни строкой; ";
    }
    if ( companyModel.dayoffStart == null){
      result = result + "Начало работы выxодные; ";
    }
    if ( companyModel.dayoffEnd == null){
      result = result + "Окончание работы выxодные; ";
    }
    if ( companyModel.dayoffWork == null ||  companyModel.dayoffWork.trim().length == 0 ){
      result = result + "Работа выходные строкой; ";
    }
    if ( result.length == 0 ) {
      result = null;
    }
    if ( result != null ){
      swal({
        type: 'error',
        title: 'Не заполнены обязательные поля !',
        text: result
      });
    }
    let reg = /^-?\d+\.?\d*$/;
    if ( result == null && ( !reg.test(companyModel.commentCount)
    || !reg.test(companyModel.phoneOne)) ){
      result = "Кол-во отзывов или телефон";
      swal({
        type: 'error',
        title: 'Должно быть числом!',
        text: "Кол-во отзывов: "+companyModel.commentCount+", Телефон: "+companyModel.phoneOne
      });
    }
    if ( result == null && companyModel.phoneOne.trim().length != 10 ){
      result = "Телефон";
      swal({
        type: 'error',
        title: 'Длина телефонного номера должна быть равна 10',
        text: "Получили: "+companyModel.phoneOne.trim().length
      });
    }

    return result;
  }

  deleteMenu(menuType, menuCategory) {
    swal({
      title: 'Подтверждение...',
      text: "Вы желаете удалить меню: '" + menuCategory.displayName + "'",
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#16be9a',
      cancelButtonColor: '#ff7403',
      confirmButtonText: 'Да, удалить',
      cancelButtonText: 'Отмена'
    }).then((result) => {
      if (result.value) {
        this.companyService.deleteCompanyMenu(this.companyId, menuType.id, menuCategory.id).subscribe(data => {
          let deleteResult = data;
          if (deleteResult.status == 200) {
            this.companyService.getCompanyEdit(this.companyId).subscribe(data => {
              this.updateCompanyEdit(data);
              this.selectedOptionType = null;
              this.selectedOptionCategory = null;
            });
          }
          this.showHttpActionMessage(deleteResult);
        });
      }
    })
  }

  deleteMenuEntities(menuType, menuCategory) {
    swal({
      title: 'Подтверждение...',
      text: "Вы желаете удалить все блюда из меню: '" + menuCategory.displayName + "'",
      type: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#16be9a',
      cancelButtonColor: '#ff7403',
      confirmButtonText: 'Да, удалить',
      cancelButtonText: 'Отмена'
    }).then((result) => {
      if (result.value) {
        this.companyService.deleteCompanyMenuEntities(this.companyId, menuType.id, menuCategory.id).subscribe(data => {
          let deleteResult = data;
          if (deleteResult.status == 200) {
            this.companyService.getCompanyEdit(this.companyId).subscribe(data => {
              this.updateCompanyEdit(data);
              this.selectedOptionType = null;
              this.selectedOptionCategory = null;
            });
          }
          this.showHttpActionMessage(deleteResult);
        });
      }
    })
  }

  updateCompanyEdit(data) {
    this.companyEdit = data;
    this.companyModel = data.companyModel;
    this.companyId = this.companyModel.id;
    if ( this.companyId != '-1' ){
      this.companyEdit.companyModel.city.displayName = this.companyEdit.companyModel.city.name;
    } else {
      this.companyEdit.companyModel.city.displayName =  this.deliveryCity;
    }
    let typeOrder = 1;
    let categoryOrder = 1;
    this.companyEdit.menuTypes.forEach(type => {
      categoryOrder = 1;
      type.order = typeOrder*1000;
      type.menuOpen = true;
      type.menuCategories.forEach( category => {
        category.order = categoryOrder;
        categoryOrder++;
      });
      typeOrder++;
    });
  }

  getByName(name: string): string {
    let value = null;
    switch (name) {
      case 'id': {
        value = this.companyEdit.companyModel.id;
        break;
      }
      case 'companyName': {
        value = this.companyEdit.companyModel.companyName;
        break;
      }
      case 'displayName': {
        value = this.companyEdit.companyModel.displayName;
        break;
      }
      case 'city': {
        value = this.companyEdit.companyModel.city.displayName;
        break;
      }
      case 'url': {
        value = this.companyEdit.companyModel.url;
        break;
      }
      case 'email': {
        value = this.companyEdit.companyModel.email;
        break;
      }
      case 'phoneOne': {
        value = this.companyEdit.companyModel.phoneOne;
        break;
      }
      case 'phoneTwo': {
        value = this.companyEdit.companyModel.phoneTwo;
        break;
      }
      case 'phoneThree': {
        value = this.companyEdit.companyModel.phoneThree;
        break;
      }
      case 'logo': {
        value = this.companyEdit.companyModel.logo;
        break;
      }
      default: {
        break;
      }
    }
    return value;
  }

  addMenuType() {
    let exist = false;
    this.companyEdit.menuTypes.forEach(item => {
      if (item.id.toString() === this.selectedOptionType) {
        exist = true;
        swal({
          type: 'error',
          title: 'Невозможно',
          text: item.displayName + ' существует в спике меню!',
        });
      }
    });
    if (!exist) {
      this.companyEdit.deliveryMenuTypes.forEach(item => {
        if (item.id.toString() === this.selectedOptionType) {
          item.menuOpen = false;
          this.companyEdit.menuTypes.push(item);
        }
      });
      this.selectedOptionCategory = null;
    }
  }

  addMenuCategory() {
    if (this.companyId == '-1') {
      swal({
        type: 'error',
        title: 'Невозможно',
        text: 'Необходимо записать оргнизацию!',
      });
      return;
    }
    if (this.selectedOptionType == null) {
      swal({
        type: 'error',
        title: 'Невозможно',
        text: 'Неоходимо выбрать кухню!',
      });
      return;
    }
    let menuType = null;
    this.companyEdit.menuTypes.forEach(item => {
      if (item.id.toString() === this.selectedOptionType) {
        menuType = item;
      }
    });
    if (menuType == null) {
      swal({
        type: 'error',
        title: 'Невозможно',
        text: 'Кухня не найдена!',
      });
      return;
    }
    let exist = false;
    if (menuType.menuCategories == null) {
      menuType.menuCategories = new Array<MenuCategoryModel>();
    }
    menuType.menuCategories.forEach(item => {
      if (item.id.toString() === this.selectedOptionCategory) {
        exist = true;
        swal({
          type: 'error',
          title: 'Невозможно',
          text: item.displayName + ' существует в спике меню!',
        });
      }
    });
    if (!exist) {
      this.companyEdit.deliveryMenuCategories.forEach(item => {
        if (item.id.toString() === this.selectedOptionCategory) {
          this.companyService.addCompanyMenu(this.companyId,
            this.selectedOptionType, this.selectedOptionCategory).subscribe(data => {
            if (data.status == 200) {
              menuType.menuOpen = true;
              menuType.menuCategories.push(item);
            }
            this.showHttpActionMessage(data);
          });
        }
      });
    }
  }

  getWorkStartValueByDisplayVal( displayVal ){
    let val = null;
    this.workDayStartValues.forEach( workStart => {
      if( workStart.display == displayVal ){
        val = workStart.value;
      }
    });
    return val;
  }

  getWorkEndValueByDisplayVal( displayVal ){
    let val = null;
    this.workDayEndValues.forEach( workEnd => {
      if( workEnd.display == displayVal ){
        val = workEnd.value;
      }
    });
    return val;
  }

  getWorkDisplayByValue( value, valueArray ){
    let display = null;
    valueArray.forEach( work => {
      if( work.value == value ){
        display = work.display;
      }
    });
    return display;
  }

  backToDetails(){
    if ( this.companyId == '-1'){
      this.router.navigate(['pages/company']);
    } else {
      this.router.navigate(['pages/company-detail']);
    }

  }

  editMenu(){
    this.router.navigate(['pages/delivery-menu']);
  }

  changeOrderType( index, menuType, direction ){
    console.log("change order:",menuType.order, index, direction);
    if( direction == -1 && menuType.order == 1000 ){
      swal("Первый элемент");
      return;
    }
    if(  direction == 1 && menuType.order == 1000*(this.companyEdit.menuTypes.length) ){
      swal("Последний элемент");
    }
    if ( direction == -1 ){
      let previous = this.companyEdit.menuTypes[index-1];
      this.companyEdit.menuTypes[index-1] = menuType;
      this.companyEdit.menuTypes[index] = previous;
    }
    if( direction == 1 ){
      let next = this.companyEdit.menuTypes[index+1];
      this.companyEdit.menuTypes[index+1] = menuType;
      this.companyEdit.menuTypes[index] = next;
    }
    let typeOrder = 1;
    this.companyEdit.menuTypes.forEach(type => {
      type.order = typeOrder*1000;
      typeOrder++;
    });
  }

  changeOrderCategory( index, menuType, menuCategory, direction ){
    if( direction == -1 && menuCategory.order == 1 ){
      swal("Первый элемент");
      return;
    }
    if(  direction == 1 && menuCategory.order == this.companyEdit.menuTypes.length ){
      swal("Последний элемент");
    }
    if ( direction == -1 ){
      let previous = menuType.menuCategories[index-1];
      menuType.menuCategories[index-1] = menuCategory;
      menuType.menuCategories[index] = previous;
    }
    if( direction == 1 ){
      let next = menuType.menuCategories[index+1];
      menuType.menuCategories[index+1] = menuCategory;
      menuType.menuCategories[index] = next;
    }
    let categoryOrder = 1;
    menuType.menuCategories.forEach(category => {
      category.order = categoryOrder;
      categoryOrder++;
    });
  }



}
