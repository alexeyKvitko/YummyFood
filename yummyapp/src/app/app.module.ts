import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CustomMaterialModule } from './shared/material.module';
import { CompanyComponent } from './company/company.component';
import { CompanyService} from './services/company.service';
import { LoginComponent } from './login/login.component';
import { LoginService} from "./services/login.service";
import { HTTP_INTERCEPTORS, HttpClientModule } from "@angular/common/http";
import { ReactiveFormsModule } from "@angular/forms";
import { TokenInterceptor } from "./services/token-interceptor.service";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  declarations: [
    AppComponent,
    CompanyComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    ReactiveFormsModule,
    CustomMaterialModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [CompanyService,LoginService,{provide: HTTP_INTERCEPTORS,
    useClass: TokenInterceptor,
    multi : true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
