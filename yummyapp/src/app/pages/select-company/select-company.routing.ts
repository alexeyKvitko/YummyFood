import { Routes, RouterModule } from '@angular/router';
import {SelectCompanyComponent} from "./select-company.component";


const childRoutes: Routes = [
  {
    path: '',
    component: SelectCompanyComponent
  }
];

export const routing = RouterModule.forChild(childRoutes);
