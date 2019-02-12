import {Injectable} from '@angular/core';
import {HttpClient } from '@angular/common/http';
import {ApiResponse} from "../model/api.response";
import {OurClientModel} from "../model/our-client";

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


}
