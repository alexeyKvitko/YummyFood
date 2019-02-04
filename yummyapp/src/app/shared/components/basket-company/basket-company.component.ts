import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {BasketModel} from "../../../model/basket.model";

@Component({
  selector: 'basket-company',
  templateUrl: './basket-company.component.html',
  styleUrls: ['./basket-company.component.scss']
})
export class BasketCompanyComponent implements OnInit {

  logoSrc: string;

  @Input()
  basket: BasketModel;

  priceDiff: number;

  @Output()
  showCompany: EventEmitter<number> = new EventEmitter<number>();

  constructor() { }

  ngOnInit() {
    if ( this.basket != null ){
      this.logoSrc = "assets/images/logos/"+this.basket.company.logo;
    }
    this.priceDiff = this.basket.price-this.basket.company.delivery  ;
  }

  showCompanyDetails(){
    let companyId = +this.basket.company.id;
    this.showCompany.emit( companyId );
  }

}
