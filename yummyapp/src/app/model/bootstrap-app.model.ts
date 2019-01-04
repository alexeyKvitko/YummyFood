import {CompanyShortModel} from "./company-short.model";
import {DeliveryMenuModel} from "./delivery-menu.model";
import {DictionaryModel} from "./dictionary.model";

export class BootstrapAppModel {
  companyShorts: CompanyShortModel[];
  deliveryMenu: DeliveryMenuModel;
  cities: DictionaryModel[];
  deliveryCity: string;
  isDefault: boolean;

  constructor() {
    this.companyShorts = new Array<CompanyShortModel>();
    this.deliveryMenu = new DeliveryMenuModel();
    this.cities = new Array<DictionaryModel>();
  }

}
