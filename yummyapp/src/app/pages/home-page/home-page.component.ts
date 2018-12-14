import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent implements OnInit {
  homeImgSrc: string;
  imageNum: number = 1;
  imageUrlArray: string[] = ['assets/images/home-3.jpg',
    'assets/images/home-2.jpg',
    'assets/images/home-1.jpg',
    'assets/images/home-4.jpg'];


  constructor() {

  }

  ngOnInit() {

  }

  changeBackground(){
    this.imageNum ++;
    this.homeImgSrc = 'assets/images/home-'+this.imageNum+'.jpg';
    if( this.imageNum == 5 ){
      this.imageNum = 1;
    }
  }


}
