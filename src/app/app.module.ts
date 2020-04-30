import { WalletService } from './services/wallet.service';
import { HttpClientModule }    from '@angular/common/http';
import { LoginGuard } from './guards/login.guard';
import { AuthGuard } from './guards/auth.guard';
import { AuthService } from './services/auth.service';
import { routers } from './app.router';
import { RouterModule, Routes } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { IndexComponent } from './index/index.component';
import { HomeComponent } from './home/home.component';
import { NoPageComponent } from './no-page/no-page.component';
import { HomeModule } from './home/home.module';
import { IndexModule } from './index/index.module';

@NgModule({
  declarations: [
    AppComponent,
    IndexComponent,
    HomeComponent,
    NoPageComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HomeModule,
    IndexModule,
    RouterModule.forRoot(routers),
    HttpClientModule
  ],
  providers: [AuthService,WalletService,AuthGuard,LoginGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
