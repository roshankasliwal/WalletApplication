import { FormBuilder, Validators } from '@angular/forms';
import { Router} from '@angular/router';
import { WalletService } from './../../services/wallet.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-withdraw',
  templateUrl: './withdraw.component.html',
  styleUrls: ['./withdraw.component.css']
})
export class WithdrawComponent implements OnInit {

  amount:number;

  constructor(public walletService:WalletService,public router:Router,public formBuilder:FormBuilder) { }

  ngOnInit(): void {
  
  }
  myForm=this.formBuilder.group({
    amount:["",[Validators.required,Validators.pattern('\\d*')]],
});



  withdrawAction(){
    
    this.walletService.withdraw(this.amount).subscribe(
      data=>{
        window.alert("Amount Withdraw Successfully");
        this.router.navigate(['']);
      },
      err=>{
        window.alert(err.error.errorMessage);
        this.amount=null;
      }
    );
    
  }
  

}
