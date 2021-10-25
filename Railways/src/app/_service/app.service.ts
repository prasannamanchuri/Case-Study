import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { AppConstants } from "../helpers/appConstants";

@Injectable({
    providedIn:'root'
})
export class AppService{
    private baseUrl=AppConstants.API_ENDPOINT;
    httpOptions={
        headers:new HttpHeaders({'Content-Type':'application/json'})
    };

    constructor(private http:HttpClient){}
}