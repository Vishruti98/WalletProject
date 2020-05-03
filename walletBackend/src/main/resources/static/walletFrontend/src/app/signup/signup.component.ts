import { Component, OnInit } from '@angular/core';
import { WalletService } from '../wallet.service';
import { Wallet } from '../wallet';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { CustomValidators } from '../custom-validators';
import { CustomValidator2 } from '../custom-validator2';

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
  result:any;

  constructor(private formBuilder: FormBuilder,private walletService:WalletService,private router: Router) { }

  ngOnInit() {
    this.createForm=this.formBuilder.group({
      username:['',Validators.compose([
        Validators.required,
        CustomValidator2.patternValidator(/\d/,{ hasNumber: true }),
        CustomValidators.patternValidator(/^([A-Z][a-z]((\s[A-Za-z])?[a-z])*)$/,{ hasCapitalCase: true}),
        CustomValidator2.patternValidator(/[?=.*!@#$%^&*()]/,{ hasSpecialCharacters: true}),
        ])],
      password:['',Validators.compose([
        Validators.required,
        CustomValidators.patternValidator(/\d/,{ hasNumber: true }),
        CustomValidators.patternValidator(/[A-Z]/,{ hasCapitalCase: true}),
        CustomValidators.patternValidator(/[a-z]/, { hasSmallCase: true}),
        CustomValidators.patternValidator(/[?=.*!@#$%^&*()]/,{ hasSpecialCharacters: true}),
        Validators.minLength(8)])],
      phoneNumber:['',Validators.compose([
        Validators.required,
        Validators.maxLength(10),
        CustomValidators.patternValidator(/[6-9][0-9]{9}/,{ hasPattern: true }),
        CustomValidator2.patternValidator(/[A-Za-z]/,{ hasLetters: true}),
        CustomValidator2.patternValidator(/[?=.*!@#$%^&*()]/,{ hasSpecialCharacters: true}),
        ])]
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
        this.transaction1();
            //alert("Account Number generated as: "+this.message);
            
            //this.router.navigate(['new-login']);
      },
      err => 
      { console.log(err.stack);
      });
    }
  }

  transaction1(){
    this.walletService.getUserById(this.message).subscribe(data => {
      this.result=data;
      console.log(this.result);
    },
    err =>{
      console.log(err.stack);
    })
  }

  do(){
    this.router.navigate(['new-login']);
  }

  onSubmit(){ 
    this.save();
  } 

}
