import { Component } from '@angular/core';
import { GlobalService } from '../shared/services/global.service';
import {pageRouteAnimation} from "./page-animation";
import {DeliveryMenuService} from "../services/delivery-menu.service";
import {CompanyService} from "../services/company.service";
import {LoginComponent} from "./login/login.component";
import {LoginService} from "../services/login.service";

@Component({
  selector: 'app-pages',
  templateUrl: './pages.component.html',
  styleUrls: ['./pages.component.scss'], animations: [
    pageRouteAnimation
  ]
})

export class PagesComponent {

  loading: boolean = false;

  constructor(private _globalService: GlobalService,private deliveryMenuService : DeliveryMenuService,
              private companyService: CompanyService, private loginService: LoginService) {
    this.init();
  }

  public init(){
    this.loginService.getIP().subscribe(data => {
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
