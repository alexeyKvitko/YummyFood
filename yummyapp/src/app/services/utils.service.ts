import { Injectable } from '@angular/core';
import {CompanyModel} from "../model/company.model";

@Injectable({
  providedIn: 'root'
})
export class UtilsService {

  constructor() { }

  public isArrayСrossed( sourceStr: string, target: Array<number>): boolean{
    let present = false;
    let source =  sourceStr.split(",");
    source.forEach( sourceEl => {
      target.forEach( targetEl => {
        if( !present && (+sourceEl) == targetEl ){
          present = true;
        }
      });
    });
    return present;
  }

  public isPayTypeEnabled(source: Array<number>, company: CompanyModel ): boolean{
    let enabled = false;
    source.forEach( sourceEl => {
      if ( (sourceEl == 1 && company.payTypeCash == 1) ||
              (sourceEl == 2 && company.payTypeCard == 1) ||
                  (sourceEl == 3 && company.payTypeWallet == 1)){
        enabled = true;
      }
    });
    return enabled;
  }

}
