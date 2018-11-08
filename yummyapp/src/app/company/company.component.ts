import { Component, OnInit } from '@angular/core';

import { Router } from '@angular/router';

import { CompanyModel } from '../model/company.model';
import { CompanyService } from '../services/company.service';

@Component({
  selector: 'app-company',
  templateUrl: './company.component.html',
  styleUrls: ['./company.component.scss']
})
export class CompanyComponent implements OnInit {

  companies: CompanyModel[];

  constructor(private router: Router, private companyService: CompanyService) {

  }

  ngOnInit() {
    this.companyService.getCompanies()
      .subscribe( data => {
        this.companies = data;
        console.log(data);
      });
  };

}
