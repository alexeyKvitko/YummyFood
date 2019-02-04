import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

/* components */
import { CardComponent } from './components/card/card.component';
import { TabsetComponent } from './components/tabset/tabset.component';
import { TabContentComponent } from './components/tabset/tab-content/tab-content.component';
import { ProgressBarComponent } from './components/progress-bar/progress-bar.component';
import { FileTreeComponent } from './components/file-tree/file-tree.component';
import { SwitchComponent } from './components/switch/switch.component';
import { AlertComponent } from './components/alert/alert.component';
import { ProfileComponent } from './components/profile/profile.component';
import { CompanyCardComponent } from "./components/company-card/company-card.component";
import {CompanyEntityComponent} from "./components/company-entity/company-entity.component";
import {DialogComponent} from "./layouts/app-dialog/app-dialog.component";
import {BasketCompanyComponent} from "./components/basket-company/basket-company.component";
import {BasketEntityComponent} from "./components/basket-entity/basket-entity.component";
import {PageFooterComponent} from "./components/page-footer/page-footer.component";
import {SocialCardComponent} from "./components/social-card/social-card.component";

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [
    CardComponent,
    FileTreeComponent,
    TabsetComponent,
    TabContentComponent,
    ProgressBarComponent,
    SwitchComponent,
    CompanyCardComponent,
    CompanyEntityComponent,
    BasketCompanyComponent,
    BasketEntityComponent,
    PageFooterComponent,
    SocialCardComponent,
    AlertComponent,
    ProfileComponent,
    DialogComponent
  ],
  exports: [
    CardComponent,
    FileTreeComponent,
    TabsetComponent,
    TabContentComponent,
    ProgressBarComponent,
    CompanyCardComponent,
    CompanyEntityComponent,
    BasketCompanyComponent,
    BasketEntityComponent,
    SocialCardComponent,
    PageFooterComponent,
    SwitchComponent,
    AlertComponent,
    ProfileComponent,
    DialogComponent
  ]
})
export class SharedModule { }
