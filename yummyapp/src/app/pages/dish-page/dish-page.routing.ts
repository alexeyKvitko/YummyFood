import { Routes, RouterModule } from '@angular/router';
import {DishPageComponent} from "./dish-page.component";

const childRoutes: Routes = [
  {
    path: '',
    component: DishPageComponent
  }
];

export const routing = RouterModule.forChild(childRoutes);
