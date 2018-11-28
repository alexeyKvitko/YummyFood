import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../services/auth.service";
import {CompanyService} from "../../services/company.service";
import {GlobalService} from "../../shared/services/global.service";
import {CompanyEditModel} from "../../model/company-edit.model";
import {NgxNotificationService} from "ngx-notification";
import {MenuTypeModel} from "../../model/menu-type.model";
import {MenuCategoryModel} from "../../model/menu-category.model";

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

  constructor(private formBuilder: FormBuilder, private _authService: AuthService, private _ngxNotificationService: NgxNotificationService,
              private companyService: CompanyService, private _globalService: GlobalService) {
    this._authService.isAuthenticated();
    this.companyId = window.localStorage.getItem('companyId');
    this.selMenuType.id = '-1';
    this.selMenuCategory.id = '-1';
    if (this.companyId != null) {
      this._globalService.dataBusChanged('pageLoading', true);
    }
  }

  ngOnInit() {
    if (this.companyId != null) {
      this.companyService.getCompanyEdit(this.companyId).subscribe(data => {
        this.companyEdit = data;
        this.companyEdit.menuTypes.forEach(item => {
          item.menuOpen = true;
        });
        this.initForm();
        this.logoImgSrc = 'assets/images/logos/' + this.companyEdit.companyModel.logo;
        this._globalService.dataBusChanged('headerTitle', this.companyEdit.companyModel.displayName);
        this._globalService.dataBusChanged('companyUrl', this.companyEdit.companyModel.url);
        this._globalService.dataBusChanged('pageLoading', false);
        this.loading = false;
      });
    } else {
      this.companyEdit = new CompanyEditModel();
    }
  }

  initForm() {
    this.companyForm = this.formBuilder.group({
      id: [{value: this.companyEdit.companyModel.id, disabled: true}],
      companyName: [{value: this.companyEdit.companyModel.companyName, disabled: true}, Validators.compose([Validators.required])],
      displayName: [{value: this.companyEdit.companyModel.displayName, disabled: true}, Validators.compose([Validators.required])],
      city: [{value: this.companyEdit.companyModel.cityId, disabled: true}, Validators.compose([Validators.required])],
      url: [{value: this.companyEdit.companyModel.url, disabled: true}],
      email: [{value: this.companyEdit.companyModel.email, disabled: true}],
      phoneOne: [{value: this.companyEdit.companyModel.phoneOne, disabled: true}, Validators.compose([Validators.required])],
      phoneTwo: [{value: this.companyEdit.companyModel.phoneTwo, disabled: true}],
      phoneThree: [{value: this.companyEdit.companyModel.phoneThree, disabled: true}],
      logo: [{value: this.companyEdit.companyModel.logo, disabled: true}, Validators.compose([Validators.required])]
    });
  }

  isControlHidden(controlName) {
    return this.companyForm.get(controlName).status === 'DISABLED';
  }

  undoControl( controlName ){
    //return this.companyForm.get(controlName).setValue( this.getByName( controlName) );
  }

  copyControl(controlName){
    // let txt = this.parseForm.get(controlName).value;
    // this._clipboardService.copyFromContent( txt );
    // this._ngxNotificationService.sendMessage('Скопировано( '+txt+' )', 'success', 'top-right');
  }

  inputControlClick(controlName,isSplit) {
    for (let field in this.companyForm.controls) {
      this.companyForm.get(field).disable();
    }
    this.companyForm.get(controlName).enable();

  }

}
