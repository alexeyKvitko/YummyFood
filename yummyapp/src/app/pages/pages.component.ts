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
    if (window.navigator && window.navigator.geolocation) {
      window.navigator.geolocation.getCurrentPosition(
        position => {
          this.companyService.initBootstrapApp( position.coords.latitude, position.coords.longitude );
        },
        error => {
          switch (error.code) {
            case 1:
              console.log('Permission Denied');
              break;
            case 2:
              console.log('Position Unavailable');
              break;
            case 3:
              console.log('Timeout');
              break;
          }
          this.companyService.initBootstrapApp( '-1', '-1' );
        }
      );
    } else {
      this.companyService.initBootstrapApp( '-1', '-1' );
    };
    this._globalService.data$.subscribe(data => {
      if (data.ev === 'pageLoading') {
        this.loading = data.value;
      }
    }, error => {
      console.log('Error: ' + error);
    });
  }

}
