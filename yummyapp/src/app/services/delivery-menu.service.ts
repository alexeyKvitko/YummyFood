import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {DeliveryMenuModel} from "../model/delivery-menu.model";
import {MenuTypeModel} from "../model/menu-type.model";
import {MenuCategoryModel} from "../model/menu-category.model";
import {CompanyInfoModel} from "../model/company-info.model";
import {ApiResponse} from "../model/api.response";

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class DeliveryMenuService {

  constructor(private http: HttpClient) {
  }

  private deliveryMenuUrl = '/api/menu';


  public getDeliveryMenus() {
    return this.http.get<DeliveryMenuModel>(this.deliveryMenuUrl+'/all');
  }

  public saveMenuType( menuType: MenuTypeModel ) {
    return this.http.post<ApiResponse>(this.deliveryMenuUrl+'/saveType', menuType );
  }

  public saveMenuCategory( menuCategory: MenuCategoryModel ) {
    return this.http.post<ApiResponse>(this.deliveryMenuUrl+'/saveCategory', menuCategory );
  }

  public deleteMenuType(id) {
    return this.http.get<ApiResponse>(this.deliveryMenuUrl + '/deleteType/' + id);
  }

  public deleteMenuCategory(id) {
    return this.http.get<ApiResponse>(this.deliveryMenuUrl + '/deleteCategory/' + id);
  }

}