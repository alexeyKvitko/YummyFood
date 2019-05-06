import { Component, OnInit, Input, Output, OnChanges, EventEmitter } from '@angular/core';
import {animate, style, transition, trigger} from "@angular/animations";

@Component({
  selector: 'app-dialog',
  templateUrl: './app-dialog.component.html',
  styleUrls: ['./app-dialog.component.scss'],
  animations: [
    trigger('dialog', [
      transition('void => *', [
        style({ transform: 'scale3d(.3, .3, .3)' }),
        animate(100)
      ]),
      transition('* => void', [
        animate(100, style({ transform: 'scale3d(.0, .0, .0)' }))
      ])
    ])
  ]
})
export class DialogComponent implements OnInit {
  @Input() closable = true;
  @Input() visible: boolean;
  @Input() type: string
  @Output() visibleChange: EventEmitter<boolean> = new EventEmitter<boolean>();

  dialogClass: string = 'dialog';

  constructor() { }

  ngOnInit() {
    console.log('this.type',this.type);
    if( this.type && this.type === "login" ){
      this.dialogClass = "login-dialog";
    } else {
      if( this.type && this.type === "email" ){
        this.dialogClass = "email-dialog";
      }
    }
  }

  close() {
    this.visible = false;
    this.visibleChange.emit(this.visible);
  }
}
