import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {SharedModule} from '../../shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {RegistrationComponent} from "./registration.component";
import {routing} from "./registration.routing";


@NgModule({
  imports: [
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    SharedModule,
    routing
  ],
  declarations: [
    RegistrationComponent
  ]
})
export class RegistrationModule {
}
