import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';

import { CompanyInfoModel } from '../../model/company-info.model';
import { CompanyService } from '../../services/company.service';
import { GlobalService } from '../../shared/services/global.service';

@Component({
  selector: 'app-company-info',
  templateUrl: './company-info.component.html',
  styleUrls: ['./company-info.component.scss']
})
export class CompanyInfoComponent implements OnInit {
  companyId : string;
  btnBackImgSrc:string = 'assets/images/buttons/back.png';
  logoImgSrc: string = 'assets/images/logos/fidel.png';
  companyInfo: CompanyInfoModel;
  loading: boolean = false;

  constructor(private router: Router,
              private companyService: CompanyService, private _globalService: GlobalService) {
   this.companyId = window.localStorage.getItem('companyId');
  }


  ngOnInit() {
      this.companyService.getCompanyInfo( this.companyId )
      .subscribe( data => {
        this.companyInfo = data;
        this.logoImgSrc = 'assets/images/logos/'+this.companyInfo.companyModel.logo;
        this._globalService.dataBusChanged('pageLoading', false);
        this.loading = false;
      });
  }

  public back(){
    this.router.navigate(['pages/company']);
  }

}
