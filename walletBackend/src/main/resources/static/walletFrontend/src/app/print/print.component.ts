import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { WalletService } from '../wallet.service';
import { Transaction } from '../transaction';

@Component({
  selector: 'app-print',
  templateUrl: './print.component.html',
  styleUrls: ['./print.component.css']
})
export class PrintComponent implements OnInit {
result:any;
constructor(private walletService:WalletService,private router: Router) { }

  ngOnInit() {
    if(!(localStorage.username || localStorage.password))
    {
      this.router.navigate(['']);
    }
    if((localStorage.username && localStorage.password)&& !(localStorage.accountNumber))
    {
      this.router.navigate(['new-options']);
    }
    this.transaction1();
  }

  transaction1(){
    this.walletService.printAllTransaction(localStorage.accountNumber).subscribe(data => {
      this.result=data;
      console.log(this.result);
    },
    err =>{
      console.log(err.stack);
    })
  } 

  do(){
    
    this.router.navigate(['new-options']);
  }

}
