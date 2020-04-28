import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import {catchError} from 'rxjs/operators';
import { Wallet } from './wallet';
import { Transaction } from './transaction';

@Injectable({
  providedIn: 'root'
})
export class WalletService {

  private baseUrl = 'http://localhost:8080/api';
  constructor(private http: HttpClient) { }

  showBalance(accountNumber: number): Observable<Object>{
    return this.http.get<number>(`${this.baseUrl}/get/${accountNumber}`);
  }

  create(wallet: Object): Observable<Object>{
    return this.http.post<number>(`${this.baseUrl}/add`,wallet);
  }

  deposit(accountNumber: number, amount: number){
    return this.http.get<Transaction>(this.baseUrl+"/deposit/"+accountNumber+"/"+amount);
  }

  withdraw(accountNumber: number, amount: number): Observable<Object>{
    return this.http.get<Transaction>(`${this.baseUrl}/withdraw/${accountNumber}/${amount}`).pipe(catchError(this.handleError));
  }

  transfer(amount: number, accountNumber1: number,accountNumber: number){
    return this.http.get<Transaction>(this.baseUrl+"/transfer/"+amount+"/"+accountNumber1+"/"+accountNumber).pipe(catchError(this.handleError));
  }

  printAllTransaction(accountNumber: number): Observable<Object>{
    return this.http.get<Transaction[]>(`${this.baseUrl}/print/${accountNumber}`);
  }

  validateAccount(accountNumber: number): Observable<Object>{
    return this.http.get<boolean>(`${this.baseUrl}/validateAccount/${accountNumber}`);
  }

  validate(password:String, accountNumber: number): Observable<Object>{
    return this.http.get<boolean>(`${this.baseUrl}/validate/${password}/${accountNumber}`).pipe(catchError(this.handleError));
  }

  getUserById(accountNumber: number): Observable<Object>{
    return this.http.get<Wallet>(`${this.baseUrl}/getUser/${accountNumber}`);
  }

  private handleError(errorResponse:HttpErrorResponse){
    return throwError(errorResponse);
  }

}
