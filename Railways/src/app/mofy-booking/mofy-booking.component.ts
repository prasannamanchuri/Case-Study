import { Component, OnInit, ViewChild } from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { ToastrService } from 'ngx-toastr';
import { Subject } from 'rxjs';
import { BookingInfo } from '../_models/bookinginfo';
import { UserInfo } from '../_models/userinfo';
import { AuthenticationService } from '../_service/authentication.service';
import { BookingService } from '../_service/booking.service';

@Component({
  selector: 'app-mofy-booking',
  templateUrl: './mofy-booking.component.html',
  styleUrls: ['./mofy-booking.component.css']
})
export class MofyBookingComponent implements OnInit {
  ticketNo:string;
  bookingList:BookingInfo[]=[];
  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;
  dtOptions: any = {};
  dtTrigger: Subject<any> = new Subject();
  isDtInitialized: boolean;
  loggedUser:UserInfo;

  constructor(private bookingService:BookingService,private authenticationService:AuthenticationService,private toastr:ToastrService) { 
    this.authenticationService.currentUser.subscribe(x=>this.loggedUser=x);
  }

  ngOnInit(): void {
    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 5,
      processing: true,
      lengthMenu:[[5,10,20,25,50,-1],[5,10,20,25,50,"All"]]
    };
  }

  cancelbooking(booking) {
    booking.status="Cancelled";
    alert(booking.trainInfo);
    this.bookingService.cancelbooking(booking)
   .subscribe(res=>{
    if(res==="success") {
       this.toastr.success("Booking cancelled Successfull");
       window.location.reload();
     }
     else
      this.toastr.error("Failed Cancelling Booking");
  },error=>this.toastr.error(error,'cancelbooking'));
  }

  fetchTicketDetails() {
      this.bookingService.fetchTicketDetails(this.ticketNo)
      .subscribe(
          data=>{
            if(data.length>0) {
              if (this.isDtInitialized) {
                this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
                  dtInstance.destroy();
                  this.bookingList=data;
                  console.log(this.bookingList);
                  this.dtTrigger.next();
                });
              } else {
                this.isDtInitialized = true;
                this.bookingList=data;
                this.dtTrigger.next();
              }
            }
            else
            this.toastr.error("fetchTicketDetails Failed");
          });
    }
}
