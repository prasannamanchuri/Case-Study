import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { AppConstants } from "../helpers/appConstants";
import { BankInfo } from "../_models/bankinfo";
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
        return this.http.post(`${this.baseUrl}/trainService/train/updatetraininfo`,traininfo,{responseType:"text"});
    }

    deleteTrain(formdata:FormData):any{
        return this.http.post(`${this.baseUrl}/trainService/train/deleteTrainByName`,formdata,{responseType:"text"});
    }

    deleteUser(formdata: FormData) {
    return this.http.post(`${this.baseUrl}/railwayAuthentication/auth/deleteUser`,formdata,{responseType:"text"});
    }
    getUserInfoByUsername(formdata: FormData):Observable<any> {
    return this.http.post(`${this.baseUrl}/railwayAuthentication/auth/getUserinfoByUsername`,formdata);
    }
    edituserinfo(userinfo: UserInfo) {
    return this.http.post(`${this.baseUrl}/railwayAuthentication/auth/editUser`,userinfo,{responseType:"text"});
    }
    addUser(userinfo: UserInfo) {
    return this.http.post(`${this.baseUrl}/railwayAuthentication/auth/addUser`,userinfo,{responseType:"text"});
    }
    getAllBanks(userinfo:UserInfo):Observable<any> {
        return this.http.post(`${this.baseUrl}/railwayAuthentication/auth/getAllBanksByUserInfo`,userinfo);
    }

    deleteBank(bankinfo:BankInfo) {
        return this.http.post(`${this.baseUrl}/railwayAuthentication/auth/deleteBanksForUser`,bankinfo,{responseType:"text"});
    }
    getBankInfoById(id: string):Observable<any> {
        const formdata=new FormData();
        formdata.append("id",id);
        return this.http.post(`${this.baseUrl}/railwayAuthentication/auth/getBankInfoById`,formdata);
    }
    editBank(bankinfo:BankInfo) {
        return this.http.post(`${this.baseUrl}/railwayAuthentication/auth/editBanksForUser`,bankinfo,{responseType:"text"});
    }
    addBank(bankinfo:BankInfo) {
        return this.http.post(`${this.baseUrl}/railwayAuthentication/auth/addBanksForUser`,bankinfo,{responseType:"text"});
    }
}