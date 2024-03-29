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


  save(){
    this.submitted=true;
    if(this.createForm.invalid)
    {
      return;
    }
    else{
      this.check=true;
    this.walletService.create(this.createForm.value).subscribe(data => 
      {
        
        this.message=data;
        this.transaction1();
          
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

  //on submitting the form this method is called
  onSubmit(){ 
    this.save();
  } 

}
