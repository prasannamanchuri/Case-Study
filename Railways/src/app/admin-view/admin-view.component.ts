import { Component, OnInit, ViewChild } from '@angular/core';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { DataTableDirective } from 'angular-datatables';
import { ToastrService } from 'ngx-toastr';
import { Subject } from 'rxjs';
import { AdminService } from '../_service/admin.service';
import { TrainInfo } from '../_models/traininfo';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-admin-view',
  templateUrl: './admin-view.component.html',
  styleUrls: ['./admin-view.component.css']
})
export class AdminViewComponent implements OnInit {
  trainlist:TrainInfo[]=[];
  @ViewChild(DataTableDirective)
  dtElement: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  dtTrigger: Subject<any> = new Subject();
  closeResult: string;
  trainModel:any={};
  editTrainModel:any={};
  deleteTrainModel:any={};
  isDtInitialized:boolean;
  constructor(private adminservice:AdminService,private toastr:ToastrService,private modalService:NgbModal) { }

  ngOnInit(): void {
    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 5,
      processing: true,
      lengthMenu:[[5,10,20,25,50,-1],[5,10,20,25,50,"All"]]
    };
    this.getAllTrains();
    this.trainModel={
      trainname:'',
      from:'',
      to:'',
      date:null,
      class_a_seats:0,
      class_b_seats:0,
      class_c_seats:0,
      class_a_amount:0,
      class_b_amount:0,
      class_c_amount:0
    };
    console.log(this.trainModel);
  }
  
  getAllTrains(){
    this.adminservice.getalltrains()
      .subscribe(res=>{
        this.trainlist=res;
        if (this.isDtInitialized) {
          this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
            dtInstance.destroy();
            this.trainlist=res;
            this.dtTrigger.next();
          });
        } else {
          this.isDtInitialized = true;
          this.trainlist=res;
          this.dtTrigger.next();
        }
        this.toastr.success("Fetched train details Successfully");
      },error=>this.toastr.error(error,'getAllTrains'));
 }
 addTrain(form){
  let traininfo:TrainInfo;
  traininfo=form.value;
  traininfo.status="Available";
  console.log(traininfo);
  this.adminservice.addtrains(traininfo)
   .subscribe(res=>{
      if(res==="success") {
        this.toastr.success("Added Train details successfully");
        this.getAllTrains();
        this.modalService.dismissAll();
      }
  },error=>this.toastr.error(error,'Addtraininfo'));
}
editTrain(form){
  let traininfo:TrainInfo;
  traininfo=form.value;
  console.log(traininfo);
  this.adminservice.editTrainInfo(traininfo)
   .subscribe(res=>{
    console.log("Booking Success"); 
    if(res==="success") {
       this.toastr.success("Edit Successfull");
      this.getAllTrains();
      this.modalService.dismissAll();
     }
     else
      this.toastr.error("Failed Editing Train details");
  },error=>this.toastr.error(error,'edittraininfo'));
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

setForm(trainname,type) {
  const formdata=new FormData();
  formdata.append("trainname",trainname);
   this.adminservice.getTrainInfoByTrainnname(formdata)
   .subscribe(res=>{
    if(res.length>0) {
      let traininfo:TrainInfo;
      traininfo=res[0];
      var object={
        trainname:traininfo.trainname,
        from:traininfo.from,
        to:traininfo.to,
        date:traininfo.date,
        class_a_seats:traininfo.class_a_seats,
        class_b_seats:traininfo.class_b_seats,
        class_c_seats:traininfo.class_c_seats,
        class_a_amount:traininfo.class_a_amount,
        class_b_amount:traininfo.class_b_amount,
        class_c_amount:traininfo.class_c_amount,
        status:traininfo.status
      };
      if(type==='edit')
        this.editTrainModel=object;
      else
        this.deleteTrainModel=object;
    }
    else
      this.toastr.error("No Results found for trainnname");
  },error=>this.toastr.error(error,'setEditTrainInfo'));
}

deleteTrain(trainname) {
  const formdata=new FormData();
  formdata.append("trainname",trainname);
   this.adminservice.deleteTrain(formdata)
   .subscribe(res=>{
      if(res==="success") {
        this.toastr.success("Deleted Train details successfully");
        this.getAllTrains();
        this.modalService.dismissAll();
      }
  },error=>this.toastr.error(error,'Deletetraininfo'));
}
}
