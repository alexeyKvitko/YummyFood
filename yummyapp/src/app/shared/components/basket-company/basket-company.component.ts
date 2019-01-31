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

  @Output()
  showCompany: EventEmitter<number> = new EventEmitter<number>();

  constructor() { }

  ngOnInit() {
    if ( this.basket != null ){
      this.logoSrc = "assets/images/logos/"+this.basket.company.logo;
    }
  }

  showCompanyDetails(){
    let companyId = +this.basket.company.id;
    this.showCompany.emit( companyId );
  }

}
