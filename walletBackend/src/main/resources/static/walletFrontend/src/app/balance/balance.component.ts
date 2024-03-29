import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { WalletService } from '../wallet.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-balance',
  templateUrl: './balance.component.html',
  styleUrls: ['./balance.component.css']
})
export class BalanceComponent implements OnInit {

  submitted: boolean=false;
  balanceForm: FormGroup;
  msg1:any;
  message:any;
  errormsg:string;
  result:any;
  check: boolean=true;
  constructor(private formBuilder: FormBuilder,private walletService:WalletService,private router: Router) { }

  ngOnInit() {
    this.balanceForm=this.formBuilder.group({
      accountNumber:['',[Validators.required,Validators.pattern('.{5,}'),Validators.min(1)]]
    });
    
  }
  balance(){
    this.submitted=true;
    if(this.balanceForm.invalid)
    {
      return;
    }
    else{
      let accNo1=this.balanceForm.controls.accountNumber.value;
      this.walletService.validate(localStorage.password, this.balanceForm.controls.accountNumber.value).subscribe(data1 => {
        this.msg1=data1;
        if(this.msg1==true)
        {
      this.walletService.showBalance(accNo1).subscribe(data2 => {
        this.message=data2;
        //alert("Rs."+this.message);
        if(this.submitted==true)
        {
          this.check=false;
          this.transaction1();
        }
        // this.router.navigate(['new-options']);
      },
      err=>{
        console.log(err.stack);
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
  
  transaction1(){
    this.walletService.getUserById(localStorage.accountNumber).subscribe(data => {
      this.result=data;
    },
    err =>{
      console.log(err.stack);
    })
  }

  do(){
    this.router.navigate(['new-options']);
  }


}
