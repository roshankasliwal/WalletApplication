import { AuthService } from './../services/auth.service';
import { Injectable } from '@angular/core';
import { CanActivate} from '@angular/router';
import {Router} from '@angular/router';
@Injectable({
  providedIn: 'root'
})
export class LoginGuard implements CanActivate {

  constructor(public authService:AuthService, public router:Router){

  }
  canActivate(): boolean{
    if(this.authService.isAutheticated()){
      this.router.navigate(['']);
      return false;
    }
    return true;
  }
  
}
