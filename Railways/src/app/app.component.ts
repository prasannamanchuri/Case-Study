import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { AppService } from './_service/app.service';
import { AuthenticationService } from './_service/authentication.service';
import { UserInfo } from './_models/userinfo';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  currentUser: UserInfo;

  constructor(private router:Router,private authenticationService:AuthenticationService,
    private appService:AppService,private toastr:ToastrService) {
      this.authenticationService.currentUser.subscribe(x=>this.currentUser=x);
      
    }
  
    ngOnInit(){
      console.log(this.currentUser);
    }

    logout(){
      this.authenticationService.logout();
      this.router.navigate(['/login']);
    }

    
}
