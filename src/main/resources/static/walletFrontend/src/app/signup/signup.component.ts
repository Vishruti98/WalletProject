import { Component, OnInit } from '@angular/core';
import { WalletService } from '../wallet.service';
import { Wallet } from '../wallet';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  wallet: Wallet=new Wallet();
  submitted=false;

  constructor(private walletService: WalletService) { }

  ngOnInit() {
  }

  newWallet(): void{
    this.submitted=false;
    this.wallet=new Wallet();
  }

  save(){
    this.walletService.create(this.wallet)
      .subscribe(data => console.log(data),error => console.log(error));
    this.wallet=new Wallet();
  }

  onSubmit(){
    this.submitted=true;
    this.save();
  }

}
