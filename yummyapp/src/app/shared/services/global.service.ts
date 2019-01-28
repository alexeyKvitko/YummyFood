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
    selEntityImg: string;

    data$ = this.dataSource.asObservable();

    public dataBusChanged(ev, value) {
        this.dataSource.next({
            ev: ev,
            value: value
        })
    }

    public addEntityToBasket( menuEntity : MenuEntityModel){
      let exist = false;
      this.selEntityImg = menuEntity.imageUrl;
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
        entityPrice += this.calculatePrice( menuEntity );
      });
      return entityPrice;
    }

    public calculatePrice( menuEntity: MenuEntityModel ){
      let calc = 0;
      switch( menuEntity.wspType ){
        case 'One' :
          calc = (+menuEntity.priceOne)*menuEntity.count;
          break;
        case 'Two' :
          calc = (+menuEntity.priceTwo)*menuEntity.count;
          break;
        case 'Three' :
          calc = (+menuEntity.priceThree)*menuEntity.count;
          break;
        case 'Four' :
          calc = (+menuEntity.priceFour)*menuEntity.count;
          break;
        default:
          break;
      }
      return calc;
    }

    public getBasket(){
      return this.customerBasket;
    }


  public getEntityImg(): string {
    return this.selEntityImg;
  }
}


export class DataSourceClass {
    ev: string;
    value: any
}
