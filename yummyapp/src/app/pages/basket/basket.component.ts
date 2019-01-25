import { Component, OnInit } from '@angular/core';
import {GlobalService} from "../../shared/services/global.service";

@Component({
  selector: 'app-basket',
  templateUrl: './basket.component.html',
  styleUrls: ['./basket.component.scss']
})
export class BasketComponent implements OnInit {

  basketprice : number = 0;

  constructor(private  globalService : GlobalService) { }

  ngOnInit() {
    this.basketprice = this.globalService.getBasketPrice();
  }

}
