import { Component, OnInit } from '@angular/core';
import {GlobalService} from "../../services/global.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {ClientService} from "../../../services/client.service";

@Component({
  selector: 'email-dialog',
  templateUrl: './email-dialog.component.html',
  styleUrls: ['./email-dialog.component.scss']
})
export class EmailDialogComponent implements OnInit {

  emailForm: FormGroup;
  showEmailDialog: boolean = false;
  sendError: string = null;
  interval;


  constructor( private _globalService: GlobalService,private formBuilder: FormBuilder,private clientService: ClientService,
               private router: Router) {
    this._globalService.data$.subscribe(data => {
      if (data.ev === 'show-email-dialog') {
        if ( data.value ){
          this.showEmailDialog = true;
          this.emailForm.get('message').setValue('');
        }
      }
    }, error => {
      console.log('Error: ' + error);
    });
  }

  ngOnInit() {
    this.emailForm = this.formBuilder.group({
      message: ['']
    });

  }

  onSubmit(){

    let message = this.emailForm.controls.message.value.trim();
    if ( message.length == 0 ){
      this.sendError = "Сообщение не может быть пустым";
      this.showErrorMsg( false );
      return;
    }

    this.clientService.sendEmailToUs( message ).subscribe(data => {
      this.sendError = data.message;
      if (data.status == 200) {
        this.showErrorMsg( true );
      } else {
        this.showErrorMsg( false );
      }
    });
  }

  showErrorMsg( closeDlg : boolean){
    this.interval = setInterval(() => {
      this.sendError = null;
      clearInterval(this.interval);
      if ( closeDlg ){
        this.showEmailDialog = false;
      }
    },3000);
  }



}
