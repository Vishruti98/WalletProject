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
  msg1:boolean;
  message:string;
  constructor(private formBuilder: FormBuilder,private walletService:WalletService,private router: Router) { }

  ngOnInit() {
    this.transferForm=this.formBuilder.group({
      accountNumber1:['',Validators.required],
      balance:['',Validators.required],
      accountNumber:['',Validators.required]
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
      this.walletService.transfer(accNo1,amount1,accNo).subscribe(data2 => {
        this.message="Rs"+amount1+" transferred successfully to Account Number: "+accNo1;
        alert(this.message);
        this.router.navigate(['new-options']);
      },
      err=>{
        console.log(err.stack);
      });

    }
  }

}
