import { Component, OnInit } from '@angular/core';
import { WalletService } from '../wallet.service';
import { Wallet } from '../wallet';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  wallet: Wallet=new Wallet();
  submitted=false;
  check=false;
  createForm: FormGroup;
  msg1:boolean;
  message:any;

  constructor(private formBuilder: FormBuilder,private walletService:WalletService,private router: Router) { }

  ngOnInit() {
    this.createForm=this.formBuilder.group({
      username:['',[Validators.required,Validators.pattern("[A-Za-z]{2,32}")]],
      password:['',Validators.required],
      phoneNumber:['',[Validators.required, Validators.pattern("^((\\+91-?)|0)?[0-9]{10}$")]]
    });
  }

  newWallet(): void{
    this.submitted=false;
    this.wallet=new Wallet();
  }

  save(){
    this.submitted=true;
    if(this.createForm.invalid)
    {
      return;
    }
    else{
      this.check=true;
    this.walletService.create(this.wallet).subscribe(data => 
      {
        console.log(data);
        this.wallet=new Wallet();
        this.message=data;
        
            alert("Account Number generated as: "+this.message);
            
            this.router.navigate(['new-login']);
      },
      err => 
      { console.log(err.stack);
      });
    }
  }

  onSubmit(){ 
    
    this.save();
  } 

}
