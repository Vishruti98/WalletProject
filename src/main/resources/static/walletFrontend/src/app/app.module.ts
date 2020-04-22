import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {RouterModule} from '@angular/router';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { SignupComponent } from './signup/signup.component';
import { LoginComponent } from './login/login.component';
import { OptionsComponent } from './options/options.component';
import { DepositComponent } from './deposit/deposit.component';
import { WithdrawComponent } from './withdraw/withdraw.component';
import { TransferComponent } from './transfer/transfer.component';
import { BalanceComponent } from './balance/balance.component';
import { TransactionComponent } from './transaction/transaction.component';
import { HttpClientModule } from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    SignupComponent,
    LoginComponent,
    OptionsComponent,
    DepositComponent,
    WithdrawComponent,
    TransferComponent,
    BalanceComponent,
    TransactionComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot([
      {
        path: '',
        component:HomeComponent
     },
      {
         path: 'new-signup',
         component:SignupComponent
      },
      {
        path: 'new-login',
        component:LoginComponent
      },
      {
      path: 'new-options',
      component:OptionsComponent
      },
      {
      path: 'new-deposit',
     component:DepositComponent
      },
      {
       path: 'new-withdraw',
       component:WithdrawComponent
      },
      {
      path: 'new-transfer',
      component:TransferComponent
      },
      {
        path: 'new-balance',
        component:BalanceComponent
      },
      {
        path: 'new-transaction',
        component:TransactionComponent
      }
   ]
   )
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
