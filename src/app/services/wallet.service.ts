import { FundTransferRequest } from './../entity/fundTransfer';
import { catchError, retry } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { Transaction } from '../entity/transaction';
@Injectable({
  providedIn: 'root'
})
export class WalletService {

  
  private depositURL = 'http://localhost:9091/wallet/deposit/';
  private withdrawURL = 'http://localhost:9091/wallet/withdraw/';
  private fundTransferURL = 'http://localhost:9091/wallet/fundTransfer';
  private transactionsURL = 'http://localhost:9091/wallet/transactions/';

  constructor(private http:HttpClient) { }


  deposit(amount:number){
    
    let accountNumber=sessionStorage.getItem('accountNumber');
    
    return this.http.get(`${this.depositURL}`+accountNumber+'/'+amount).pipe(
      catchError(this.handleError)
    );

  }

  withdraw(amount:number){
    let accountNumber=sessionStorage.getItem('accountNumber');
    
    return this.http.get(`${this.withdrawURL}`+accountNumber+'/'+amount).pipe(
      catchError(this.handleError)
    );
        
  }

  fundTransfer(request:FundTransferRequest){
      return this.http.post(`${this.fundTransferURL}`,request).pipe(
            catchError(this.handleError)
        );
  }

  getTransaction():Observable<Transaction[]>{
    let accountNumber=sessionStorage.getItem('accountNumber');
    
    return this.http.get<Transaction[]>(`${this.transactionsURL}`+accountNumber);
  }

  handleError(error) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // client-side error
      errorMessage = `Error: ${error.error.message}`;
      console.log("from client side");
    } else {
      // server-side error
      errorMessage = `Error: ${error.error.errorMessage}`;
    }
    console.log(error.error.errorMessage);
    return throwError(error);
  }
}
