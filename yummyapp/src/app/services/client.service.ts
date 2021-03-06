import {Injectable} from '@angular/core';
import {HttpClient } from '@angular/common/http';
import {ApiResponse} from "../model/api.response";
import {OurClientModel} from "../model/our-client";
import {ClientOrderModel} from "../model/client-order.model";
import {CompanyModel} from "../model/company.model";

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  private clientUrl = '/api/client';
  private orderUrl = '/api/client/order';

  constructor( private http: HttpClient ) {}


  public registerOurClient( ourClientModel : OurClientModel ) {
    return this.http.post<ApiResponse>(this.clientUrl + '/registerClient', ourClientModel );
  }

  public authorizationOurClient( ourClientModel : OurClientModel ) {
    return this.http.post<ApiResponse>(this.clientUrl + '/authorizationClient', ourClientModel );
  }


  public getClientInfo(uuid: string){
    return this.http.get<ApiResponse>(this.clientUrl + '/getClientInfo/' + uuid);
  }

  public getPayeerUrl(orderId: string, amount: string, date: string){
    return this.http.get<ApiResponse>(this.clientUrl + '/getPayeerUrl/' + orderId +"/"+amount+"/"+date);
  }


  public sendEmailToUs( message: string ) {
    return this.http.get<ApiResponse>(this.clientUrl+'/sendEmailToUs/'+ message);
  }

  public createOrder( clientOrder : ClientOrderModel ){
    return this.http.post<ApiResponse>(this.orderUrl + '/createClientOrder', clientOrder );
  }


}
