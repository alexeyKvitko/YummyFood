import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { routing } from './company.routing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../../shared/shared.module';
import { CompanyComponent } from './company.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    routing
  ],
  declarations: [
    CompanyComponent
  ]
})
export class CompanyModule { }
