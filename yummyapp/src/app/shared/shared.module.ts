import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { JsonpModule } from '@angular/http';

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

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    JsonpModule,
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
    AlertComponent,
    ProfileComponent
  ],
  exports: [
    CardComponent,
    FileTreeComponent,
    TabsetComponent,
    TabContentComponent,
    ProgressBarComponent,
    CompanyCardComponent,
    CompanyEntityComponent,
    SwitchComponent,
    AlertComponent,
    ProfileComponent
  ]
})
export class SharedModule { }
