import { Component, Input } from '@angular/core';
import { GlobalService } from '../../services/global.service';
import {Router} from "@angular/router";

@Component({
  selector: 'pages-top',
  templateUrl: './pages-top.component.html',
  styleUrls: ['./pages-top.component.scss'],
})
export class PagesTopComponent {
  avatarImgSrc: string = 'assets/images/logo.png';
  btnBackImgSrc: string = 'assets/images/buttons/back.png';
  sidebarToggle: boolean = true;
  headerTitle: string;
  showIcon: boolean = true;
  menuType: string;
  menuCategory: string;
  companyUrl: string;

  tip = { ring: true, email: true };

  constructor(private _globalService: GlobalService,private router: Router) {
    this._globalService.data$.subscribe(data => {
      if (data.ev === 'headerTitle') {
        this.headerTitle = data.value;
      }
      if (data.ev === 'companyUrl') {
        this.companyUrl = data.value;
      }
      if (data.ev === 'showIcon') {
        this.showIcon = data.value;
      }
      if (data.ev === 'menuType') {
        this.menuType = data.value;
      }
      if (data.ev === 'menuCategory') {
        this.menuCategory = data.value;
      }
    }, error => {
      console.log('Error: ' + error);
    });
  }

  public back() {
    this.router.navigate(['pages/company']);
  }

  public _sidebarToggle() {
    this._globalService.data$.subscribe(data => {
      if (data.ev === 'sidebarToggle') {
        this.sidebarToggle = data.value;
      }
    }, error => {
      console.log('Error: ' + error);
    });
    this._globalService.dataBusChanged('sidebarToggle', !this.sidebarToggle);
  }


}
