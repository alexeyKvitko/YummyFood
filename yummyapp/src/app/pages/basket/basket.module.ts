import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { routing } from './basket.routing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { SharedModule } from '../../shared/shared.module';
import { BasketComponent } from './basket.component';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    SharedModule,
    routing
  ],
  declarations: [
    BasketComponent
  ]
})
export class BasketModule { }
