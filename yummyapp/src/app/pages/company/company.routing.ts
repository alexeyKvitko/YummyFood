import { Routes, RouterModule } from '@angular/router';
import { CompanyComponent } from './company.component';

const childRoutes: Routes = [
  {
    path: '',
    component: CompanyComponent
  }
];

export const routing = RouterModule.forChild(childRoutes);
