import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';

import {CompanyModel} from '../model/company.model';
import {CompanyInfoModel} from '../model/company-info.model';
import {CompanyMenuModel} from "../model/company-menu.model";
import {ApiResponse} from "../model/api.response";
import {CompanyEditModel} from "../model/company-edit.model";
import {BootstrapAppModel} from "../model/bootstrap-app.model";
import {GlobalService} from "../shared/services/global.service";
import {FastMenuModel} from "../model/fast-menu.model";
import {OurClientModel} from "../model/our-client";

@Injectable({
  providedIn: 'root'
})
export class CompanyService {

  private companyUrl = '/api/company';
  private apiUrl = '/api';
  private bootstrapApp: BootstrapAppModel = new BootstrapAppModel();
  private isBootstrapLoading: boolean = false;

  constructor(private http: HttpClient,private _globalService: GlobalService) {
  }

  public initBootstrapApp( latitude, longitude ) {
    if ( this.isBootstrapLoading ){
      return;
    }
    this.isBootstrapLoading = true;
      this.http.get<BootstrapAppModel>(this.companyUrl+'/bootstrap/'+latitude+"/"+longitude).subscribe( data => {
      this.bootstrapApp = data;
      this.bootstrapApp.companies.forEach( company =>{
        company.isPresentInBasket = false;
      });
      this._globalService.dataBusChanged('data-loaded', true);
      this._globalService.dataBusChanged('selected-link', null);
      this.isBootstrapLoading = false;
    });
  }

  public initBootstrapAppWithLink( latitude, longitude, link ) {
    if ( this.isBootstrapLoading ){
      return;
    }
    this.isBootstrapLoading = true;
    this.http.get<BootstrapAppModel>(this.companyUrl+'/bootstrap/'+latitude+"/"+longitude).subscribe( data => {
      this.bootstrapApp = data;
      this.bootstrapApp.companies.forEach( company =>{
        company.isPresentInBasket = false;
      });
      this._globalService.dataBusChanged('data-loaded', true);
      this._globalService.dataBusChanged('selected-link', link);
      this.isBootstrapLoading = false;
    });
  }

  public loadBootstrapApp() {
    return this.http.get<CompanyModel[]>(this.companyUrl+'/short');
  }



  public getCompaniesModel() {
    return this.bootstrapApp.companies;
  }

  public getFastMenuModel(): FastMenuModel{
    return this.bootstrapApp.fastMenu;
  }

  public addCompanyToBasket( companyId, value ){
    this.bootstrapApp.companies.forEach( company =>{
      if ( company.id == companyId ){
        company.isPresentInBasket = value;
      }
    });
  }

  public removeCompaniesFromBasket(){
    this.bootstrapApp.companies.forEach( company =>{
        company.isPresentInBasket = false;
    });
  }


  public getNumberOfCompaniesInBasket( companyId ){
    let count = 0;
    this.bootstrapApp.companies.forEach( company =>{
      if ( company.isPresentInBasket && company.id != companyId ){
        count++;
      }
    });
    return count;
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

  public getCompanyDishes( deliveryCity, categoryId ) {
    return this.http.get<ApiResponse>(this.companyUrl + '/dishes/' +deliveryCity+"/"+ categoryId);
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

  public testPayment( ) {
    return this.http.post('https://payeer.com/merchant/?m_shop=792221744&m_orderid=12345&m_amount=10.00&m_curr=RUB&m_desc=VGVzdCBwYXltZW50IOKEljEyMzQ1&m_sign=ADBB6375CC944F458CB7CCD2E2807187C67FAFA9818E02BA6C3A69B818A78CD4&lang=ru',null);
  }

}
