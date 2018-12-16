import { Component, OnInit } from '@angular/core';
import { CATALOG } from '../../pages/home-page/catalog';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.scss']
})
export class HomePageComponent implements OnInit {
  logoImgSrc: string = 'assets/images/logo.png';
  imageNum: number = 1;
  catalogItems = CATALOG;
  imageUrlArray: string[] = ['assets/images/home-3.jpg',
    'assets/images/home-2.jpg',
    'assets/images/home-1.jpg',
    'assets/images/home-4.jpg'];


  constructor() {
  }

  ngOnInit() {

  }

}
