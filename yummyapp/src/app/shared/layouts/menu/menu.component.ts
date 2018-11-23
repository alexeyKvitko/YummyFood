import {Component, Input, OnInit} from '@angular/core';
import { collapse } from '../../animation/collapse-animate';
import {menuService} from "../../services/menu.service";

@Component({
  selector: 'du-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss'],
  animations: [collapse]
})
export class MenuComponent {
  selectedPath: string;
  @Input() menuInfo: any;

  constructor( private menuService: menuService) {
    menuService.selectItem( menuService.putSidebarJson() );
    this.selectedPath = window.localStorage.getItem('activeMenuPath');
  }

  private isToggleOn(item) {
    item.toggle === 'on' ? item.toggle = 'off' : item.toggle = 'on';
  }

  private _selectItem(item) {
    this.selectedPath = item.path
    window.localStorage.setItem('activeMenuPath',this.selectedPath);
  }
}
