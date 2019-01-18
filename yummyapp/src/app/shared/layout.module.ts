import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { SharedModule } from './shared.module';

import { GlobalService } from './services/global.service';

import { NotificationComponent } from './components/notification/notification.component';
import { LoadingComponent } from './components/loading/loading.component';
import { MenuComponent } from './layouts/menu/menu.component';
import { PagesTopComponent } from './layouts/pages-top/pages-top.component';
import {DialogComponent} from "./layouts/app-dialog/app-dialog.component";

@NgModule({
    imports: [
        CommonModule,
        RouterModule,
        SharedModule
    ],
    providers: [
        GlobalService
    ],
    declarations: [
        MenuComponent,
        PagesTopComponent,
        NotificationComponent,
        LoadingComponent,
        DialogComponent
    ],
    exports: [
        PagesTopComponent,
        NotificationComponent,
        LoadingComponent
    ]
})
export class LayoutModule { }
