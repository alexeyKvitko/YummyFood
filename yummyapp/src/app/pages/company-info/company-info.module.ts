import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { routing } from './company-info.routing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../../shared/shared.module';
import { NgxPaginationModule } from 'ngx-pagination';
import { CompanyInfoComponent } from './company-info.component';
import { ClipboardModule } from 'ngx-clipboard';
import { SimpleNotificationsModule } from 'angular2-notifications';

@NgModule({
  imports: [
    CommonModule,
    NgxPaginationModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    SimpleNotificationsModule.forRoot(),
    ClipboardModule,
    routing
  ],
  declarations: [
    CompanyInfoComponent
  ]
})
export class CompanyInfoModule { }
