import {Component, Input, OnInit} from '@angular/core';
import { GlobalService } from '../../services/global.service';
import {Router} from "@angular/router";
import { menuService } from '../../services/menu.service';
import {TOP_MENU} from "./top-menu";
import {DictionaryModel} from "../../../model/dictionary.model";
import {CompanyService} from "../../../services/company.service";
import {animate, state, style, transition, trigger} from "@angular/animations";
import swal from "sweetalert2";


@Component({
  selector: 'pages-top',
  templateUrl: './pages-top.component.html',
  styleUrls: ['./pages-top.component.scss'],
  providers: [menuService],
  animations: [
    trigger('animateBasket',

      [
      state('initial', style({
        opacity:'0',
      })),
      state('final', style({
        opacity:'1',
      })),
      transition('final=>initial', [
        animate('600ms ease-out', style({ transform: 'scale3d(.0, .0, .0)' }))
      ])
    ]
    ),
  ]
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
  basketState: string = 'initial';
  basketEntityImage: string = '';
  isUserAuth: boolean = false;

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
        if( data.value != null ){
          this.selectedLink = data.value;
        }
        if( window.localStorage.getItem("our-client") != null ){
          this.isUserAuth = true;
        }
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
    if ( link.trim().length == 0 ){
      swal('В разработке ...');
      return;
    }
      if( this.selectedLink != link ){
      this.router.navigate([link]);
      this.selectedLink = link;
    }
  }

  selectCity( city ){
    this.showDialog = false;
    if( this.deliveryCity == city.displayName) {
      return;
    }
    if( this.deliveryCity != city.displayName && this.basketPrice > 0 ){
      swal({
        title: 'Внимание...',
        text: "При смене города, Ваш заказ будет утерян, продождить ?",
        type: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#16be9a',
        cancelButtonColor: '#ff7403',
        confirmButtonText: 'ДА',
        cancelButtonText: 'НЕТ'
      }).then((result) => {
        if (result.value) {
          this.changeDeliveryCity( city )
        } else {
          return;
        }
      })
    } else {
      this.changeDeliveryCity( city );
    }
  }


  changeDeliveryCity( city ){
    this._globalService.clearBasket();
    this.companyService.removeCompaniesFromBasket();
    this._globalService.dataBusChanged("add-to-basket","update");
    swal('Город доставки: '+city.displayName);
    this.selectedLink = 'pages/home-page';
    this.companyService.initBootstrapAppWithLink( city.latitude, city.longitude, this.selectedLink );
    this._globalService.data$.subscribe(data => {
      if (data.ev === 'data-loaded') {
        if (data.value) {
          console.log("CITY CHANGEDING");
          this.router.navigate(['pages/home-page']);
        }
      }
    });

  }

  registration(){
    this.selectedLink = null;
    this.router.navigate(['pages/registration']);
  }

  showLoginDialog(){
    this._globalService.dataBusChanged('show-login-dialog', true);
  }

  showEmailDialog(){
    this._globalService.dataBusChanged('show-email-dialog', true);
  }

  openCityModal(){
    this.showDialog = true;
  }

  updateBasket(){
    this.basketState = 'final';
    this.basketPrice = this._globalService.getBasketPrice();
    this.basketEntityImage = this._globalService.getEntityImg();
  }

  showBasket(){
    this.selectedLink = null;
    this.router.navigate(['pages/basket']);
  }

  onAnimationEvent(event){
    this.basketState = 'initial';
  }

  showPesonalArea(){}

  logout(){
    window.localStorage.removeItem("our-client");
    this.isUserAuth = false;
  }
}
