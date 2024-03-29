import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { WalletService } from '../wallet.service';
import { Router } from '@angular/router';
import { CustomValidators } from '../custom-validators';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  submitted: boolean=false;
  loginForm: FormGroup;
  msg1:any;
  message:string;
  invalidLogin: boolean=false;
  errormsg:string;
  constructor(private formBuilder: FormBuilder,private walletService:WalletService,private router: Router) { }


  ngOnInit() {
    if(localStorage.username || localStorage.password || localStorage.accountNumber){
      localStorage.removeItem("username");
      localStorage.removeItem("password");
      localStorage.removeItem("accountNumber");
    }
    this.loginForm=this.formBuilder.group({
      accountNumber:['',[Validators.required,Validators.pattern('.{5,}'),Validators.min(1)]],
      password:['',Validators.compose([
        Validators.required,
        CustomValidators.patternValidator(/\d/,{ hasNumber: true }),
        CustomValidators.patternValidator(/[A-Z]/,{ hasCapitalCase: true}),
        CustomValidators.patternValidator(/[a-z]/, { hasSmallCase: true}),
        CustomValidators.patternValidator(/[?=.*!@#$%^&*()]/,{ hasSpecialCharacters: true}),
        Validators.minLength(8)])]
    });
  }

  login(){
    this.submitted=true;
    if(this.loginForm.invalid){
      return;
    }

    let accountNumber=this.loginForm.controls.accountNumber.value;
    let password=this.loginForm.controls.password.value;
    localStorage.accountNumber=accountNumber;
    localStorage.password=password;

    this.walletService.validate(password,accountNumber).subscribe(data => {
      this.msg1=data;
      if(this.msg1)
      {
        localStorage.accountNumber=accountNumber;
        this.router.navigate(['new-options']);
      }
      else{
        this.invalidLogin=true;
      }
    },
    err => {
      this.errormsg=err.error;
      alert(this.errormsg);
    });
  }

}
