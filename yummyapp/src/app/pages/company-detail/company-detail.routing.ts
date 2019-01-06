import { Routes, RouterModule } from '@angular/router';
import { CompanyDetailComponent } from './company-detail.component';

const childRoutes: Routes = [
  {
    path: '',
    component: CompanyDetailComponent
  }


];

export const routing = RouterModule.forChild(childRoutes);
