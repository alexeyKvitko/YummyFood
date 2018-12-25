import {Component, Input, OnInit} from '@angular/core';
import { GlobalService } from '../../services/global.service';
import {Router} from "@angular/router";
import { menuService } from '../../services/menu.service';
import {TOP_MENU} from "./top-menu";

@Component({
  selector: 'pages-top',
  templateUrl: './pages-top.component.html',
  styleUrls: ['./pages-top.component.scss'],
  providers: [menuService]
})
export class PagesTopComponent implements OnInit{
  avatarImgSrc: string = 'assets/images/logo.png';
  btnBackImgSrc: string = 'assets/images/buttons/back.png';
  sidebarToggle: boolean = true;
  headerTitle: string;
  showIcon: boolean = true;
  menuType: string;
  menuCategory: string;
  companyUrl: string;
  topMenus: Array<any> = TOP_MENU;
  selectedLink: string;

  constructor( private _globalService: GlobalService,private router: Router ) {

  }

  ngOnInit() {
    this.selectedLink = null;
    this.routeToLink( this.topMenus[0].link );
  }

  public routeToLink(link) {
      if( this.selectedLink != link ){
      this.router.navigate([link]);
      this.selectedLink = link;
    }
  }



}
