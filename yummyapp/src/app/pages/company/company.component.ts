import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';

import { CompanyModel } from '../../model/company.model';
import { CompanyService } from '../../services/company.service';
import { GlobalService } from '../../shared/services/global.service';

@Component({
  selector: 'app-company',
  templateUrl: './company.component.html',
  styleUrls: ['./company.component.scss']
})
export class CompanyComponent implements OnInit {

  companies: CompanyModel[];
  loading: boolean = false;


  /* pagination Info */
  pageSize = 10;
  pageNumber = 1;


  constructor(private router: Router,
              private companyService: CompanyService, private _globalService: GlobalService) {
    this.loading = true;
    this._globalService.dataBusChanged('pageLoading', true);
  }

  ngOnInit() {
    this._globalService.dataBusChanged('headerTitle', 'Кафе/Рестораны');
    this.companyService.getCompanies()
      .subscribe( data => {
        this.companies = data;
        this._globalService.dataBusChanged('pageLoading', false);
        this.loading = false;
      });
  };

  pageChanged(pN: number): void {
    this.pageNumber = pN;
  }

  showCompanyDetails( companyId ){
    window.localStorage.setItem('companyId',companyId);
    this.router.navigate(['pages/company-info']);

  }
}
