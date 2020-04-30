import { SignupRequest } from './../entity/signuprequest';
import { LoginRequest } from './../entity/loginrequest';

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';



@Injectable({
  providedIn: 'root'
})
export class AuthService {

  
  constructor(private http:HttpClient) { }

  private loginURL = 'http://localhost:9091/customer/login';
  private signupURL = 'http://localhost:9091/customer/signup';
  
  public isAutheticated(): boolean{
    
   var temp=sessionStorage.getItem('mobileNumber');
          if(temp)
          {
            return true;
          }
          else{
            return false;
          }
      }
  public login(loginrequest:LoginRequest):Observable<any>{
  return this.http.post(`${this.loginURL}`,loginrequest); 
  }

  public getuserData():Observable<any>{
   var mobileNumber=sessionStorage.getItem('mobileNumber');
   console.log(mobileNumber)
    return this.http.get('http://localhost:9091/customer/search/'+mobileNumber);
  }

  public signup(signupRequest:SignupRequest){
    return this.http.post(`${this.signupURL}`,signupRequest).pipe(
      catchError(this.handleError)
  );
  }
  public forgot(mobileNumber,newPassword){
        return this.http.get('http://localhost:9091/customer/forgot/'+mobileNumber+'/'+newPassword).pipe(
          catchError(this.handleError)
      );
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

  public logout(){
    sessionStorage.removeItem('logindetail');
    sessionStorage.removeItem('mobileNumber');
    sessionStorage.removeItem('accountNumber');
    sessionStorage.clear();
    return true;
  }
}
