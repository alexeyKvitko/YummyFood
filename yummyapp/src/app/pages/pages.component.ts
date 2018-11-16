import { Component } from '@angular/core';
import { GlobalService } from '../shared/services/global.service';

@Component({
  selector: 'app-pages',
  templateUrl: './pages.component.html',
  styleUrls: ['./pages.component.scss']
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
