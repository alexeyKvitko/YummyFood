import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { routing } from './company-edit.routing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../../shared/shared.module';
import { NgxPaginationModule } from 'ngx-pagination';
import { CompanyEditComponent } from './company-edit.component';

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
    CompanyEditComponent
  ]
})
export class CompanyEditModule { }
