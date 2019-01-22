import {ParseResultModel} from "./parse-result";

export class ParseMenuModel {
  id: string;
  companyId: string;
  typeId: string;
  categoryId: string;
  htmlResponse: string;
  parseUrl: string;
  prefixUrl: string;
  tagTrash: string;
  tagEndSection: string;
  tagName: string;
  tagDescription: string;
  tagImageUrl: string;
  tagWeightOne: string;
  tagSizeOne: string;
  tagPriceOne: string;
  tagWeightTwo: string;
  tagSizeTwo: string;
  tagPriceTwo: string;
  tagWeightThree: string;
  tagSizeThree: string;
  tagPriceThree: string;
  tagWeightFour: string;
  tagSizeFour: string;
  tagPriceFour: string;
  broken: boolean;
  parseResult: ParseResultModel;
  processed: string;

}
