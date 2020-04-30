import { FormBuilder, Validators } from '@angular/forms';
import { WalletService } from './../../services/wallet.service';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-deposit',
  templateUrl: './deposit.component.html',
  styleUrls: ['./deposit.component.css']
})
export class DepositComponent implements OnInit {

  amount:number;

  constructor(public walletService:WalletService,public router:Router,public formBuilder:FormBuilder) { }

  ngOnInit(): void {
  
  }
  myForm=this.formBuilder.group({
    amount:["",[Validators.required,Validators.pattern('\\d*')]],
  });


  

  depositAction(){
    this.walletService.deposit(this.amount).subscribe();
    window.alert("Amount Deposited Successfully")
    this.router.navigate(['']);
  }
  
}
