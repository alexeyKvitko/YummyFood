import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { CompanyModel } from '../model/company.model';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  constructor(private http:HttpClient) {}

  private companyUrl = '/api/getCompanies';

  public getCompanies() {
    return this.http.get<CompanyModel[]>(this.companyUrl);
  }

}
