import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminViewComponent } from './admin-view/admin-view.component';
import { AuthGuard } from './helpers/auth.guard';
import { HistoryComponent } from './history/history.component';
import { LoginComponent } from './login/login.component';
import { MofyBookingComponent } from './mofy-booking/mofy-booking.component';
import { SignupComponent } from './signup/signup.component';
import { UserViewComponent } from './user-view/user-view.component';

const routes: Routes = [
  {path: 'login', component:LoginComponent},
  { path: 'signup', component: SignupComponent },
  { path: 'adminview', component: AdminViewComponent, canActivate:[AuthGuard] },
  { path: 'userview', component: UserViewComponent, canActivate:[AuthGuard] },
  { path: 'history', component: HistoryComponent, canActivate:[AuthGuard] },
  { path: 'cancel', component: MofyBookingComponent, canActivate:[AuthGuard] },
  { path: '', redirectTo: '/userview', pathMatch:'full'},
  { path: '**', redirectTo:'/404'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

 