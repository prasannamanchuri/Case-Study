import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { UserInfo } from '../_models/userinfo';
import { SignupService } from '../_service/signup.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  userModel:any={};
  constructor(private signupService:SignupService,private toastrService:ToastrService) { }

  ngOnInit(): void {
  }
  signup(form) {
    let userinfo:UserInfo;
    userinfo=form.value;
    this.signupService.signup(userinfo)
    .subscribe(
        data=>{
          if(data==="success") {
            this.toastrService.success("Signup successfull");
            this.userModel={};
          }
          else
          this.toastrService.error("Singup Failed");
        });
  }
}
