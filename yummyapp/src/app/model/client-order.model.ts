import {BasketModel} from "./basket.model";

export class ClientOrderModel{
  id: number;
  nickName: string;
  email: string;
  phone: string;
  city: string;
  street: string;
  building: string;
  flat: string;
  floor: string;
  entry: string;
  intercom: string;
  needChange: number;
  comment: string;
  payType: string;
  orders: BasketModel[] = new Array<BasketModel>();
}

