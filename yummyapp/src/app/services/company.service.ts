import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {CompanyModel} from '../model/company.model';
import {CompanyInfoModel} from '../model/company-info.model';
import {CompanyMenuModel} from "../model/company-menu.model";
import {ApiResponse} from "../model/api.response";
import {CompanyEditModel} from "../model/company-edit.model";
import {CompanyShortModel} from "../model/company-short.model";
import {DeliveryMenuModel} from "../model/delivery-menu.model";

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  private companyUrl = '/api/company';
  private apiUrl = '/api';
  private companiesShort: CompanyShortModel[] = new Array<CompanyShortModel>();

  constructor(private http: HttpClient) {
  }

  public initCompaniesShort() {
    this.http.get<CompanyShortModel[]>(this.companyUrl+'/short').subscribe( data => {
      this.companiesShort = data;
    });
  }

  public loadCompaniesShort() {
    return this.http.get<CompanyShortModel[]>(this.companyUrl+'/short');
  }

  public getCompaniesShort() {
    return this.companiesShort;
  }

  public getCompanies() {
    return this.http.get<CompanyModel[]>(this.companyUrl);
  }

  public getCompanyInfo(id) {
    return this.http.get<CompanyInfoModel>(this.companyUrl + '/' + id);
  }

  public getCompanyEdit(id) {
    return this.http.get<CompanyEditModel>(this.companyUrl + '/edit/' + id);
  }

  public saveCompanyModel(companyModel) {
    return this.http.post<ApiResponse>(this.apiUrl + '/saveCompany',companyModel);
  }

  public getCompanyMenu(companyId, typeId, categoryId) {
    return this.http.get<CompanyMenuModel>(this.companyUrl + '/' + companyId
      + '/' + typeId + '/' + categoryId);
  }

  public addCompanyMenu(companyId, typeId, categoryId) {
    return this.http.get<ApiResponse>(this.companyUrl + '/addMenu/' + companyId
      + '/' + typeId + '/' + categoryId);
  }

  public deleteCompanyMenu(companyId, typeId, categoryId) {
    return this.http.get<ApiResponse>(this.companyUrl + '/deleteMenu/' + companyId
      + '/' + typeId + '/' + categoryId);
  }

  public testMenuPage(parseMenuModel) {
    return this.http.post<ApiResponse>(this.apiUrl+'/testParse', parseMenuModel);
  }

  public saveParseModel(parseMenuModel) {
    return this.http.post<ApiResponse>(this.apiUrl+'/saveParseModel', parseMenuModel);
  }

}
