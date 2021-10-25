import { Component, OnInit, ViewChild } from '@angular/core';
import { DataTableDirective } from 'angular-datatables';
import { ToastrService } from 'ngx-toastr';
import { Subject } from 'rxjs';
import { BookingInfo } from '../_models/bookinginfo';
import { UserInfo } from '../_models/userinfo';
import { AuthenticationService } from '../_service/authentication.service';
import { BookingService } from '../_service/booking.service';

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.css']
})
export class HistoryComponent implements OnInit {

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
      lengthMenu:[[5,10,20,25,50,-1],[5,10,20,25,50,"All"]],
      dom: 'Bfrtip',
      buttons: [
          'copy', 'csv', 'excel', 'print'
      ]
    };
  this.getAllBookings();
  }

  getAllBookings() {
    this.bookingService.getAllBookings(this.loggedUser)
    .subscribe(res=>{
      console.log(res);
        if (this.isDtInitialized) {
          this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
            dtInstance.destroy();
            this.bookingList=res;
            console.log(this.bookingList);
            this.dtTrigger.next();
          });
        } else {
          this.isDtInitialized = true;
          this.bookingList=res;
          this.dtTrigger.next();
        }
    },error=>this.toastr.error(error,'getAllBookings'));
  }

}
