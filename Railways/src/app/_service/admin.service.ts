import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { AppConstants } from "../helpers/appConstants";
import { TrainInfo } from "../_models/traininfo";
import { UserInfo } from "../_models/userinfo";

@Injectable({
    providedIn:'root'
})
export class AdminService{
    
    private baseUrl=AppConstants.API_ENDPOINT;
    httpOptions={
        headers:new HttpHeaders({'Content-Type':'application/json'})
    };

    constructor(private http:HttpClient){}
    
    getalltrains():Observable<any>{
        return this.http.get(`${this.baseUrl}/trainService/train/trains`);
    }

    addtrains(traininfo:TrainInfo):any{
        return this.http.post(`${this.baseUrl}/trainService/train/addTrain`,traininfo,{responseType:"text"});
    }

    getTrainInfoByTrainnname(formdata: FormData):Observable<any> {
        return this.http.post(`${this.baseUrl}/trainService/train/traininfobyname`,formdata);
    }

    editTrainInfo(traininfo:TrainInfo):any{
        let headers = new HttpHeaders().set('Content-type', 'application/json');
        return this.http.post(`${this.baseUrl}/trainService/train/updatetraininfo`,traininfo,{responseType:"text"});
    }

    deleteTrain(formdata:FormData):any{
        return this.http.post(`${this.baseUrl}/trainService/train/deleteTrainByName`,formdata,{responseType:"text"});
    }
}