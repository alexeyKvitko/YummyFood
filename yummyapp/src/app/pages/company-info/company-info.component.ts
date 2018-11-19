import { Component, OnInit } from '@angular/core';

import { CompanyInfoModel } from '../../model/company-info.model';
import { CompanyService } from '../../services/company.service';
import { GlobalService } from '../../shared/services/global.service';

@Component({
  selector: 'app-company-info',
  templateUrl: './company-info.component.html',
  styleUrls: ['./company-info.component.scss']
})
export class CompanyInfoComponent implements OnInit {

  companyInfo: CompanyInfoModel;
  loading: boolean = false;

  constructor(private companyService: CompanyService, private _globalService: GlobalService) {
  }

  public getCompanyInfo( id ){
    this.companyService.getCompanyInfo(id)
      .subscribe( data => {
        this.companyInfo = data;
        console.log('COMPANY INFO',this.companyInfo);
        this._globalService.dataBusChanged('pageLoading', false);
        this.loading = false;
      });
  }

  ngOnInit() {
    this._globalService.data$.subscribe(data => {
      if (data.ev === 'companyId') {
        console.log('comapny id: ',data.value);
        this.getCompanyInfo( data.value );
      }
    }, error => {
      console.log('Error: ' + error);
    });
  }

}
