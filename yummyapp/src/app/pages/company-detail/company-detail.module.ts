Simport { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { routing } from './company-detail.routing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../../shared/shared.module';
import {CompanyDetailComponent} from "./company-detail.component";


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    routing
  ],
  declarations: [
    CompanyDetailComponent
  ]
})
export class CompanyDetailModule { }
