import {Component, OnInit, ViewChild} from '@angular/core';
import { CATALOG } from '../../pages/home-page/catalog';
import {animate, state, style, transition, trigger} from "@angular/animations";
import {TrackScrollDirective} from "../../directives/track-scroll";

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss'],
  animations: [
    trigger('changeDivSize', [
      state('initial', style({
        opacity:'0',
        top:'-75px',
        width: '250px',
        left:'15%'
      })),
      state('final', style({
        opacity:'1',
        top:'38px',
        width: '130px',
        left:'15%'
      })),
      transition('initial=>final', animate('400ms')),
      transition('final=>initial', animate('200ms'))
    ]),
  ]
})
export class HomePageComponent {
  @ViewChild(TrackScrollDirective) scroll: TrackScrollDirective;
  inviteImgSrc: string = 'assets/images/invite.png';
  logoImgSrc: string = 'assets/images/logo.png';
  currentState = 'initial';
  catalogItems = CATALOG;
  imageUrlArray: string[] = ['assets/images/slide-1.jpg',
    'assets/images/slide-2.jpg',
    'assets/images/slide-3.jpg',
    'assets/images/slide-4.jpg'];

   scrollPercent: number = 0;

  constructor() {
  }

  track(value: number): void {
    if( value > 45 ){
      this.currentState = 'final';
    } else {
      this.currentState = 'initial';
    }
    this.scrollPercent = value;
  }

  isWelcomeHidden(){
    if ( this.scrollPercent > 45 ) {
      return true;
    } else {
      return false;
    }
  }

}
