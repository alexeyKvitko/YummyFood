import {Component, OnInit, Input, Output, EventEmitter, ViewChild} from '@angular/core';
import {MenuEntityModel} from "../../../model/menu-entity.model";
import {GlobalService} from "../../services/global.service";
import {CompanyService} from "../../../services/company.service";
import swal from "sweetalert2";

@Component({
  selector: 'company-entity',
  templateUrl: './company-entity.component.html',
  styleUrls: ['./company-entity.component.scss']
})
export class CompanyEntityComponent implements OnInit {

  @Input()
  menuEntity: MenuEntityModel = new MenuEntityModel();

  @Output()
  selectCompany: EventEmitter<number> = new EventEmitter<number>();

  selWeight: string;
  selSize: string;
  selPrice: string;
  defaultImg: string = "assets/images/no-photo.png";
  wspType: string = "One";
  priceCount: number;
  hoverClass: string = "not-hover";
  animateColor: string ="";

  constructor(private  globalService : GlobalService, private companyService: CompanyService) {
  }

  ngOnInit() {
    this.selWeight = this.menuEntity.weightOne;
    this.selSize = this.menuEntity.sizeOne;
    this.selPrice = this.menuEntity.priceOne;
    this.priceCount = (this.menuEntity.priceOne != null ? 1 : 0) +
                      (this.menuEntity.priceTwo != null ? 1 : 0) +
                      (this.menuEntity.priceThree != null ? 1 : 0) +
                      (this.menuEntity.priceFour != null ? 1 : 0);
    if( this.menuEntity.imageUrl == null ){
      this.menuEntity.imageUrl = this.defaultImg;
    }
  }

  isHidden(obj){
    let hidden = true;
    if ( obj == null || this.selPrice == obj){
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

  emitCompanyDetail( companyId ){
    this.selectCompany.emit( companyId );
  }

  addToBasket(){
    if ( this.companyService.getNumberOfCompaniesInBasket( this.menuEntity.companyId ) == 3 ){
      swal("Вы можете сделать заказ максимум в 3-х заведениях ");
      return;
    }
    this.menuEntity.wspType = this.wspType;
    this.menuEntity.count ++;
    this.globalService.addEntityToBasket( this.menuEntity );
    this.globalService.dataBusChanged("add-to-basket","update");
    this.companyService.addCompanyToBasket( this.menuEntity.companyId, true );
  }

  footerMouseOver(){
    switch ( this.priceCount ) {
      case 1 :{
          this.hoverClass = "hover-one";
        break;
      }
      case 2 :{
        this.hoverClass = "hover-two";
        break;
      }
      case 3 :{
        this.hoverClass = "hover-three";
        break;
      }
      case 4 :{
        this.hoverClass = "hover-four";
        break;
      }
    }

  }

  footerMouseLeave(){
    this.hoverClass = "not-hover";
  }

  addToBasketOver(){
    this.animateColor="animate-color";
  }

  addToBasketLeave(){
    this.animateColor="";
  }

}
