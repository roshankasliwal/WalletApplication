import { Router } from '@angular/router';
import { AuthService } from './../../services/auth.service';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators,FormGroup } from '@angular/forms';

@Component({
  selector: 'app-forgot',
  templateUrl: './forgot.component.html',
  styleUrls: ['./forgot.component.css']
})
export class ForgotComponent implements OnInit {

  mobileNumber:number;
  newPassword:String;
  errorMessage:String;
  
  constructor(public authService : AuthService,public router : Router, private formBuilder:FormBuilder) { }

  ngOnInit(): void {
  }
myForm=this.formBuilder.group({
    mobileNumber:[null,[Validators.required,Validators.pattern('[6-9]\\d{9}')]],
    newPassword:["",Validators.required],
});

  forgetAction(){(
      this.authService.forgot(this.mobileNumber,this.newPassword).subscribe(
        data=>{
          window.alert("password Changed !!!");
          this.router.navigate(['']);
        },err=>{
          window.alert(err.error.errorMessage);
        }
      ));
      
  }

}
