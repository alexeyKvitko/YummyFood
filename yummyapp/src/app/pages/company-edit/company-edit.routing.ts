import { Routes, RouterModule } from '@angular/router';
import { CompanyEditComponent } from './company-edit.component';

const childRoutes: Routes = [
  {
    path: '',
    component: CompanyEditComponent
  }


];

export const routing = RouterModule.forChild(childRoutes);
