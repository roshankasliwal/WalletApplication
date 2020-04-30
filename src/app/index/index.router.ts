import { LoginGuard } from './../guards/login.guard';
import { ForgotComponent } from './forgot/forgot.component';
import { SignupComponent } from './signup/signup.component';
import { LoginComponent } from './login/login.component';
import { IndexComponent } from './index.component';
import { Component } from '@angular/core';
import {Route} from '@angular/router'

export const IndexRoutes : Route [] = [
    {
        path: '' ,
        component : IndexComponent,
        canActivate:[LoginGuard],
        children: [
            {path: 'login',component:LoginComponent},
            {path:'signup',component:SignupComponent},
            {path:'forgot',component:ForgotComponent }
        ]
    }
]