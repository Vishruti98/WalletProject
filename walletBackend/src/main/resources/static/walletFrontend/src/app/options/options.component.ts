import { Component, OnInit } from '@angular/core';
import { WalletService } from '../wallet.service';
import { Router } from '@angular/router';
import { Wallet } from '../wallet';

@Component({
  selector: 'app-options',
  templateUrl: './options.component.html',
  styleUrls: ['./options.component.css']
})
export class OptionsComponent implements OnInit {
  result:any;
  constructor(private walletService:WalletService,private router: Router) { }

  ngOnInit() {
    this.transaction1();
  }

  transaction1(){
    this.walletService.getUserById(localStorage.accountNumber).subscribe(data => {
      this.result=data;
    },
    err =>{
      console.log(err.stack);
    })
  }
  
  do(){
    localStorage.removeItem("localStorage.accountNumber");
    this.router.navigate(['']);
  }

}
