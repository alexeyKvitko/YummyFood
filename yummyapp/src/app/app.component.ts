import {Component, HostListener} from '@angular/core';

@Component({
  selector: 'app-root',
  template: `<router-outlet></router-outlet>`
})
export class AppComponent {
  title = 'app';


  @HostListener('window:beforeunload', ['$event'])
  beforeunloadHandler(event) {
    if( window.localStorage.getItem("remember-me") == null ||
            window.localStorage.getItem("remember-me") == 'false' ){
      window.localStorage.removeItem("our-client");
    }
  }

}
