import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { WalletService } from '../wallet.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-transfer',
  templateUrl: './transfer.component.html',
  styleUrls: ['./transfer.component.css']
})
export class TransferComponent implements OnInit {

  submitted: boolean=false;
  transferForm: FormGroup;
  msg1:any;
  message:string;
  errormsg:string;
  constructor(private formBuilder: FormBuilder,private walletService:WalletService,private router: Router) { }

  ngOnInit() {
    this.transferForm=this.formBuilder.group({
      accountNumber1:['',[Validators.required,Validators.pattern('.{5,}'),Validators.min(1)]],
      balance:['',[Validators.required,Validators.min(1)]],
      accountNumber:['',[Validators.required,Validators.pattern('.{5,}'),Validators.min(1)]]
    });
  }
  transfer(){
    this.submitted=true;
    if(this.transferForm.invalid)
    {
      return;
    }
    else{
      let accNo1=this.transferForm.controls.accountNumber1.value;
      let amount1=this.transferForm.controls.balance.value;
      let accNo=this.transferForm.controls.accountNumber.value;
      this.walletService.validate(localStorage.password, this.transferForm.controls.accountNumber.value).subscribe(data1 => {
        this.msg1=data1;
        if(this.msg1==true)
        {
      this.walletService.transfer(amount1,accNo1,accNo).subscribe(data2 => {
        this.message="Rs"+amount1+" transferred successfully to Account Number: "+accNo1;
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
