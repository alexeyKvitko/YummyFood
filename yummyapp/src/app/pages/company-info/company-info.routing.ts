import { Routes, RouterModule } from '@angular/router';
import { CompanyInfoComponent } from './company-info.component';

const childRoutes: Routes = [
  {
    path: '',
    component: CompanyInfoComponent
  }

];

export const routing = RouterModule.forChild(childRoutes);
