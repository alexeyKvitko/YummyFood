import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {GlobalService} from "../../services/global.service";

@Component({
  selector: 'fast-menu',
  templateUrl: './fast-menu.component.html',
  styleUrls: ['./fast-menu.component.scss']
})
export class FastMenuComponent implements OnInit {

  @Input()
  top : number ;

  @Output()
  selectValue: EventEmitter<number> = new EventEmitter<number>();

  topPos: string;
  selected: number = -1;
  showBackground = false;

  constructor( private globalService: GlobalService) {
    this.globalService.data$.subscribe(data => {
      if (data.ev === 'fast-menu-pos') {
        if( this.top > 110 ){
          this.topPos = data.value+'px';
          this.showBackground = false;
        } else {
          this.topPos = '112px';
          this.showBackground = true;
        }
        this.top = (+data.value);
      }
      if (data.ev === 'fast-menu-clear') {
        this.selected = -1;
      }
    }, error => {
      console.log('Error: ' + error);
    });

  }

  ngOnInit() {
    this.topPos = this.top+'px';
  }

  selectMenu( val ){
    if( this.selected == val ){
      return;
    }
    this.selected = val;
    this.selectValue.emit( val );
  }

}
