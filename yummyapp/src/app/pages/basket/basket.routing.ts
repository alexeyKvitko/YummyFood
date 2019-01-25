import { Routes, RouterModule } from '@angular/router';
import { BasketComponent } from './basket.component';

const childRoutes: Routes = [
  {
    path: '',
    component: BasketComponent
  }
];

export const routing = RouterModule.forChild(childRoutes);
