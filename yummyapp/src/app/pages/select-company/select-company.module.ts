import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {routing} from './select-company.routing';

import {SelectCompanyComponent} from "./select-company.component";


@NgModule({
  imports: [
    CommonModule,
    routing
  ],
  declarations: [
    SelectCompanyComponent
  ]
})
export class SelectCompanyModule {
}
