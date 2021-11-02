import { Component, OnInit, ViewChild } from '@angular/core';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { DataTableDirective } from 'angular-datatables';
import { ToastrService } from 'ngx-toastr';
import { Subject } from 'rxjs';
import { BankInfo } from '../_models/bankinfo';
import { UserInfo } from '../_models/userinfo';
import { AdminService } from '../_service/admin.service';
import { AuthenticationService } from '../_service/authentication.service';

@Component({
  selector: 'app-userprofile',
  templateUrl: './userprofile.component.html',
  styleUrls: ['./userprofile.component.css']
})
export class UserprofileComponent implements OnInit {

  banklist:BankInfo[]=[];
  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  dtTrigger: Subject<any> = new Subject();
  closeResult: string;
  bankModel:any={};
  editbankModel:any={};
  deletebankModel:any={};
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
    this.getAllBanks();
    this.bankModel={
      username:'',
      cvv:'',
      expirydate:'',
      balance:''
    };
  }
  
  getAllBanks(){
    this.adminservice.getAllBanks(this.loggedUser)
      .subscribe(res=>{
        this.banklist=res;
        if (this.isDtInitialized) {
          this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
            dtInstance.destroy();
            this.banklist=res;
            this.dtTrigger.next();
          });
        } else {
          this.isDtInitialized = true;
          this.banklist=res;
          this.dtTrigger.next();
        }
      },error=>this.toastr.error(error,'getAllBanks'));
 }
 addBank(form){
  let bankinfo:BankInfo;
  bankinfo=form.value;
  bankinfo.userinfo=this.loggedUser;
  console.log(bankinfo);
  this.adminservice.addBank(bankinfo)
   .subscribe(res=>{
      if(res==="success") {
        this.toastr.success("Added Bank details successfully");
        this.getAllBanks();
        this.modalService.dismissAll();
      }
  },error=>this.toastr.error(error,'Addbankinfo'));
}
editBank(form){
  let bankinfo:BankInfo;
  bankinfo=form.value;
  bankinfo.userinfo=this.loggedUser;
  console.log(bankinfo);
  this.adminservice.editBank(bankinfo)
   .subscribe(res=>{
    if(res==="success") {
       this.toastr.success("Edit Successfull");
      this.getAllBanks();
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

setForm(id) {
  this.adminservice.getBankInfoById(id)
   .subscribe(res=>{
    if(res!=null) {
      let bankinfo:BankInfo;
      bankinfo=res;
      var object={
        _id:bankinfo._id,
        bankname:bankinfo.bankname,
        cardNo:bankinfo.cardNo,
        cvv:bankinfo.cvv,
        expirydate:bankinfo.expirydate,
        balance:bankinfo.balance
      };
      this.editbankModel=object;
    }
    else
      this.toastr.error("No Results found for usernname");
  },error=>this.toastr.error(error,'getbankinfoByUsername'));
}

deleteBank(bankinfo) {
  this.adminservice.deleteBank(bankinfo)
   .subscribe(res=>{
      if(res==="success") {
        this.toastr.success("Deleted Bank details successfully");
        this.getAllBanks();
        this.modalService.dismissAll();
      }
  },error=>this.toastr.error(error,'deleteBank'));
  }

}
