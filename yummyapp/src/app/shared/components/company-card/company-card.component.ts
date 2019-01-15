import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import {CompanyModel} from "../../../model/company.model";
@Component({
  selector: 'company-card',
  templateUrl: './company-card.component.html',
  styleUrls: ['./company-card.component.scss']
})
export class CompanyCardComponent implements OnInit {
  @Input()
  company: CompanyModel;

  @Input()
  isDetailInfo: boolean = false ;

  @Output()
  selectCompany: EventEmitter<number> = new EventEmitter<number>();

  logoSrc: string;
  starImgSrc: string ="assets/images/icons/stars.png";


  constructor() {}

  ngOnInit() {
    if ( this.company != null ){
      this.logoSrc = "assets/images/logos/"+this.company.logo;
    }
  }

  showMenu( company ){
    this.selectCompany.emit( company.id );
  }

}
