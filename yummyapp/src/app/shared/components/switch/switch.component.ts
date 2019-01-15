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
  checked: boolean = false;

  @Output()
  selectValue: EventEmitter<number> = new EventEmitter<number>();

  constructor() { }
  ngOnInit() {
     console.log('checked',this.checked);
  }

  switch(param){
    this.checked = param;
    let val = param ? 1 : 0;
    this.selectValue.emit( val );
  }

}
