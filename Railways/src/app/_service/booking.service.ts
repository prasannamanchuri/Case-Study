import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { AppConstants } from "../helpers/appConstants";
import { BookingInfo } from "../_models/bookinginfo";
import { UserInfo } from "../_models/userinfo";

@Injectable({
    providedIn:'root'
})
export class BookingService{
    private baseUrl=AppConstants.API_ENDPOINT;
    httpOptions={
        headers:new HttpHeaders({'Content-Type':'application/json'})
    };

    constructor(private http:HttpClient){}
    
    getAllBookings(userinfo:UserInfo):Observable<any>{
        return this.http.post(`${this.baseUrl}/ralwayBooking/booking/getAllBookings`,userinfo);
    }

    searchTrains(formdata:FormData):Observable<any>{
        return this.http.post(`${this.baseUrl}/trainService/train/searchTrains`,formdata);
    }

    bookSeats(bookinginfo:BookingInfo):any{
        return this.http.post(`${this.baseUrl}/ralwayBooking/booking/bookSeats`,bookinginfo,{responseType:"text"});
    }

    editbooking(bookingInfo: BookingInfo):any{
        return this.http.post(`${this.baseUrl}/ralwayBooking/booking/editBooking`,bookingInfo,{responseType:"text"});
    }

    cancelbooking(bookingInfo: BookingInfo):any{
        return this.http.post(`${this.baseUrl}/ralwayBooking/booking/cancelBooking`,bookingInfo,{responseType:"text"});
    }

    fetchTicketDetails(ticketNo:string):Observable<any>{
        const formdata=new FormData();
        formdata.append("ticketNo",ticketNo);
        return this.http.post(`${this.baseUrl}/ralwayBooking/booking/fetchTicketDetails`,formdata);
    }
    
}