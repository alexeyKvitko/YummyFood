import { Component, OnInit } from '@angular/core';
import {trigger, state, style, animate, transition, animation, query, stagger} from '@angular/animations';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss'],
  animations: [
    trigger('triggerBackgroundChange', [
      transition('* => 1', [
        style({ opacity: 0.5 }),
        animate(1000, style({ opacity: 1 }))
      ]),
      transition('* => 2',  [
        animate(1000, style({ opacity: 0.5 })),
        animate(1000, style({ opacity: 1 }))
      ]),
      transition('* => 3',  [
        animate(1000, style({ opacity: 0.5 })),
        animate(1000, style({ opacity: 1 }))
      ]),
      transition('* => 4',  [
        animate(1000, style({ opacity: 0.5 })),
        animate(1000, style({ opacity: 1 }))
      ])
    ])
  ]
})
export class HomePageComponent implements OnInit {
  homeImgSrc: string;
  imageNum: number = 1;

  constructor() {
  }

  ngOnInit() {
    this.changeBackground();
  }

  changeBackground(){
    this.imageNum ++;
    this.homeImgSrc = 'assets/images/home-'+this.imageNum+'.jpg';
    if( this.imageNum == 5 ){
      this.imageNum = 1;
    }
  }


}
