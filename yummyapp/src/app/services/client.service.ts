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

  public createOrder( clientOrder : ClientOrderModel ){
    return this.http.post<ApiResponse>(this.clientUrl + '/createClientOrder', clientOrder );
  }

  public sendEmailToUs( message: string ) {
    return this.http.get<ApiResponse>(this.clientUrl+'/sendEmailToUs/'+ message);
  }


}
