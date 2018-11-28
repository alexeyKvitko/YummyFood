import { Routes, RouterModule } from '@angular/router';
import { DeliveryMenuComponent } from './delivery-menu.component';

const childRoutes: Routes = [
  {
    path: '',
    component: DeliveryMenuComponent
  }
];

export const routing = RouterModule.forChild(childRoutes);
