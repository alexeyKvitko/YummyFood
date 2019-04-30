import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {GlobalService} from "../../services/global.service";
import {CompanyService} from "../../../services/company.service";

@Component({
  selector: 'fast-menu',
  templateUrl: './fast-menu.component.html',
  styleUrls: ['./fast-menu.component.scss']
})
export class FastMenuComponent implements OnInit {

  @Input()
  top : number ;

  @Input()
  topPercent : number ;

  @Output()
  selectValue: EventEmitter<number> = new EventEmitter<number>();

  topPos: string;
  selected: number = -1;
  showBackground = false;

  constructor( private globalService: GlobalService, private companyService: CompanyService) {
    this.globalService.data$.subscribe(data => {
      if (data.ev === 'fast-menu-pos') {
        if( this.top > 125 ){
          this.topPos = data.value+'px';
          this.showBackground = false;
        } else {
          this.top = 150;
          this.topPos = '150px';
          this.showBackground = true;
        }
        this.top = (+data.value);
      }
      if (data.ev === 'fast-menu-clear') {
        this.selected = -1;
      }
      if (data.ev === 'fast-menu-select') {
        this.selectMenuByDish( data.value );
      }
    }, error => {
      console.log('Error: ' + error);
    });

  }

  ngOnInit() {
    if ( this.top ){
      this.topPos = this.top+'px';
    }
    if ( this.topPercent ){
      this.topPos = this.topPercent+'%';
    }

  }

  selectMenu( val ){
    if( this.selected == val ){
      return;
    }
    this.selected = val;
    this.selectValue.emit( val );
 }

  selectMenuByDish( dishId) {
    let fastMenu = this.companyService.getFastMenuModel();
    if (!fastMenu ){
      return;
    }
    let menuValue = -1;
    fastMenu.pizzaIds.forEach(id => {
      if( dishId == id ){
        menuValue = 1;
      }
    });
    if ( menuValue == -1 ){
      fastMenu.shushiIds.forEach(id => {
        if( dishId == id ){
          menuValue = 2;
        }
      });
    }
    if ( menuValue == -1 ){
      fastMenu.burgerIds.forEach(id => {
        if( dishId == id ){
          menuValue = 3;
        }
      });
    }
    if ( menuValue == -1 ){
      fastMenu.grillIds.forEach(id => {
        if( dishId == id ){
          menuValue = 4;
        }
      });
    }
    if ( menuValue == -1 ){
      fastMenu.wokIds.forEach(id => {
        if( dishId == id ){
          menuValue = 5;
        }
      });
    }
    if( menuValue == -1 ){
      menuValue = 0;
    }

    this.selected = menuValue;

  }


}
