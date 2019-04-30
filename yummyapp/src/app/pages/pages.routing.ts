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
      { path: 'dish-page', loadChildren: './dish-page/dish-page.module#DishPageModule' },
      { path: 'basket', loadChildren: './basket/basket.module#BasketModule'},
      { path: 'registration', loadChildren: './registration/registration.module#RegistrationModule'},
      { path: 'delivery-menu', loadChildren: './delivery-menu/delivery-menu.module#DeliveryMenuModule' },
      { path: 'company-edit', loadChildren: './company-edit/company-edit.module#CompanyEditModule' },
      { path: 'company-info', loadChildren: './company-info/company-info.module#CompanyInfoModule' },
      { path: 'company-detail', loadChildren: './company-detail/company-detail.module#CompanyDetailModule' },
      { path: ':company', loadChildren: './company-detail/company-detail.module#CompanyDetailModule' }
    ]
}
];

export const routing = RouterModule.forChild(childRoutes);
