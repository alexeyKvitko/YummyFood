import {Component, Input, OnInit} from '@angular/core';
import { GlobalService } from '../../services/global.service';
import {Router} from "@angular/router";
import { menuService } from '../../services/menu.service';
import {TOP_MENU} from "./top-menu";
import {DictionaryModel} from "../../../model/dictionary.model";
import {CompanyService} from "../../../services/company.service";

@Component({
  selector: 'pages-top',
  templateUrl: './pages-top.component.html',
  styleUrls: ['./pages-top.component.scss'],
  providers: [menuService]
})
export class PagesTopComponent implements OnInit{
  avatarImgSrc: string = 'assets/images/logo.png';
  logoOpacity: number = 0;
  headerTitle: string;
  showIcon: boolean = true;
  menuType: string;
  menuCategory: string;
  companyUrl: string;
  topMenus: Array<any> = TOP_MENU;
  selectedLink: string;
  cities: DictionaryModel[] = new Array<DictionaryModel>();
  deliveryCity: string;
  showDialog: boolean = false;
  basketPrice: number = 0;

  constructor( private _globalService: GlobalService,
                private router: Router, private companyService : CompanyService ) {
    this._globalService.data$.subscribe(data => {
      if (data.ev === 'data-loaded') {
        if ( data.value ){
          this.cities = this.companyService.getAllCities();
          this.deliveryCity = this.companyService.getDeliveryCity();
        }
      }
      if (data.ev === 'logo-opacity') {
        this.logoOpacity = data.value;
      }
      if (data.ev === 'selected-link') {
        this.selectedLink = data.value;
      }
      if (data.ev === 'add-to-basket' && data.value === 'update') {
        this.updateBasket();
      }
      if (data.ev === 'pageLoading') {
        this.logoOpacity = data.value ? 0 : 1;
      }

    }, error => {
      console.log('Error: ' + error);
    });
  }

  ngOnInit() {
    this.selectedLink = null;
    this.routeToLink( this.topMenus[0].link );
  }

  public routeToLink(link) {
      if( this.selectedLink != link ){
      this.router.navigate([link]);
      this.selectedLink = link;
    }
  }

  selectCity( city ){
    this.companyService.initBootstrapApp( city.name);
    this.showDialog = false;
  }

  openCityModal(){
    this.showDialog = true;
  }

  updateBasket(){
    this.basketPrice = this._globalService.getBasketPrice();
  }


}
