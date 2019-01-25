import { Component } from '@angular/core';
import { GlobalService } from '../shared/services/global.service';
import {DeliveryMenuService} from "../services/delivery-menu.service";
import {CompanyService} from "../services/company.service";
import {LoginService} from "../services/login.service";

@Component({
  selector: 'app-pages',
  templateUrl: './pages.component.html',
  styleUrls: ['./pages.component.scss']
})

export class PagesComponent {

  loading: boolean = false;

  constructor(private _globalService: GlobalService,private deliveryMenuService : DeliveryMenuService,
              private companyService: CompanyService, private loginService: LoginService) {
    this.init();
  }

  public init(){
    this.loginService.getIP().subscribe(data => {
      if( data.city == null || data.city == undefined || data.city.trim().length == 0){
        data.city = 'Simferopol'
      }
      this.companyService.initBootstrapApp( data.city );
    });
    this._globalService.data$.subscribe(data => {
      if (data.ev === 'pageLoading') {
        this.loading = data.value;
      }
    }, error => {
      console.log('Error: ' + error);
    });
  }

}
