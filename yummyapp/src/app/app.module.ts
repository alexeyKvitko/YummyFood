import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';

import { APP_ROUTING } from './app-routing';
import { AppComponent } from './app.component';
import { CompanyService} from './services/company.service';
import { LoginService} from "./services/login.service";

import { HTTP_INTERCEPTORS, HttpClientModule } from "@angular/common/http";
import { TokenInterceptor } from "./services/token-interceptor.service";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { PagesModule } from './pages/pages.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    APP_ROUTING,
    HttpClientModule,
    FormsModule,
    PagesModule
  ],
  providers: [CompanyService,LoginService,{provide: HTTP_INTERCEPTORS,
    useClass: TokenInterceptor,
    multi : true}],
  bootstrap: [AppComponent]
})
export class AppModule { }
