import { Transaction } from './../../entity/transaction';
import { WalletService } from './../../services/wallet.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-transactions',
  templateUrl: './transactions.component.html',
  styleUrls: ['./transactions.component.css']
})
export class TransactionsComponent implements OnInit {

  transactions:Transaction[];
  constructor(public walletService:WalletService) { }

  ngOnInit(): void {
    this.walletService.getTransaction().subscribe(
      data=>{
        console.log(data);
        this.transactions=data;
      }
    );
  }

}
