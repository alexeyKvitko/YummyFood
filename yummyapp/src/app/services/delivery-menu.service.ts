import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {DeliveryMenuModel} from "../model/delivery-menu.model";
import {MenuTypeModel} from "../model/menu-type.model";
import {MenuCategoryModel} from "../model/menu-category.model";
import {ApiResponse} from "../model/api.response";
import {MenuOrderModel} from "../model/menu-order.model";

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class DeliveryMenuService {

  private deliveryMenu: DeliveryMenuModel = new DeliveryMenuModel();

  constructor(private http: HttpClient) {
  }

  private deliveryMenuUrl = '/api/menu';


  public initDeliveryMenus() {
    this.http.get<DeliveryMenuModel>(this.deliveryMenuUrl+'/all').subscribe( data => {
      this.deliveryMenu = data;
    });
  }

  public loadDeliveryMenus() {
    return this.http.get<DeliveryMenuModel>(this.deliveryMenuUrl+'/all');
  }

  public getDeliveryMenus() {
    return this.deliveryMenu;
  }

  public saveMenuType( menuType: MenuTypeModel ) {
    return this.http.post<ApiResponse>(this.deliveryMenuUrl+'/saveType', menuType );
  }

  public saveMenuCategory( menuCategory: MenuCategoryModel ) {
    return this.http.post<ApiResponse>(this.deliveryMenuUrl+'/saveCategory', menuCategory );
  }

  public saveMenuOrder( menuOrders: MenuOrderModel[] ) {
    return this.http.post<ApiResponse>(this.deliveryMenuUrl+'/saveOrder', menuOrders );
  }

  public deleteMenuType(id) {
    return this.http.get<ApiResponse>(this.deliveryMenuUrl + '/deleteType/' + id);
  }

  public deleteMenuCategory(id) {
    return this.http.get<ApiResponse>(this.deliveryMenuUrl + '/deleteCategory/' + id);
  }

}
