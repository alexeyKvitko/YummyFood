import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import {MenuEntityModel} from "../../../model/menu-entity.model";

@Component({
  selector: 'company-entity',
  templateUrl: './company-entity.component.html',
  styleUrls: ['./company-entity.component.scss']
})
export class CompanyEntityComponent implements OnInit {
  @Input()
  menuEntity: MenuEntityModel = new MenuEntityModel();

  private selWeight: string;
  private selSize: string;
  private selPrice: string;

  constructor() {
  }

  ngOnInit() {
    this.selWeight = this.menuEntity.weightOne;
    this.selSize = this.menuEntity.sizeOne;
    this.selPrice = this.menuEntity.priceOne;
  }

  isHidden(obj){
    let hidden = true;
    if ( obj == null){
      hidden = false;
    }
    return hidden;
  }

  selectPrice( sel: string ){
    switch (sel) {
      case 'One': {
        this.selWeight = this.menuEntity.weightOne;
        this.selSize = this.menuEntity.sizeOne;
        this.selPrice = this.menuEntity.priceOne;
        break;
      }
      case 'Two': {
        this.selWeight = this.menuEntity.weightTwo;
        this.selSize = this.menuEntity.sizeTwo;
        this.selPrice = this.menuEntity.priceTwo;
        break;
      }
      case 'Three': {
        this.selWeight = this.menuEntity.weightThree;
        this.selSize = this.menuEntity.sizeThree;
        this.selPrice = this.menuEntity.priceThree;
        break;
      }
      case 'Four': {
        this.selWeight = this.menuEntity.weightFour;
        this.selSize = this.menuEntity.sizeFour;
        this.selPrice = this.menuEntity.priceFour;
        break;
      }
      default: {
        break;
      }
    }
  }

}
