import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class WalletService {

  private baseUrl = 'http://localhost:8080/api';
  constructor(private http: HttpClient) { }

  showBalance(accountNumber: number): Observable<Object>{
    return this.http.get(`${this.baseUrl}/get/${accountNumber}`);
  }

  create(wallet: Object): Observable<Object>{
    return this.http.post(`${this.baseUrl}/add`,wallet);
  }

  deposit(accountNumber: number, amount: number){
    return this.http.get(this.baseUrl+"/deposit/"+accountNumber+"/"+amount);
  }

  withdraw(accountNumber: number, amount: number): Observable<Object>{
    return this.http.get(`${this.baseUrl}/withdraw/${accountNumber}/${amount}`);
  }

  transfer(accountNumber1: number, amount: number,accountNumber: number){
    return this.http.get(this.baseUrl+"/transfer/"+accountNumber1+"/"+amount+"/"+accountNumber);
  }

  printAllTransaction(accountNumber: number): Observable<Object>{
    return this.http.get(`${this.baseUrl}/print/${accountNumber}`);
  }
}
