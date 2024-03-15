import { Component, OnInit } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';
import { UserService } from './user-service.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})

export class AppComponent implements OnInit {
  
  public isLoggedIn = false;

  constructor(private oauthService: OAuthService, private userService: UserService) { 
    this.oauthService.configure({
      issuer: 'https://accounts.google.com',
      redirectUri: 'http://localhost:4200',
      clientId: '1090977595223-fh191rof05is6b4s9dbe3at3rcpp038u.apps.googleusercontent.com',
      scope: 'openid profile email',
      strictDiscoveryDocumentValidation: false,
    });
    this.oauthService.loadDiscoveryDocumentAndTryLogin();        
  }
 
  ngOnInit() {
  }

  login() {
    this.oauthService.initImplicitFlow();
  } 

  consultar() {
    this.userService.getUsers().subscribe(data => {
      console.log(data)
    }, error => {
      console.error('There was an error!', error);
    });
  }

}
