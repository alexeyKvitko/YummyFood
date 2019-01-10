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
          entity.count ++;
          exist = true;
        }
      });
      if ( !exist ){
        let newInstance = Object.assign({}, menuEntity);
        newInstance.count = 1;
        this.customerBasket.push( newInstance );
      }
    }

    public getBasketPrice(): number{
      let entityPrice = 0;
      this.customerBasket.forEach( menuEntity =>{
        switch( menuEntity.wspType ){
          case 'One' :
            entityPrice = entityPrice + (+menuEntity.priceOne)*menuEntity.count;
          break;
          case 'Two' :
            entityPrice = entityPrice + (+menuEntity.priceTwo)*menuEntity.count;
            break;
          case 'Three' :
            entityPrice = entityPrice + (+menuEntity.priceThree)*menuEntity.count;
            break;
          case 'Four' :
            entityPrice = entityPrice + (+menuEntity.priceFour)*menuEntity.count;
            break;
          default:
          break;
            
        }
      });
      return entityPrice;
    }

    public getBasket(){
      return this.customerBasket;
    }


}


export class DataSourceClass {
    ev: string;
    value: any
}
