import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { routing } from './company-info.routing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../../shared/shared.module';
import { NgxPaginationModule } from 'ngx-pagination';
import { CompanyInfoComponent } from './company-info.component';
import { ClipboardModule } from 'ngx-clipboard';
import { NgxNotificationComponent } from 'ngx-notification';

@NgModule({
  imports: [
    CommonModule,
    NgxPaginationModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    ClipboardModule,
    routing
  ],
  declarations: [
    NgxNotificationComponent,
    CompanyInfoComponent
  ]
})
export class CompanyInfoModule { }
