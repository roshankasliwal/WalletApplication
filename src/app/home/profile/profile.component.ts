import { LoginResponse } from './../../entity/loginresponse';
import { Customer } from './../../entity/customer';
import { Router } from '@angular/router';
import { AuthService } from './../../services/auth.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  customer:Customer=new Customer();

  constructor(public authService:AuthService,public router:Router) { }

  ngOnInit(): void {
    this.authService.getuserData().subscribe(data=>{
         this.customer=data;
         console.log(this.customer);
    let accountNumber=this.customer.wallet.walletNumber.toString();
    sessionStorage.setItem('accountNumber',accountNumber);
         console.log(accountNumber);
    });
    }
  
}
