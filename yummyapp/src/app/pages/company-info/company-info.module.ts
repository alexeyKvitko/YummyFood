import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { routing } from './company-info.routing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../../shared/shared.module';
import { CompanyInfoComponent } from './company-info.component';
import { NgxPaginationModule } from 'ngx-pagination';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    NgxPaginationModule,
    routing
  ],
  declarations: [
    CompanyInfoComponent
  ]
})
export class CompanyInfoModule { }
