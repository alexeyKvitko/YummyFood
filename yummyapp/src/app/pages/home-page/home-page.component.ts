import {Component, OnInit, ViewChild} from '@angular/core';
import {CATALOG} from '../../pages/home-page/catalog';
import {animate, state, style, transition, trigger} from "@angular/animations";
import {TrackScrollDirective} from "../../directives/track-scroll";
import {take} from 'rxjs/operators';
import {timer} from "rxjs";

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
        left: '15%'
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
    trigger('changeInvaitePosition', [
      state('inviteOne', style({
        opacity: '1',
        left: '80%'
      })),
      state('inviteTwo', style({
        opacity: '0',
        left: '80%'
      })),
      state('inviteThree', style({
        opacity: '0',
        left: '15%'
      })),
      state('inviteFour', style({
        opacity: '1',
        left: '15%'
      })),
      transition('inviteOne=>inviteTwo', animate('1000ms')),
      transition('inviteTwo=>inviteThree', animate('600ms')),
      transition('inviteThree=>inviteFour', animate('1000ms')),
      transition('inviteFour=>inviteOne', animate('1000ms')),
    ])
  ]
})
export class HomePageComponent {
  @ViewChild(TrackScrollDirective) scroll: TrackScrollDirective;
  inviteImgSrc: string = 'assets/images/invite.png';
  animArr: string[] = ['inviteOne','inviteTwo','inviteThree','inviteFour'];
  invitePos: string = 'inviteOne';
  pos: number = 1;
  logoImgSrc: string = 'assets/images/logo.png';
  currentState = 'initial';
  catalogItems = CATALOG;
  inviteTimer = timer(1500, 2500);

  imageUrlArray: string[] = ['assets/images/slide-2.jpg',
    'assets/images/back-1.jpg','assets/images/slide-1.jpg','assets/images/back-3.jpg'];

  scrollPercent: number = 0;

  constructor() {
    // timer(1000, 5000).pipe(
    //   take(10000))
    this.inviteTimer.subscribe(x => {
      this.divClick();
    })
  }

  divClick() {
    if( this.pos == 4){
      this.pos = 0;
    }
    this.invitePos = this.animArr[this.pos];
    this.pos++;
  }


  track(value: number): void {
    if (value > 15) {
      this.currentState = 'final';
    } else {
      this.currentState = 'initial';
    }
    this.scrollPercent = value;
  }


}
