import { Component, Input } from '@angular/core';
import { collapse } from '../../animation/collapse-animate';
import { GlobalService } from '../../services/global.service';

@Component({
  selector: 'du-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss'],
  animations: [collapse]
})
export class MenuComponent {
  selectedPath: string;
  @Input() menuInfo: any;

  constructor(private _globalService: GlobalService) {
    this.selectedPath = window.localStorage.getItem('activeMenuPath');
  }

  private isToggleOn(item) {
    item.toggle === 'on' ? item.toggle = 'off' : item.toggle = 'on';
  }


  private _selectItem(item) {
    //this._globalService._isActived(item);
    this.selectedPath = item.path
    window.localStorage.setItem('activeMenuPath',this.selectedPath);
  }
}
