import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';

import { CompanyModel } from '../../model/company.model';
import { CompanyService } from '../../services/company.service';
import { GlobalService } from '../../shared/services/global.service';
import { AuthService } from "../../services/auth.service";

@Component({
  selector: 'app-company',
  templateUrl: './company.component.html',
  styleUrls: ['./company.component.scss']
})
export class CompanyComponent implements OnInit {

  companies: CompanyModel[];
  loading: boolean = false;




  constructor(private router: Router,private _authService: AuthService,
              private companyService: CompanyService, private _globalService: GlobalService) {
    this._authService.isAuthenticated();
    // this.loading = true;
    // this._globalService.dataBusChanged('pageLoading', true);
  }

  ngOnInit() {
    this._globalService.dataBusChanged('headerTitle', 'Кафе/Рестораны');
    this._globalService.dataBusChanged('companyUrl', null);
    this._globalService.dataBusChanged('menuType', null);
    this._globalService.dataBusChanged('menuCategory', null);
    this._globalService.dataBusChanged('showIcon', true);
    // this.companyService.getCompanies()
    //   .subscribe( data => {
    //     this.companies = data;
    //     this._globalService.dataBusChanged('pageLoading', false);
    //     this.loading = false;
    //   });
  };


  showCompanyDetails( companyId ){
    window.localStorage.setItem('companyId',companyId);
    this.router.navigate(['pages/company-info']);

  }

  editCompany( companyId ){
    window.localStorage.setItem('companyId',companyId);
    this.router.navigate(['pages/company-edit']);

  }

  createCompany(){
    window.localStorage.setItem('companyId','-1');
    this.router.navigate(['pages/company-edit']);
  }

}
