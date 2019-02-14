import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';

@Component({
  selector: 'phone-input',
  templateUrl: './phone-input.component.html',
  styleUrls: ['./phone-input.component.scss']
})
export class PhoneInputComponent implements OnInit {

 @Input()
 existPhone: string = null;

  phone: string[] =  new Array();

  @Output()
  phoneValue: EventEmitter<string> = new EventEmitter<string>();

  constructor() { }

  ngOnInit() {
    if ( this.existPhone != null ){
      for( let idx=0; idx<this.existPhone.length; idx++ ){
        let nextId = "digit-id-"+(idx+1);
        this.phone[idx] = this.existPhone[idx];
        document.getElementById( nextId ).setAttribute('value',this.existPhone[idx]);
      }
    }

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
