import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {CompanyService} from "../../services/company.service";
import {GlobalService} from "../../shared/services/global.service";
import {LoginService} from "../../services/login.service";

@Component({
  selector: 'select-company',
  template: ''
})
export class SelectCompanyComponent implements OnInit {

  selectedCompanyId: string;

  constructor(private _globalService: GlobalService, private router: Router,private loginService: LoginService,
              private activatedRoute: ActivatedRoute, private companyService: CompanyService) {
  }

  ngOnInit(): void {
    let latitude = null;
    let longitude = null;
    let company = this.activatedRoute.snapshot.paramMap.get("company");
    switch (company) {
      // СИМФЕРОПОЛЬ
      case 'fidele-food':
        latitude = 44.9521;
        longitude = 34.1024;
        this.selectedCompanyId = '1';
        break;
      case 'kuhnya-crimea':
        latitude = 44.9521;
        longitude = 34.1024;
        this.selectedCompanyId = '2';
        break;
      case 'pizzarolla-simf':
        latitude = 44.9521;
        longitude = 34.1024;
        this.selectedCompanyId = '3';
        break;
      case 'foodie-eda':
        latitude = 44.9521;
        longitude = 34.1024;
        this.selectedCompanyId = '5';
        break;
      case 'pavlin-mavlin':
        latitude = 44.9521;
        longitude = 34.1024;
        this.selectedCompanyId = '14';
        break;
      case 'nysp':
        latitude = 44.9521;
        longitude = 34.1024;
        this.selectedCompanyId = '15';
        break;
      case 'chilipizza':
        latitude = 44.9521;
        longitude = 34.1024;
        this.selectedCompanyId = '16';
        break;
        // СЕВАСТОПОЛЬ
      case 'pizza-maximus':
        latitude = 44.6166;
        longitude = 33.5254;
        this.selectedCompanyId = '4';
        break;
      case 'sevas-sushi24':
        latitude = 44.6166;
        longitude = 33.5254;
        this.selectedCompanyId = '6';
        break;
      case 'enter-admin':
        this.onSubmit();
        this.selectedCompanyId == null;
        break;
      default:
        this.router.navigate(['pages/home-page']);
        this.selectedCompanyId == null;
        break;
    }
    if (this.selectedCompanyId != null) {
      this._globalService.dataBusChanged('pageLoading', true);
      this.companyService.initBootstrapApp(latitude, longitude);
      this._globalService.data$.subscribe(data => {
        if (data.ev === 'data-loaded') {
          if (data.value) {
            window.localStorage.setItem('companyId', this.selectedCompanyId);
            window.localStorage.setItem("delivery-city", this.companyService.getDeliveryCity() );
            let companyName = this.companyService.getCompanyById( this.selectedCompanyId ).companyName;
            this.router.navigate(['pages/company-detail/'+companyName]);
          }
        }
      });
    }

  }

  onSubmit() {
    const loginPayload = {
      username: "admin",
      password: "a_pswd"
    };

    this.loginService.login(loginPayload).subscribe(data => {
      if(data.status === 200) {
        window.localStorage.setItem('token', data.result.token);
        window.localStorage.setItem('userrole', data.result.userRole);
        console.log("You are logged in as administrator ...", data.result.userRole );
        this.router.navigate(['pages/home-page']);
      }else {
        alert(data.message);
      }
    });
  }

}
