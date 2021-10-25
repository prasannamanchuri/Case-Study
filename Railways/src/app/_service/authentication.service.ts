import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable } from "rxjs";
import { AppConstants } from "../helpers/appConstants";
import { UserInfo } from "../_models/userinfo";

@Injectable({providedIn:'root'})
export class AuthenticationService {
    private currentUserSubject: BehaviorSubject<UserInfo>;
    public currentUser: Observable<UserInfo>;
    private baseUrl=AppConstants.API_ENDPOINT;
    public userinfo:UserInfo[];

    constructor(private http:HttpClient) {
        var userRoleStr=localStorage.getItem('currentUser');
        console.log("Userrolestr..."+userRoleStr);
        if(userRoleStr!=null && userRoleStr!=undefined) {
            this.currentUserSubject=new BehaviorSubject<UserInfo>(JSON.parse(userRoleStr));
        }
        else
            this.currentUserSubject=new BehaviorSubject<UserInfo>(<UserInfo>{});
        this.currentUser=this.currentUserSubject.asObservable();
    }

    public get currentUserValue():UserInfo {
        return this.currentUserSubject.value;
    }

    setCurrentUserValue(userInfo:UserInfo){
        this.currentUserSubject.next(userInfo);
    }

    ngOnInit():void{
        this.getAllUsers();
    }

    login(formData:FormData):Observable<any>{
        return this.http.post(`${this.baseUrl}/railwayAuthentication/auth/login`,formData);
    }

    logout(){
        localStorage.removeItem('currentUser');
        this.currentUserSubject.next(<UserInfo>{});
    }

    getAllUsers():Observable<any>{
        return this.http.get(`${this.baseUrl}/railwayAuthentication/auth/users`);
    }
}