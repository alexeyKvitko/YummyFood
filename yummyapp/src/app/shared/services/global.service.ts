import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/internal/Subject';
import {MenuEntityModel} from "../../model/menu-entity.model";
/* models */
/* import { TabMenuModel } from '../models/tabs-model';
import { NotificationModel } from '../models/notification-model'; */

@Injectable()
export class GlobalService {

    private customerBasket: MenuEntityModel[] = new Array<MenuEntityModel>();

    private dataSource = new Subject<DataSourceClass>();

    data$ = this.dataSource.asObservable();

    public dataBusChanged(ev, value) {
        this.dataSource.next({
            ev: ev,
            value: value
        })
    }

    public addEntityToBasket( menuEntity : MenuEntityModel){
      let exist = false;
      this.customerBasket.forEach( entity =>{
        if ( entity.id == menuEntity.id
                && entity.wspType == menuEntity.wspType ){
          entity.count = menuEntity.count;
          exist = true;
        }
      });
      if ( !exist ){
        this.customerBasket.push( menuEntity );
      }
    }

    public getBasketItemCount(): number{
      let enityCount = 0;
      this.customerBasket.forEach( menuEntity =>{
        enityCount += menuEntity.count;
      });
      return enityCount;
    }

}


export class DataSourceClass {
    ev: string;
    value: any
}
