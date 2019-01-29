import {DictionaryModel} from "./dictionary.model";
import {MenuCategoryModel} from "./menu-category.model";

export class MenuTypeModel extends DictionaryModel{

  menuOpen : boolean;
  menuCategories: MenuCategoryModel[];

}
