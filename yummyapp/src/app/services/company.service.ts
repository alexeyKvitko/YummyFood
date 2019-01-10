import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {CompanyModel} from '../model/company.model';
import {CompanyInfoModel} from '../model/company-info.model';
import {CompanyMenuModel} from "../model/company-menu.model";
import {ApiResponse} from "../model/api.response";
import {CompanyEditModel} from "../model/company-edit.model";
import {CompanyShortModel} from "../model/company-short.model";
import {BootstrapAppModel} from "../model/bootstrap-app.model";
import {GlobalService} from "../shared/services/global.service";

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  private companyUrl = '/api/company';
  private apiUrl = '/api';
  private bootstrapApp: BootstrapAppModel = new BootstrapAppModel();

  constructor(private http: HttpClient,private _globalService: GlobalService) {
  }

  public initBootstrapApp( ip: string) {
    if ( ip == null || ip.length == 0 ){
      ip = "undefined";
    }
    this.http.get<BootstrapAppModel>(this.companyUrl+'/bootstrap/'+ip).subscribe( data => {
      this.bootstrapApp = data;
      this.bootstrapApp.companyShorts.forEach( company =>{
        company.isPresentInBasket = false;
      });
      this._globalService.dataBusChanged('data-loaded', true);
    });
  }

  public loadBootstrapApp() {
    return this.http.get<CompanyShortModel[]>(this.companyUrl+'/short');
  }

  public getCompaniesShort() {
    return this.bootstrapApp.companyShorts;
  }

  public addCompanyShortToBasket( companyId ){
    this.bootstrapApp.companyShorts.forEach( company =>{
      if ( company.companyId == companyId ){
        company.isPresentInBasket = true;
      }
    });
  }

  public getCompanyShortById( companyId ): CompanyShortModel {
    let companyShort = null;
    this.bootstrapApp.companyShorts.forEach(company => {
       if ( companyId == company.id ){
         companyShort = company;
       }
    });
    return companyShort;
  }

  public getDeliveryMenus() {
    return this.bootstrapApp.deliveryMenu;
  }

  public getAllCities() {
    return this.bootstrapApp.cities;
  }

  public getDeliveryCity(){
    return this.bootstrapApp.deliveryCity;
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
