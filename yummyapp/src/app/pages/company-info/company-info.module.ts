import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { routing } from './company-info.routing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../../shared/shared.module';
import { NgxPaginationModule } from 'ngx-pagination';
import { CompanyInfoComponent } from './company-info.component';

@NgModule({
  imports: [
    CommonModule,
    NgxPaginationModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    routing
  ],
  declarations: [
    CompanyInfoComponent
  ]
})
export class CompanyInfoModule { }
