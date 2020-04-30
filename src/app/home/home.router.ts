import { AuthGuard } from './../guards/auth.guard';
import { TransactionsComponent } from './transactions/transactions.component';
import { BalanceComponent } from './balance/balance.component';
import { FundtransferComponent } from './fundtransfer/fundtransfer.component';
import { WithdrawComponent } from './withdraw/withdraw.component';
import { DepositComponent } from './deposit/deposit.component';
import { ProfileComponent } from './profile/profile.component';
import { HomeComponent } from './home.component';
import {Route} from '@angular/router'

export const HomeRoutes : Route [] = [
    {
        path: '' ,
        component : HomeComponent,
        canActivate:[AuthGuard],
        children: [
            {path: '',component:ProfileComponent},
            {path:'deposit',component:DepositComponent},
            {path:'withdraw',component:WithdrawComponent},
            {path:'fundtransfer',component:FundtransferComponent},
            {path:'balance',component:BalanceComponent},
            {path:'transactions',component:TransactionsComponent}
        ]
    }
]