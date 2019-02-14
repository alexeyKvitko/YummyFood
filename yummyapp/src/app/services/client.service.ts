import {Injectable} from '@angular/core';
import {HttpClient } from '@angular/common/http';
import {ApiResponse} from "../model/api.response";
import {OurClientModel} from "../model/our-client";
import {CompanyInfoModel} from "../model/company-info.model";

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


}
