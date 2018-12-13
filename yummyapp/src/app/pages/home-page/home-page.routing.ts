import { Routes, RouterModule } from '@angular/router';
import { HomePageComponent } from './home-page.component';

const childRoutes: Routes = [
    {
        path: '',
        component: HomePageComponent
    }
];

export const routing = RouterModule.forChild(childRoutes);
