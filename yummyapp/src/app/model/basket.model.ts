import {CompanyModel} from "./company.model";
import {MenuEntityModel} from "./menu-entity.model";

export class BasketModel {
  company: CompanyModel;
  basket: MenuEntityModel[] = new Array<MenuEntityModel>();
  price: number = 0;
}
