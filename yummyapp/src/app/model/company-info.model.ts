import { CompanyModel } from '../model/company.model';
import {MenuTypeModel} from "./menu-type.model";
import {MenuEntityModel} from "./menu-entity.model";

export class CompanyInfoModel {
  companyModel: CompanyModel;
  menuTypes: MenuTypeModel[];
  menuEntities: MenuEntityModel[];

  constructor() {
    this.companyModel = new CompanyModel();
    this.menuTypes = new Array<MenuTypeModel>();
    this.menuEntities = new Array<MenuEntityModel>();
  }

}
