import {DeliveryMenuModel} from "./delivery-menu.model";
import {DictionaryModel} from "./dictionary.model";
import {CompanyModel} from "./company.model";
import {FastMenuModel} from "./fast-menu.model";

export class BootstrapAppModel {
  companies: CompanyModel[];
  deliveryMenu: DeliveryMenuModel;
  cities: DictionaryModel[];
  fastMenu: FastMenuModel;
  deliveryCity: string;
  isDefault: boolean;

  constructor() {
    this.companies = new Array<CompanyModel>();
    this.deliveryMenu = new DeliveryMenuModel();
    this.cities = new Array<DictionaryModel>();
  }

}
