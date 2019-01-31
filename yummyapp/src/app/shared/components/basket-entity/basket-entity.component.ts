import {Component, Input, OnInit} from '@angular/core';
import {MenuEntityModel} from "../../../model/menu-entity.model";
import {GlobalService} from "../../services/global.service";

@Component({
  selector: 'basket-entity',
  templateUrl: './basket-entity.component.html',
  styleUrls: ['./basket-entity.component.scss']
})
export class BasketEntityComponent implements OnInit {

  @Input()
  menuEntity: MenuEntityModel = new MenuEntityModel();
  selectedWeight: string = null;
  selectedSize: string = null;
  selectedPrice: string;
  total: number;

  constructor( private globalService :GlobalService ) { }

  ngOnInit() {
    if( this.menuEntity != null ){
      switch( this.menuEntity.wspType ){
        case 'One' :
            this.selectedWeight = this.menuEntity.weightOne;
            this.selectedSize = this.menuEntity.sizeOne;
            this.selectedPrice = this.menuEntity.priceOne;
          break;
        case 'Two' :
          this.selectedWeight = this.menuEntity.weightTwo;
          this.selectedSize = this.menuEntity.sizeTwo;
          this.selectedPrice = this.menuEntity.priceTwo;
          break;
        case 'Three' :
          this.selectedWeight = this.menuEntity.weightThree;
          this.selectedSize = this.menuEntity.sizeThree;
          this.selectedPrice = this.menuEntity.priceThree;
          break;
        case 'Four' :
          this.selectedWeight = this.menuEntity.weightFour;
          this.selectedSize = this.menuEntity.sizeFour;
          this.selectedPrice = this.menuEntity.priceFour;
          break;
      }
      if( this.selectedWeight != null && this.selectedWeight.trim().length == 0 ){
        this.selectedWeight = null;
      }
      if( this.selectedSize != null && this.selectedSize.trim().length == 0 ){
        this.selectedSize = null;
      }
      this.total = (+this.selectedPrice)*this.menuEntity.count;
    }
  }

  changeEntityCount( value: number ){
    this.globalService.changeEntityCountInBasket( this.menuEntity, value );
    this.total = (+this.selectedPrice)*this.menuEntity.count;
    this.globalService.dataBusChanged("add-to-basket","update");
    // this.companyService.addCompanyToBasket( this.menuEntity.companyId );
  }



}
