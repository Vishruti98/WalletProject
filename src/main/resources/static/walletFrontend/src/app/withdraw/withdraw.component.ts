import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { WalletService } from '../wallet.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-withdraw',
  templateUrl: './withdraw.component.html',
  styleUrls: ['./withdraw.component.css']
})
export class WithdrawComponent implements OnInit {

  submitted: boolean=false;
  withdrawForm: FormGroup;
  msg1:boolean;
  message:string;
  constructor(private formBuilder: FormBuilder,private walletService:WalletService,private router: Router) { }

  ngOnInit() {
    this.withdrawForm=this.formBuilder.group({
      accountNumber:['',Validators.required],
      balance:['',Validators.required]
    });
  }
  withdraw(){
    this.submitted=true;
    if(this.withdrawForm.invalid)
    {
      return;
    }
    else{
      let accNo1=this.withdrawForm.controls.accountNumber.value;
      let amount1=this.withdrawForm.controls.balance.value;
      this.walletService.withdraw(accNo1,amount1).subscribe(data2 => {
        this.message="Rs"+amount1+" Withdrawn successfully from Account Number: "+accNo1;
        alert(this.message);
        this.router.navigate(['new-options']);
      },
      err=>{
        console.log(err.stack);
      });

    }
  }

}
