import { Component, OnInit } from '@angular/core';
import {SOCIAL} from "./const-social";
import {PAY_TYPE} from "./const-pay";

@Component({
  selector: 'page-footer',
  templateUrl: './page-footer.component.html',
  styleUrls: ['./page-footer.component.scss']
})
export class PageFooterComponent implements OnInit {

  socialIcons = SOCIAL;

  payTypeIcons = PAY_TYPE;

  constructor() { }

  ngOnInit() {
  }

}
