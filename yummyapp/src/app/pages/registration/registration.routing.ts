import { Routes, RouterModule } from '@angular/router';
import {RegistrationComponent} from "./registration.component";

const childRoutes: Routes = [
  {
    path: '',
    component: RegistrationComponent
  }
];

export const routing = RouterModule.forChild(childRoutes);
