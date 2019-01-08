import {Component, OnInit, Input, Output, EventEmitter} from '@angular/core';
import {CompanyShortModel} from "../../../model/company-short.model";

@Component({
  selector: 'company-card',
  templateUrl: './company-card.component.html',
  styleUrls: ['./company-card.component.scss']
})
export class CompanyCardComponent implements OnInit {
  @Input()
  company: CompanyShortModel;

  @Input()
  isDetailInfo: boolean = false ;

  @Output()
  selectCompany: EventEmitter<number> = new EventEmitter<number>();

  logoSrc: string;
  starImgSrc: string ="assets/images/icons/stars.png";


  constructor() {}

  ngOnInit() {
    this.logoSrc = "assets/images/logos/"+this.company.companyLogo;
  }

  showMenu( company ){
    this.selectCompany.emit( company.id );
  }

}
