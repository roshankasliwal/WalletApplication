import { Address } from './address';
import { Wallet } from './wallet';
export class Customer{
    id:number; 
    mobileNumber:number;
    name:String;
    password:String;
    emailId:String;
    wallet:Wallet;
    address:Address;
}