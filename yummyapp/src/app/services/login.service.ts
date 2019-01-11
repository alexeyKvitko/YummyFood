import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {User} from "../model/user.model";
import {Observable} from "rxjs/index";
import {ApiResponse} from "../model/api.response";

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http:HttpClient) {}
  private loginUrl = '/api/login';
  private tokenUrl = '/token/generate-token';

  login(loginPayload) : Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.tokenUrl, loginPayload);
  }

  getUsers() : Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.loginUrl);
  }

  getUserById(id: number): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.loginUrl + id);
  }

  createUser(user: User): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.loginUrl, user);
  }

  updateUser(user: User): Observable<ApiResponse> {
    return this.http.put<ApiResponse>(this.loginUrl + user.id, user);
  }

  deleteUser(id: number): Observable<ApiResponse> {
    return this.http.delete<ApiResponse>(this.loginUrl + id);
  }


  getIP(): Observable<any> {
    // jQuery.getJSON("https://jsonip.com?callback=?", function(data) {
    //   alert("Your IP address is :- " + data.ip);
    // });
    return this.http.get('https://ipinfo.io/json',{ responseType: 'json' });
      // .subscribe(data => {
      //   console.log("DATA",data);
      // }, error => {
      //   console.log('Error: ',  error);
      // });
      // .catch((error:any) => Observable.throw(error.json().error || 'Server error')); //...errors if any
  }


}
