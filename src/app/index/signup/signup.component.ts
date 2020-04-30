import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { SignupRequest } from './../../entity/signuprequest';
import { AuthService } from './../../services/auth.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  signupRequest:SignupRequest = new SignupRequest();
  constructor(public authService:AuthService,public router:Router,private formBuilder:FormBuilder){

  }
  myForm=this.formBuilder.group({
    mobileNumber:[null,[Validators.required,Validators.pattern('[6-9]\\d{9}')]],
    password:["",Validators.required],
    name:["",Validators.required],
    emailId:["",[Validators.required,Validators.pattern('[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}')]]
});

  ngOnInit(): void {
  }
  signupAction(){
     this.authService.signup(this.signupRequest).subscribe(
       data=>{
         console.log(data);
         window.alert('Account Created Successfully');
         this.router.navigate(['login']);
       },
       err=>{
        window.alert(err.error.errorMessage);
        
        this.router.navigate(['login']);
       }
     );
     
  }

}
