import { TrainInfo } from "./traininfo";
import { UserInfo } from "./userinfo";

export class BookingInfo {
    constructor(public _id:string,public ticketNo:string,public userinfo:UserInfo, public trainInfo:TrainInfo, public noofadult:Number, 
        public noofchildren:Number,public typeofclass:string,public status:string) {}
}