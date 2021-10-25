import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from "@angular/router";
import { AuthenticationService } from "../_service/authentication.service";

@Injectable({providedIn:'root'})
export class AuthGuard implements CanActivate {
    constructor(private router:Router,private authenticationService:AuthenticationService){}

    canActivate(route:ActivatedRouteSnapshot,state:RouterStateSnapshot) {
        const currentUser=this.authenticationService.currentUserValue;
        console.log("currentUser..."+currentUser);
        console.log("Username"+currentUser.username);
        if(currentUser.username!=undefined)
            return true;
        this.router.navigate(['/login'],{queryParams:{returnUrl:state.url}});
        return false;
    }
}