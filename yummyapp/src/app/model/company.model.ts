import {DictionaryModel} from "./dictionary.model";

export class CompanyModel {
  id: string;
  companyName: string;
  displayName: string;
  city: any;
  thumbUrl: string;
  url: string;
  email: string;
  phoneOne: string;
  phoneTwo: string;
  phoneThree: string;
  logo:string;
  delivery: number;
  deliveryTimeMin: number;
  commentCount: string;
  feedbackRate: number;
  deliveryCondition: string;
  payTypeCash: number;
  payTypeCard: number;
  payTypeWallet: number;
  weekdayStart: string;
  weekdayEnd: string;
  dayoffStart: string;
  dayoffEnd: string;
  foodPoint: string;
  action: string;
  weekdayWork: string;
  dayoffWork: string;
  isPresentInBasket: boolean;
  menuTypeIds: string;
  menuCategoiesIds: string;

  constructor(){
    this.city = new DictionaryModel();
  }
}
