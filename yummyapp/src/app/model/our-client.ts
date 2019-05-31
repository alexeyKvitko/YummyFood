import {ClientLocationModel} from "./client-location.model";
import {FavoriteCompanyModel} from "./favorite-company.model";
import {BonusModel} from "./bonus.model";

export class OurClientModel {
  id: number = -1;
  nickName: string;
  email: string = null;
  phone: string = null;
  password: string = null;
  confirm: string = null;
  uuid: string = null;
  photo: string = null;
  additionalMessage: string = null;
  payType: string = "CASH";
  clientLocationModel: ClientLocationModel;
  favoriteCompanies:FavoriteCompanyModel[] = new Array<FavoriteCompanyModel>();
  bonusModels:BonusModel[] = new Array<BonusModel>();
}

