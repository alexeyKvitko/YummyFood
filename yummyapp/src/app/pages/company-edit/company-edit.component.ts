import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../services/auth.service";
import {CompanyService} from "../../services/company.service";
import {GlobalService} from "../../shared/services/global.service";
import {CompanyEditModel} from "../../model/company-edit.model";
import {MenuTypeModel} from "../../model/menu-type.model";
import {MenuCategoryModel} from "../../model/menu-category.model";
import swal from "sweetalert2";
import {CompanyModel} from "../../model/company.model";

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
  selMenuType: MenuTypeModel = new MenuTypeModel();
  selMenuCategory: MenuCategoryModel = new MenuCategoryModel();
  companyForm: FormGroup;
  selectedOptionType: string = null;
  selectedOptionCategory: string = null;

  constructor(private formBuilder: FormBuilder, private _authService: AuthService,
              private companyService: CompanyService, private _globalService: GlobalService) {
    this._authService.isAuthenticated();
    this.companyId = window.localStorage.getItem('companyId');
    this.selMenuType.id = '-1';
    this.selMenuCategory.id = '-1';
    if (this.companyId != '-1') {
      this._globalService.dataBusChanged('pageLoading', true);
    }
  }

  ngOnInit() {
    if (this.companyId != '-1') {
      this.companyService.getCompanyEdit(this.companyId).subscribe(data => {
        this.updateCompanyEdit(data);
        this.initForm();
        this.logoImgSrc = 'assets/images/logos/' + this.companyEdit.companyModel.logo;
        this._globalService.dataBusChanged('headerTitle', this.companyEdit.companyModel.displayName);
        this._globalService.dataBusChanged('companyUrl', this.companyEdit.companyModel.url);
      });
    } else {
      this.companyEdit = new CompanyEditModel();
      this.initForm();
    }
  }

  initForm() {
    this.companyForm = this.formBuilder.group({
      id: [{value: this.companyEdit.companyModel.id, disabled: true}],
      companyName: [{
        value: this.companyEdit.companyModel.companyName,
        disabled: true
      }, Validators.compose([Validators.required])],
      displayName: [{
        value: this.companyEdit.companyModel.displayName,
        disabled: true
      }, Validators.compose([Validators.required])],
      city: [{
        value: this.companyEdit.companyModel.city.name,
        disabled: true
      }, Validators.compose([Validators.required])],
      url: [{value: this.companyEdit.companyModel.url, disabled: true}],
      email: [{value: this.companyEdit.companyModel.email, disabled: true}],
      phoneOne: [{
        value: this.companyEdit.companyModel.phoneOne,
        disabled: true
      }, Validators.compose([Validators.required])],
      phoneTwo: [{value: this.companyEdit.companyModel.phoneTwo, disabled: true}],
      phoneThree: [{value: this.companyEdit.companyModel.phoneThree, disabled: true}],
      logo: [{value: this.companyEdit.companyModel.logo, disabled: true}, Validators.compose([Validators.required])]
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
    if( menuType.menuCategories == null ){
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
  }

  isSaveBtnHidden():boolean {
    let hidden = true;
    for (let field in this.companyForm.controls) {
      hidden = hidden && this.companyForm.get(field).value === this.getByName(field );
    }
    return hidden;
  }

  citySelect( val ){
    this.companyEdit.cities.forEach(city => {
      if( city.id.toString() === val ){
        this.companyForm.get('city').setValue( city.displayName );
        this.companyForm.get('city').disable();
      }
    });
  }

  showHttpActionMessage( data ){
    if ( data.status === 200 ){
      swal('Обновление данных, успешно');
    } else {
      swal({
        type: 'error',
        title: data.status,
        text: data.message,
      });
    }
  }
  
  saveCompanyModel(){
    let companyModel =  new CompanyModel();
    companyModel.id = this.companyForm.get('id').value;
    companyModel.companyName = this.companyForm.get('companyName').value;
    companyModel.displayName = this.companyForm.get('displayName').value;
    let cityVal = this.companyForm.get('city').value;
    this.companyEdit.cities.forEach(city => {
      if( city.displayName === cityVal ){
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
    companyModel.logo= this.companyForm.get('logo').value;
    this.companyService.saveCompanyModel( companyModel ).subscribe(data => {
      if ( data.status == 200 ){
        this.updateCompanyEdit( data.result );
      }
      this.showHttpActionMessage(data);
    });
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
           if ( deleteResult.status == 200 ){
             this.companyService.getCompanyEdit(this.companyId).subscribe(data => {
              this.updateCompanyEdit(data);
               this.selectedOptionType = null;
               this.selectedOptionCategory = null;
             });
           }
          this.showHttpActionMessage( deleteResult );
        });
      }
    })
  }

  updateCompanyEdit( data ){
    this.companyEdit = data;
    this.companyEdit.companyModel.city.displayName = this.companyEdit.companyModel.city.name;
    this.companyEdit.menuTypes.forEach(item => {
      item.menuOpen = true;
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
          this.companyEdit.menuTypes.push( item );
        }
      });
      this.selectedOptionCategory = null;
    }
  }
  addMenuCategory(){
    if ( this.selectedOptionType == null ){
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
    if ( menuType == null ){
      swal({
        type: 'error',
        title: 'Невозможно',
        text: 'Кухня не найдена!',
      });
      return;
    }
    let exist = false;
    if ( menuType.menuCategories == null ){
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
    if( !exist ){
      this.companyEdit.deliveryMenuCategories.forEach(item => {
        if (item.id.toString() === this.selectedOptionCategory) {
          this.companyService.addCompanyMenu(this.companyId,
                    this.selectedOptionType,this.selectedOptionCategory).subscribe(data => {
            if ( data.status == 200 ){
              menuType.menuOpen = true;
              menuType.menuCategories.push( item );
            }
            this.showHttpActionMessage( data );
          });
        }
      });
    }

  }

}
