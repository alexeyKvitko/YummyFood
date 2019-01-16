import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';

@Component({
  selector: 'switch',
  templateUrl: './switch.component.html',
  styleUrls: ['./switch.component.scss']
})
export class SwitchComponent implements OnInit {
  @Input()
  switchW: string;

  @Input()
  switchH: string;

  @Input()
  switchId: string;

  @Input()
  fromStr: string = 'default';

  @Input()
  toStr: string = 'default';

  @Input()
  initValToStr: string;

  checked: boolean;

  @Output()
  selectValue: EventEmitter<number> = new EventEmitter<number>();

  constructor() { }
  ngOnInit() {
    if( this.initValToStr == 'true' ){
      this.checked = true;
    } else if( this.initValToStr == 'false' ){
      this.checked = false;
    }
      this.switch(this.checked);
  }

  switch(param){
    this.checked = param;
    let val = param == true ? 1 : 0;
    console.log('param',this.checked,param, val);
    this.selectValue.emit( val );
  }

}
