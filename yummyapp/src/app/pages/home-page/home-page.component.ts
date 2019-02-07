import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {animate, state, style, transition, trigger} from "@angular/animations";
import {TrackScrollDirective} from "../../directives/track-scroll";
import {IMAGE_PATHS} from "./const-image-paths";
import {CATALOG} from "./const-catalog";
import {ACTIONS} from "./const-actions";
import {GlobalService} from "../../shared/services/global.service";
import {CompanyService} from "../../services/company.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss'],
  animations: [
    trigger('changeDivSize', [
      state('initial', style({
        opacity: '0',
        top: '-75px',
        width: '130px',
        left: '20%'
      })),
      state('final', style({
        opacity: '1',
        top: '38px',
        width: '130px',
        left: '20%'
      })),
      transition('initial=>final', animate('400ms')),
      transition('final=>initial', animate('200ms'))
    ]),
    trigger('scrollAnimation', [
      state('top', style({
        top: '0'
      })),
      state('bottom',   style({
        top: '80%'
      })),
      transition('top => bottom', animate('700ms ease-out')),
      transition('bottom => top', animate('700ms ease-in'))
    ])
  ]
})
export class HomePageComponent implements OnInit{
  @ViewChild(TrackScrollDirective) scroll: TrackScrollDirective;
  scrollPos: string = 'top';
  currentState = 'initial';
  imagePaths = IMAGE_PATHS;
  catalogItems = CATALOG;
  actionItems = ACTIONS;
  inviteOpacity: number = 1;
  scrollPercent: number = 0;
  deliveryCity: string = "";

  constructor(private _globalService: GlobalService,
                private companyService: CompanyService,
                  private router: Router) {}

  moveTopBottom(){
    if( this.scrollPos === 'top' ){
      document.getElementById("bottom").scrollIntoView({behavior: "smooth", block: "start"});
      this.scrollPos = 'bottom';
    } else {
      document.getElementById("top").scrollIntoView({behavior: "smooth", block: "start"});
      this.scrollPos = 'top';
    }

  }

  track(value: number): void {
    let calc = 1- value/25;
    if( calc < 0 ){
      calc = 0;
    }
    this.inviteOpacity = calc;
    if (value > 20) {
      this.currentState = 'final';
    } else {
      this.currentState = 'initial';
    }
    if ( value > 50 ){
      this.scrollPos = 'bottom';
    } else {
      this.scrollPos = 'top';
    }
    this.scrollPercent = value;
  }

  ngOnInit(): void {
    this._globalService.dataBusChanged('logo-opacity',0);
    this._globalService.data$.subscribe(data => {
      if (data.ev === 'data-loaded') {
        if ( data.value ){
          this.deliveryCity = this.companyService.getDeliveryCity();
          window.localStorage.setItem("delivery-city", this.deliveryCity);
        }
      }
    });
    if ( window.localStorage.getItem("delivery-city") != null ){
      this.deliveryCity = window.localStorage.getItem("delivery-city");
    }
  }

  selectFastMenu( menuVal ){
    window.localStorage.setItem("fast-menu",menuVal);
    let link = 'pages/company';
    this._globalService.dataBusChanged('selected-link', link);
    this.router.navigate([link]);
  }

}
