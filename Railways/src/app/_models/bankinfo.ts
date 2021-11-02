import { UserInfo } from "./userinfo";

export class BankInfo {
    constructor(public _id:string,public userinfo:UserInfo,public bankname:string,public cardNo:string,
        public cvv:string,public expirydate:string,public balance:Number) {}
}