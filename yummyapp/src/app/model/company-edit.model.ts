import {CompanyInfoModel} from "./company-info.model";
import {MenuTypeModel} from "./menu-type.model";
import {MenuCategoryModel} from "./menu-category.model";
import {DictionaryModel} from "./dictionary.model";
import {MenuOrderModel} from "./menu-order.model";

export class CompanyEditModel extends CompanyInfoModel{

  menuOrders: MenuOrderModel[];
  deliveryMenuTypes: MenuTypeModel[];
  deliveryMenuCategories: MenuCategoryModel[];
  cities: DictionaryModel[];

  // constructor(){
  //   this.companyModel
  // }

}
