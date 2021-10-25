import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { AppConstants } from "../helpers/appConstants";
import { UserInfo } from "../_models/userinfo";

@Injectable({
    providedIn:'root'
})
export class SignupService{
    
    private baseUrl=AppConstants.API_ENDPOINT;
    httpOptions={
        headers:new HttpHeaders({'Content-Type':'application/json'})
    };

    constructor(private http:HttpClient){}
    
    signup(userinfo:UserInfo):any{
        return this.http.post(`${this.baseUrl}/railwayAuthentication/auth/addUser`,userinfo,{responseType:"text"});
    }

}