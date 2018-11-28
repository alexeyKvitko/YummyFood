import {CompanyInfoModel} from "./company-info.model";
import {MenuTypeModel} from "./menu-type.model";
import {MenuCategoryModel} from "./menu-category.model";
import {DictionaryModel} from "./dictionary.model";

export class CompanyEditModel extends CompanyInfoModel{

  deliveryMenuTypes: MenuTypeModel[];
  deliveryMenuCategories: MenuCategoryModel[];
  cities: DictionaryModel[];

}
