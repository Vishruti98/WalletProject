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
  msg1:any;
  message:string;
  errormsg:string;
  constructor(private formBuilder: FormBuilder,private walletService:WalletService,private router: Router) { }

  ngOnInit() {
    this.withdrawForm=this.formBuilder.group({
      accountNumber:['',Validators.required],
      balance:['',[Validators.required,Validators.min(1)]]
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
      this.walletService.validate(localStorage.password, this.withdrawForm.controls.accountNumber.value).subscribe(data1 => {
        this.msg1=data1;
        if(this.msg1==true)
        {
      this.walletService.withdraw(accNo1,amount1).subscribe(data2 => {
        this.message="Rs"+amount1+" Withdrawn successfully from Account Number: "+accNo1;
        alert(this.message);
        this.router.navigate(['new-options']);
      },
      err=>{
        this.errormsg=err.error;
      alert(this.errormsg);
      });
    }
    else{
      alert('Sorry! Account Details not correct');
    }
    },
    err =>{
      this.errormsg=err.error;
      alert(this.errormsg);
    });
    }
  }

  do(){
    
    this.router.navigate(['new-options']);
  }

}
