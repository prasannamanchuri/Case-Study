import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AuthenticationService } from '../_service/authentication.service';
import { UserInfo } from '../_models/userinfo';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DataTableDirective } from 'angular-datatables';
import { Observable, Subject } from 'rxjs';
import { BookingInfo } from '../_models/bookinginfo';
import { BookingService } from '../_service/booking.service';
import { TrainInfo } from '../_models/traininfo';
import { FormControl } from '@angular/forms';
import { AdminService } from '../_service/admin.service';
import {map, startWith} from 'rxjs/operators';
import { NGB_TIMEPICKER_I18N_FACTORY } from '@ng-bootstrap/ng-bootstrap/timepicker/timepicker-i18n';
import { BankInfo } from '../_models/bankinfo';

@Component({
  selector: 'app-user-view',
  templateUrl: './user-view.component.html',
  styleUrls: ['./user-view.component.css']
})
export class UserViewComponent implements OnInit {

  loggedUser:UserInfo;
  loggineduser:string;
  bookingModel:any={};
  closeResult: string;
  bookingList:BookingInfo[]=[];
  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  dtTrigger: Subject<any> = new Subject();
  isDtInitialized: boolean;
  trainList:TrainInfo[]=[];
  selTrain:string;
  adultCount:Number;
  childCount:Number;
  classTypes:string[]=['A','B','C'];
  seltypeofclass:string;
  editBookingModel:any={};
  fromControl = new FormControl();
  toControl = new FormControl();
  fromOptions: TrainInfo[] = [];
  from_filteredOptions: Observable<TrainInfo[]>;
  toOptions: TrainInfo[] = [];
  to_filteredOptions: Observable<TrainInfo[]>;
  fromList:TrainInfo[]=[];
  toList:TrainInfo[]=[];
  selDate=new FormControl();
  class_type=new FormControl();
  avilableTrainModel:any={
    "from":"",
    "to":"",
    "selDate":"",
    "class_type":""
  };
  selCard:string="";
  banklist:BankInfo[]=[];
  selTrainInfo:TrainInfo;

  constructor(private toastr:ToastrService,private authenticationService:AuthenticationService,
    private router:Router,private modalService:NgbModal,private bookingService:BookingService,private adminService:AdminService) {
    this.authenticationService.currentUser.subscribe(x=>this.loggedUser=x);
   }

  ngOnInit(): void {
    this.loggineduser=this.loggedUser.username;
    console.log(this.loggedUser);
    if(this.loggedUser.usertype==='Admin')
      this.router.navigate(['/managetrains']);
      this.dtOptions = {
        pagingType: 'full_numbers',
        pageLength: 5,
        processing: true,
        lengthMenu:[[5,10,20,25,50,-1],[5,10,20,25,50,"All"]]
      };
      this.bookingModel={
        "username":this.loggedUser.username
      }
    this.getAllTrains();
    this.getAllBanks();
  }

  booking(){
    let bookingInfo:BookingInfo;
    let ticketNo:string;
    console.log(this.selTrainInfo);
    ticketNo="TicketId_"+Math.floor(Math.random() * 1000000000000);
    bookingInfo={
      "_id":null,
      "ticketNo":ticketNo,
      "userinfo":this.loggedUser,
      "trainInfo":this.selTrainInfo,
      "noofadult":this.adultCount,
      "noofchildren":this.childCount,
      "typeofclass":this.seltypeofclass,
      "status":"Booked"
    }
    const formdata=new FormData();
    formdata.append("bookingInfo",new Blob([JSON.stringify(bookingInfo)],{type:'application/json'}));
    formdata.append("cardNo",this.selCard);
   this.bookingService.bookSeats(formdata)
    .subscribe(res=>{
      console.log(res);
        if(res==="booking success") {
          this.toastr.success("Booked successfully");
          //this.getAllBookings();
          this.modalService.dismissAll();
        }
    },error=>this.toastr.error(error,'bookSeats'));
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

  

  setForm(bookingInfo) {
    this.editBookingModel={
        "_id":bookingInfo._id,
        "trainname":bookingInfo.trainInfo.trainname,
        "noofadult":bookingInfo.noofadult,
        "noofchildren":bookingInfo.noofchildren,
        "typeofclass":bookingInfo.typeofclass
    };
    console.log(this.bookingModel);
  }

  editbooking(form) {
    let bookingInfo:BookingInfo;
    bookingInfo=form.value;
    for(var i=0;i<this.fromList.length;i++) {
      if(form.value.trainname===this.fromList[i].trainname) {
        bookingInfo.trainInfo=this.fromList[i];
      }
    }
    bookingInfo.userinfo=this.loggedUser;
    this.bookingService.editbooking(bookingInfo)
   .subscribe(res=>{
     alert(res);
    if(res==="success") {
       this.toastr.success("Edit Successfull");
      //this.getAllBookings();
      this.modalService.dismissAll();
     }
     else
      this.toastr.error("Failed Editing Booking details");
  },error=>this.toastr.error(error,'editbooking'));
  }

  cancelbooking(booking) {
    this.bookingService.cancelbooking(booking)
   .subscribe(res=>{
    if(res==="success") {
       this.toastr.success("Cancel Booking Successfull");
      //this.getAllBookings();
      this.modalService.dismissAll();
     }
     else
      this.toastr.error("Failed Cancelling Booking");
  },error=>this.toastr.error(error,'cancelbooking'));
  }

  setTrainDetails(){
      this.from_filteredOptions = this.fromControl.valueChanges.pipe(startWith(''),map(value => this.from_filter(value)));
      
  }

  private from_filter(value: string): TrainInfo[] {
    const filterValue = value.toLowerCase();
      return this.fromList.filter(option => option.from.toLowerCase().includes(filterValue));
  }

  private to_filter(value: string): TrainInfo[] {
    const filterValue = value.toLowerCase();
      return this.toList.filter(option => option.to.toLowerCase().includes(filterValue));
  }

  getAllTrains(){
    this.adminService.getalltrains()
    .subscribe(res=>{
     this.fromList=res;
     this.toList=res;
   },error=>this.toastr.error(error,'getAllTrains'));
  }
  
  setDestTrains(FromVAlue) {
    this.toList=[];
    for(var i=0;i<this.fromList.length;i++) {
      if(this.fromList[i].from===FromVAlue) {
        this.toList.push(this.fromList[i]);
      }
    }
    this.to_filteredOptions = this.toControl.valueChanges.pipe(startWith(''),map(value => this.to_filter(value)));
  }

  setSelectedTrain(train:TrainInfo) {
    this.selTrainInfo=train;
    console.log(this.selTrainInfo);
  }

  searchTrains(form) {
    const formdata=new FormData();
    formdata.append("from",form.value.from);
    formdata.append("to",form.value.to);
    formdata.append("seldate",form.value.selDate);
    this.bookingService.searchTrains(formdata)
    .subscribe(res=>{
      if (this.isDtInitialized) {
        this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
          dtInstance.destroy();
          this.trainList=res;
          console.log(this.bookingList);
          this.dtTrigger.next();
        });
      } else {
        this.isDtInitialized = true;
        this.trainList=res;
        this.dtTrigger.next();
      }
    },error=>this.toastr.error(error,'fetchTrains'));
  }

  getAllBanks(){
    this.adminService.getAllBanks(this.loggedUser)
      .subscribe(res=>{
        this.banklist=res;
      },error=>this.toastr.error(error,'getAllBanks'));
 }
}
