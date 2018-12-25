import { Component } from '@angular/core';
import { GlobalService } from '../shared/services/global.service';
import {RouterOutlet} from "@angular/router";
import {pageRouteAnimation} from "./page-animation";

@Component({
  selector: 'app-pages',
  templateUrl: './pages.component.html',
  styleUrls: ['./pages.component.scss'], animations: [
    pageRouteAnimation
  ]
})

export class PagesComponent {

  loading: boolean = false;

  constructor(private _globalService: GlobalService) {
    this.init();
  }

  public init(){
    this._globalService.data$.subscribe(data => {
      if (data.ev === 'pageLoading') {
        this.loading = data.value;
      }
    }, error => {
      console.log('Error: ' + error);
    });
  }

}
