import { FormBuilder, Validators } from '@angular/forms';
import { FundTransferRequest } from './../../entity/fundTransfer';
import { Router } from '@angular/router';
import { WalletService } from './../../services/wallet.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-fundtransfer',
  templateUrl: './fundtransfer.component.html',
  styleUrls: ['./fundtransfer.component.css']
})
export class FundtransferComponent implements OnInit {

  amount:number;
  receiverAccountNumber:number;
  request: FundTransferRequest=new FundTransferRequest();
  constructor(public walletService:WalletService,public router:Router,public formBuilder: FormBuilder) { }

  ngOnInit(): void {
  
  }
  myForm=this.formBuilder.group({
    mobileNumber:[null,[Validators.required,Validators.pattern('\\d{7}')]],
    amount:["",[Validators.required,Validators.pattern('\\d*')]],
});


  fundTransferAction(){
  
    this.request.senderWalletNumber=Number(sessionStorage.getItem('accountNumber'));
    this.request.receiverWalletNumber=this.receiverAccountNumber;
    this.request.amount=this.amount;
    this.walletService.fundTransfer(this.request).subscribe(
      result=>{
        this.router.navigate(['']);
        window.alert("Fund Transfer Successfully")
      },
      err=>{
        window.alert(err.error.errorMessage);
        this.amount=null;
        this.receiverAccountNumber=null;
      }

    );
    
  }
 

}
