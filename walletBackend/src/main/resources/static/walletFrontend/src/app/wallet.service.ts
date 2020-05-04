import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError, observable } from 'rxjs';
import {catchError} from 'rxjs/operators';
import { Wallet } from './wallet';
import { Transaction } from './transaction';
import { wrapListenerWithDirtyLogic } from '@angular/core/src/render3/instructions';

@Injectable({
  providedIn: 'root'
})
export class WalletService {

  private baseUrl = 'http://localhost:8080/api';
  constructor(private http: HttpClient) { }

  /* this method when called take accountNumber as argument and then using HttpClient Object ,it will 
  use get request to retrieve number type observable object*/
  showBalance(accountNumber: number){
    return this.http.get<number>(this.baseUrl+"/get/"+accountNumber);
  }

  /* this method when called take wallet object as argument and then using HttpClient Object ,it will 
  use post request to create and return number type observable object which is Account number*/
  create(wallet: Object) {
    return this.http.post<number>(this.baseUrl+"/add",wallet);
  }

  /* this method when called take accountNumber and amount as argument and then using HttpClient Object ,it will 
  use get request to retrieve Transaction type observable object*/
  deposit(accountNumber: number, amount: number){
    return this.http.get<Transaction>(this.baseUrl+"/deposit/"+accountNumber+"/"+amount);
  }

  /* this method when called take accountNumber and amount as argument and then using HttpClient Object ,it will 
  use get request to retrieve Transaction type observable object.
  If recieved error from server then will catch it and will pass it a handler method*/
  withdraw(accountNumber: number, amount: number){
    return this.http.get<Transaction>(this.baseUrl+"/withdraw/"+accountNumber+"/"+amount).pipe(catchError(this.handleError));
  }

  /* this method when called take accountNumber of sender, accountNumber1 of reciever and amount as argument 
  and then using HttpClient Object ,it will use get request to retrieve Transaction type observable object.
  If recieved error from server then will catch it and will pass it a handler method*/
  transfer(amount: number, accountNumber1: number,accountNumber: number){
    return this.http.get<Transaction>(this.baseUrl+"/transfer/"+amount+"/"+accountNumber1+"/"+accountNumber).pipe(catchError(this.handleError));
  }

  /* this method when called take accountNumber as argument and then using HttpClient Object ,it will 
  use get request to retrieve Transaction type observable object.*/
  printAllTransaction(accountNumber: number) {
    return this.http.get<Transaction[]>(this.baseUrl+"/print/"+accountNumber);
  }

  /* this method when called take accountNumber and password as argument and then using HttpClient Object ,it will 
  use get request to retrieve boolean type observable object.
  If recieved error from server then will catch it and will pass it a handler method*/
  validate(password:String, accountNumber: number) {
    return this.http.get<boolean>(this.baseUrl+"/validate/"+password+"/"+accountNumber).pipe(catchError(this.handleError));
  }

  /* this method when called take accountNumber as argument and then using HttpClient Object ,it will 
  use get request to retrieve Wallet type observable object*/
  getUserById(accountNumber: number) {
    return this.http.get<Wallet>(this.baseUrl+"/getUser/"+accountNumber);
  }

  /* this method is used to handel error.
  A response that represents an error or failure, either from a non-successful HTTP status
  or an error while executing the request is passes as argument*/
  private handleError(errorResponse:HttpErrorResponse){
    return throwError(errorResponse);
  }

}
