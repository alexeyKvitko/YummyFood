import { Routes, RouterModule } from '@angular/router';
import { PagesComponent } from './pages.component';
import { LoginComponent } from './login/login.component';

export const childRoutes: Routes = [
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: '',
    component: LoginComponent,
  },
  {
    path: 'pages',
    component: PagesComponent,
    children: [
      { path: '', redirectTo: 'index', pathMatch: 'full' },
      { path: 'company', loadChildren: './company/company.module#CompanyModule' },
      { path: 'company-info', loadChildren: './company-info/company-info.module#CompanyInfoModule' }

      // { path: 'index', loadChildren: './index/index.module#IndexModule' }
    ]
  }
];

export const routing = RouterModule.forChild(childRoutes);
