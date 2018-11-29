import { CompanyModel } from '../model/company.model';
import {MenuTypeModel} from "./menu-type.model";

export class CompanyInfoModel {
  companyModel: CompanyModel;
  menuTypes: MenuTypeModel[];

  constructor() {
    this.companyModel = new CompanyModel();
    this.menuTypes = new Array<MenuTypeModel>();
  }

}
