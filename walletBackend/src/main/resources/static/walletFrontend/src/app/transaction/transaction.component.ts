import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { WalletService } from '../wallet.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-transaction',
  templateUrl: './transaction.component.html',
  styleUrls: ['./transaction.component.css']
})
export class TransactionComponent implements OnInit {

  submitted: boolean=false;
  transactionForm: FormGroup;
  msg1:any;
  errormsg:string;
  message:any;
  constructor(private formBuilder: FormBuilder,private walletService:WalletService,private router: Router) { }

  ngOnInit() {
    if(!(localStorage.userName || localStorage.password))
    {
      this.router.navigate(['']);
    }
    this.transactionForm=this.formBuilder.group({
      accountNumber:['',[Validators.required,Validators.pattern('.{5,}'),Validators.min(1)]]
    });
  }
  transaction(){
    this.submitted=true;
    if(this.transactionForm.invalid)
    {
      return;
    }
    else{
     
      this.walletService.validate(localStorage.password,this.transactionForm.controls.accountNumber.value).subscribe(data => {
        this.msg1=data;
        if(this.msg1==true){
          localStorage.accountNumber=this.transactionForm.controls.accountNumber.value;
          this.router.navigate(['new-print']);
        }
        else{
          alert('Sorry! Account Number not correct');
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
