export class UserInfo {
    constructor(public _id:string,public username:string,public password:string,public contact:string,public address:string,public usertype:string) {
        this.username=username;
        this.password=password;
        this.contact=contact;
        this.address=address;
        this.usertype=usertype;
    }
}