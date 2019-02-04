import {Component, Input, OnInit} from '@angular/core';
import {GlobalService} from "../../services/global.service";

@Component({
  selector: 'fast-menu',
  templateUrl: './fast-menu.component.html',
  styleUrls: ['./fast-menu.component.scss']
})
export class FastMenuComponent implements OnInit {

  @Input()
  top : number ;
  topPos: string;
  fmOpacity: number = 1;

  constructor( private globalService: GlobalService) {
    this.globalService.data$.subscribe(data => {
      if (data.ev === 'fast-menu-pos') {
        this.topPos = data.value+'px';
        this.top = (+data.value);
        this.fmOpacity = 1;
        // if ( this.top < 94 ){
        //   this.fmOpacity = 1 - (this.top /100);
        // }
      }
    }, error => {
      console.log('Error: ' + error);
    });

  }

  ngOnInit() {
    this.topPos = this.top+'px';
  }

}
