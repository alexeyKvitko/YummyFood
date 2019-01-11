import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {HomePageComponent} from './home-page.component';
import {routing} from './home-page.routing';
import {SharedModule} from '../../shared/shared.module';
import {SlideshowModule} from 'ng-simple-slideshow';
import {TrackScrollDirective} from "../../directives/track-scroll";


@NgModule({
  imports: [
    SlideshowModule,
    CommonModule,
    SharedModule,
    routing
  ],
  declarations: [
    HomePageComponent
    ,TrackScrollDirective
  ]
})
export class HomePageModule {
}
