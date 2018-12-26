import { Component, OnInit, Input } from '@angular/core';

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
  checked: boolean = true;

  constructor() { }
  ngOnInit() {
  }

  switch(param){
    this.checked = param;
  }

}
