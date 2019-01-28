import {Component, Input, OnInit} from '@angular/core';
import { GlobalService } from '../../services/global.service';
import {Router} from "@angular/router";
import { menuService } from '../../services/menu.service';
import {TOP_MENU} from "./top-menu";
import {DictionaryModel} from "../../../model/dictionary.model";
import {CompanyService} from "../../../services/company.service";
import {animate, state, style, transition, trigger} from "@angular/animations";


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
        // top:'0px',
        // left:'50%'
      })),
      state('final', style({
        opacity:'1',
      })),
      transition('final=>initial', [
        animate('800ms ease-out', style({ transform: 'scale3d(.0, .0, .0)' }))
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

}
