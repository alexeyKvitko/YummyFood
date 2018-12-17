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
        top:'0px',
        left:'50%'
      })),
      state('final', style({
        opacity:'1',
        top:'38px',
        left:'15%'
      })),
      transition('initial=>final', animate('1000ms')),
      transition('final=>initial', animate('200ms'))
    ]),
  ]
})
export class HomePageComponent implements OnInit {
  @ViewChild(TrackScrollDirective) scroll: TrackScrollDirective;
  logoImgSrc: string = 'assets/images/logo.png';
  currentState = 'initial';
  catalogItems = CATALOG;
  imageUrlArray: string[] = ['assets/images/home-3.jpg',
    'assets/images/home-2.jpg',
    'assets/images/home-1.jpg',
    'assets/images/home-4.jpg'];

 divClick(){
   this.currentState = this.currentState === 'initial' ? 'final' : 'initial';
 }
  scrollPercent: number = 0;

  constructor() {
  }

  track(value: number): void {
    this.scrollPercent = value;
  }

  isWelcomeHidden(){
    if ( this.scrollPercent > 30 ) {
      return true;
    } else {
      return false;
    }
  }

}