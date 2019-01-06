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
      { path: 'home-page', loadChildren: './home-page/home-page.module#HomePageModule'},
      { path: 'company', loadChildren: './company/company.module#CompanyModule'},
      { path: 'delivery-menu', loadChildren: './delivery-menu/delivery-menu.module#DeliveryMenuModule' },
      { path: 'company-edit', loadChildren: './company-edit/company-edit.module#CompanyEditModule' },
      { path: 'company-detail', loadChildren: './company-detail/company-detail.module#CompanyDetailModule' }
    ]
}
];

export const routing = RouterModule.forChild(childRoutes);
