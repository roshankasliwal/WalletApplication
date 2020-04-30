
import { LoginRequest } from './../../entity/loginrequest';
import { AuthService } from './../../services/auth.service';
import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

mobileNumber:number;
password:String;
userRequest:LoginRequest=new LoginRequest();
check:any; 


public errorMessage:String;
  constructor(public authService:AuthService,public router:Router) { 
    this.errorMessage=" ";
  }

  ngOnInit(): void {
  }
  
  loginAction(){
    if(this.mobileNumber && this.password){
      
      this.errorMessage=" ";
      this.userRequest.mobileNumber=this.mobileNumber;
      this.userRequest.password=this.password;
      console.log(this.userRequest);

      this.authService.login(this.userRequest).
      subscribe(
        result=>{
         console.log(result);
         if(result)
          {
            sessionStorage.setItem('logindetail','content');
            let num=this.mobileNumber.toString();
            sessionStorage.setItem('mobileNumber',num);
            this.router.navigate(['']);
          }
          else
          {
            this.errorMessage="Invalid Password ";
          }
      },
      err => {
          {
            this.errorMessage="Invalid Username ";
            console.log("Invalid username ");
            console.log(err.error);
           // window.alert(err.error.errorMessage)
          }
      });
     }
    else{
      this.errorMessage="Fill the empty text box ";
    }
  }

}
