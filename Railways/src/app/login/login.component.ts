import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AppService } from '../_service/app.service';
import { AuthenticationService } from '../_service/authentication.service';
import { UserInfo } from '../_models/userinfo';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  userinfo: UserInfo[];
  userModel:any={};

  constructor(private formBuilder: FormBuilder,private route:ActivatedRoute,private router:Router,
    private authenticationService:AuthenticationService,private appService:AppService,private toastr:ToastrService)
   { }

  ngOnInit(): void {
    this.userModel={
      'username':"",
      'password':""
    };
    this.getAllUsers();
  }

 login(form){
  const formData=new FormData();
  formData.append("Username",form.value.username);
  formData.append("Password",form.value.password);
  this.authenticationService.login(formData)
    .subscribe(
        data=>{
          if(data!=null) {
            localStorage.setItem('currentUser',JSON.stringify(data));
            this.authenticationService.setCurrentUserValue(data);
            this.router.navigate(['/']);
          }
          else{
            this.toastr.error("Failed login, wrong password entered");
          }
        }, error =>{
          this.toastr.error(error.error.message,'Error');
        }
    );
 }

 getAllUsers(){
    this.authenticationService.getAllUsers()
      .subscribe(res=>{
        this.userinfo=res;
      },error=>this.toastr.error(error,'GetAllUsers'));
 }

 signup(){
   this.router.navigate(['/signup']);
 }

}
