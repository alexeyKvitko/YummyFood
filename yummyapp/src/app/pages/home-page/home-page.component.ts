import {Component, OnInit, ViewChild} from '@angular/core';
import {CATALOG} from '../../pages/home-page/catalog';
import {animate, state, style, transition, trigger} from "@angular/animations";
import {TrackScrollDirective} from "../../directives/track-scroll";

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
    ])
  ]
})
export class HomePageComponent {
  @ViewChild(TrackScrollDirective) scroll: TrackScrollDirective;
  mainScrImg: string = 'assets/images/shahlyk-1.jpg';
  animArr: string[] = ['inviteOne','inviteTwo','inviteThree','inviteFour'];
  invitePos: string = 'inviteOne';
  pos: number = 1;
  logoImgSrc: string = 'assets/images/logo.png';
  currentState = 'initial';
  catalogItems = CATALOG;
  inviteOpacity: number = 1;

  scrollPercent: number = 0;

  constructor() {
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
    this.scrollPercent = value;
  }


}
