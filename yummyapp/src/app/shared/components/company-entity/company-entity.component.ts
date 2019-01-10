import {Component, OnInit, Input, Output, EventEmitter, ViewChild} from '@angular/core';
import {MenuEntityModel} from "../../../model/menu-entity.model";
import {GlobalService} from "../../services/global.service";
import {CompanyService} from "../../../services/company.service";

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
  defaultImg: string = "assets/images/no-photo.png";
  wspType : string = "One";

  constructor(private  globalService : GlobalService, private companyService: CompanyService) {
  }

  ngOnInit() {
    this.selWeight = this.menuEntity.weightOne;
    this.selSize = this.menuEntity.sizeOne;
    this.selPrice = this.menuEntity.priceOne;
    if( this.menuEntity.imageUrl == null ){
      this.menuEntity.imageUrl = this.defaultImg;
    }
  }

  isHidden(obj){
    let hidden = true;
    if ( obj == null){
      hidden = false;
    }
    return hidden;
  }

  selectPrice( sel: string ){
    this.wspType = sel;
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

  addToBasket(){
    this.menuEntity.wspType = this.wspType;
    this.menuEntity.count ++;
    this.globalService.addEntityToBasket( this.menuEntity );
    this.globalService.dataBusChanged("add-to-basket","update");
    this.companyService.addCompanyShortToBasket( this.menuEntity.companyId );
  }

}
