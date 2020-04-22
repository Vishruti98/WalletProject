import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { WalletService } from '../wallet.service';
import { Router} from '@angular/router';

@Component({
  selector: 'app-deposit',
  templateUrl: './deposit.component.html',
  styleUrls: ['./deposit.component.css']
})
export class DepositComponent implements OnInit {

  submitted: boolean=false;
  depositForm: FormGroup;
  msg1:boolean;
  message:string;
  constructor(private formBuilder: FormBuilder,private walletService:WalletService,private router: Router) { }

  ngOnInit() {
    this.depositForm=this.formBuilder.group({
      accountNumber:['',Validators.required],
      balance:['',Validators.required]
    });
  }
  deposit(){
    this.submitted=true;
    if(this.depositForm.invalid)
    {
      return;
    }
    else{
      let accNo1=this.depositForm.controls.accountNumber.value;
      let amount1=this.depositForm.controls.balance.value;
      this.walletService.deposit(accNo1,amount1).subscribe(data2 => {
        this.message="Rs"+amount1+" deposited successfully from Account Number: "+accNo1;
        alert(this.message);
        this.router.navigate(['new-options']);
      },
      err=>{
        console.log(err.stack);
      });

    }
  }

}
