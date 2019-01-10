import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { routing } from './company-detail.routing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../../shared/shared.module';
import {CompanyDetailComponent} from "./company-detail.component";
import {TrackScrollDirective} from "../../directives/track-scroll";


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    routing
  ],
  declarations: [
    CompanyDetailComponent,
    TrackScrollDirective
  ]
})
export class CompanyDetailModule { }
