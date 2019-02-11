import {Component, EventEmitter, OnInit, Output} from '@angular/core';

@Component({
  selector: 'phone-input',
  templateUrl: './phone-input.component.html',
  styleUrls: ['./phone-input.component.scss']
})
export class PhoneInputComponent implements OnInit {

  phone: string[] =  new Array();

  @Output()
  phoneValue: EventEmitter<string> = new EventEmitter<string>();

  constructor() { }

  ngOnInit() {
  }

  changeFocus( evn, back, forward, index ){
    let nextId = "digit-id-";
    if( evn.code == "ArrowLeft" || evn.code == "Backspace" ){
      nextId += back;
    } else {
      nextId += forward;
    }
    document.getElementById(nextId).focus();
    this.phone[ index ] = evn.target.value;
    let phoneStr = "";
    for( let i = 0;i< 10;i++){
      if( this.phone[i] ){
        phoneStr += this.phone[i];
      }
    }
    this.phoneValue.emit( phoneStr );
  }

}
