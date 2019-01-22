import {ParseFieldModel} from "./parse-field";

export class ParseResultModel {
  message: string;
  stackTrace: string;
  section: string;
  status:  string;
  step: number;
  htmlResult: ParseFieldModel;
  htmlClean: ParseFieldModel;
  productName: ParseFieldModel;
  productDesc: ParseFieldModel;
  productImg: ParseFieldModel;
  wspOne: ParseFieldModel;
  wspTwo: ParseFieldModel;
  wspThree: ParseFieldModel;
  wspFour: ParseFieldModel;
}
