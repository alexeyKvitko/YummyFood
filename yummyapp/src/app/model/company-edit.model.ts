import {CompanyInfoModel} from "./company-info.model";
import {MenuTypeModel} from "./menu-type.model";
import {MenuCategoryModel} from "./menu-category.model";
import {DictionaryModel} from "./dictionary.model";
import {CompanyShortModel} from "./company-short.model";

export class CompanyEditModel extends CompanyInfoModel{

  deliveryMenuTypes: MenuTypeModel[];
  deliveryMenuCategories: MenuCategoryModel[];
  companyShortModel: CompanyShortModel = new CompanyShortModel();
  cities: DictionaryModel[];

  // constructor(){
  //   this.companyModel
  // }

}
