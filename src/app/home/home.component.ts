import { AuthService } from './../services/auth.service';
import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  
  constructor(public authService:AuthService,public router:Router) { }

  ngOnInit(): void {
    
  }
  logoutAction(){
    if(this.authService.logout())
    {
      this.router.navigate(['/login']);
    }
  }
}
