import {DictionaryModel} from "./dictionary.model";

export class CompanyModel {
  id: string;
  companyName: string;
  displayName: string;
  city: any;
  url: string;
  email: string;
  phoneOne: string;
  phoneTwo: string;
  phoneThree: string;
  logo:string;

  constructor(){
    this.city = new DictionaryModel();
  }
}
