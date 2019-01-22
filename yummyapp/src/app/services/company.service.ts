import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';

import {CompanyModel} from '../model/company.model';
import {CompanyInfoModel} from '../model/company-info.model';
import {CompanyMenuModel} from "../model/company-menu.model";
import {ApiResponse} from "../model/api.response";
import {CompanyEditModel} from "../model/company-edit.model";
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
      this.bootstrapApp.companies.forEach( company =>{
        company.isPresentInBasket = false;
      });
      this._globalService.dataBusChanged('data-loaded', true);
    });
  }

  public loadBootstrapApp() {
    return this.http.get<CompanyModel[]>(this.companyUrl+'/short');
  }

  public getCompaniesModel() {
    return this.bootstrapApp.companies;
  }

  public addCompanyToBasket( companyId ){
    this.bootstrapApp.companies.forEach( company =>{
      if ( company.id == companyId ){
        company.isPresentInBasket = true;
      }
    });
  }

  public getCompanyById( companyId ): CompanyModel {
    let companyShort = null;
    this.bootstrapApp.companies.forEach(company => {
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

  public saveCompanyModelAndInfo( companyModel : CompanyModel ) {

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

  public deleteCompanyMenuEntities(companyId, typeId, categoryId) {
    return this.http.get<ApiResponse>(this.companyUrl + '/deleteMenuEntities/' + companyId
      + '/' + typeId + '/' + categoryId);
  }

  public testMenuPage(parseMenuModel) {
    return this.http.post<ApiResponse>(this.apiUrl+'/testParse', parseMenuModel);
  }

  public saveParseModel(parseMenuModel) {
    return this.http.post<ApiResponse>(this.apiUrl+'/saveParseModel', parseMenuModel);
  }

  public copyParseModel( copyParseData ) {
    return this.http.post<ApiResponse>(this.apiUrl+'/copyParseData', copyParseData);
  }

}
