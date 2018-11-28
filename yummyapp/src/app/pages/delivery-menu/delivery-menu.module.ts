import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { routing } from './delivery-menu.routing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../../shared/shared.module';
import { DeliveryMenuComponent } from './delivery-menu.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    routing
  ],
  declarations: [
    DeliveryMenuComponent
  ]
})
export class DeliveryMenuModule { }
