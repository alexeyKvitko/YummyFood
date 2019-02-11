import { Component, OnInit } from '@angular/core';
import {SOCIAL} from "./const-social";
import {PAY_TYPE} from "./const-pay";
import {IMAGE_PATHS} from "../../../pages/home-page/const-image-paths";

@Component({
  selector: 'page-footer',
  templateUrl: './page-footer.component.html',
  styleUrls: ['./page-footer.component.scss']
})
export class PageFooterComponent implements OnInit {

  socialIcons = SOCIAL;

  payTypeIcons = PAY_TYPE;

  imagePaths = IMAGE_PATHS;

  constructor() { }

  ngOnInit() {
  }

}
