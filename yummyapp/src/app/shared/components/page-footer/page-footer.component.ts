import { Component, OnInit } from '@angular/core';
import {SOCIAL} from "./const-social";

@Component({
  selector: 'page-footer',
  templateUrl: './page-footer.component.html',
  styleUrls: ['./page-footer.component.scss']
})
export class PageFooterComponent implements OnInit {

  socialIcons = SOCIAL;

  constructor() { }

  ngOnInit() {
  }

}
