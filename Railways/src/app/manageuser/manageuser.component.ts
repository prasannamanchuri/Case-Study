import { Component, OnInit, ViewChild } from '@angular/core';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { DataTableDirective } from 'angular-datatables';
import { ToastrService } from 'ngx-toastr';
import { Subject } from 'rxjs';
import { UserInfo } from '../_models/userinfo';
import { AdminService } from '../_service/admin.service';
import { AuthenticationService } from '../_service/authentication.service';

@Component({
  selector: 'app-manageuser',
  templateUrl: './manageuser.component.html',
  styleUrls: ['./manageuser.component.css']
})
export class ManageuserComponent implements OnInit {
  userlist:UserInfo[]=[];
  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  dtTrigger: Subject<any> = new Subject();
  closeResult: string;
  userModel:any={};
  editUserModel:any={};
  deleteUserModel:any={};
  isDtInitialized:boolean;
  loggedUser:UserInfo;
  constructor(private adminservice:AdminService,private toastr:ToastrService,private modalService:NgbModal,
    private authenticationService:AuthenticationService) {
      this.authenticationService.currentUser.subscribe(x=>this.loggedUser=x);
     }

  ngOnInit(): void {
    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 5,
      processing: true,
      lengthMenu:[[5,10,20,25,50,-1],[5,10,20,25,50,"All"]]
    };
    this.getAllUsers();
    this.userModel={
      username:'',
      contact:'',
      address:'',
      usertype:''
    };
  }
  
  getAllUsers(){
    this.authenticationService.getAllUsers()
      .subscribe(res=>{
        this.userlist=res;
        if (this.isDtInitialized) {
          this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
            dtInstance.destroy();
            this.userlist=res;
            this.dtTrigger.next();
          });
        } else {
          this.isDtInitialized = true;
          this.userlist=res;
          this.dtTrigger.next();
        }
        //this.toastr.success("Fetched train details Successfully");
      },error=>this.toastr.error(error,'getAllUsers'));
 }
 addUser(form){
  let userinfo:UserInfo;
  userinfo=form.value;
  console.log(userinfo);
  this.adminservice.addUser(userinfo)
   .subscribe(res=>{
      if(res==="success") {
        this.toastr.success("Added User details successfully");
        this.getAllUsers();
        this.modalService.dismissAll();
      }
  },error=>this.toastr.error(error,'Adduserinfo'));
}
editUser(form){
  let userinfo:UserInfo;
  userinfo=form.value;
  console.log(userinfo);
  this.adminservice.edituserinfo(userinfo)
   .subscribe(res=>{
    if(res==="success") {
       this.toastr.success("Edit Successfull");
      this.getAllUsers();
      this.modalService.dismissAll();
     }
     else
      this.toastr.error("Failed Editing User details");
  },error=>this.toastr.error(error,'editUser'));
}
open(content:any) {
  this.modalService.open(content,{ariaLabelledBy:'modal-title'}).result.then((result)=>{
    this.closeResult=`Closed with ${result}`;
  },(reason)=>{
    this.closeResult=`Dismissed ${this.getDismissReason(reason)}`;
  })
}

getDismissReason(reason:any):string{
  if(reason===ModalDismissReasons.ESC) {
    return 'by pressing ESC';
  } else if(reason===ModalDismissReasons.BACKDROP_CLICK) {
    return 'by clicking on a backdrop';
  } else {
    return `with: ${reason}`;
  }
}

setForm(username,type) {
  const formdata=new FormData();
  formdata.append("username",username);
   this.adminservice.getUserInfoByUsername(formdata)
   .subscribe(res=>{
    if(res!=null) {
      let userinfo:UserInfo;
      userinfo=res;
      var object={
        _id:userinfo._id,
        username:userinfo.username,
        password:userinfo.password,
        contact:userinfo.contact,
        address:userinfo.address,
        usertype:userinfo.usertype
      };
      if(type==='edit')
        this.editUserModel=object;
      else
        this.deleteUserModel=object;
    }
    else
      this.toastr.error("No Results found for usernname");
  },error=>this.toastr.error(error,'getUserInfoByUsername'));
}

deleteUser(username) {
  const formdata=new FormData();
  formdata.append("username",username);
   this.adminservice.deleteUser(formdata)
   .subscribe(res=>{
      if(res==="success") {
        this.toastr.success("Deleted User details successfully");
        this.getAllUsers();
        this.modalService.dismissAll();
      }
  },error=>this.toastr.error(error,'deleteUser'));
  }
}
