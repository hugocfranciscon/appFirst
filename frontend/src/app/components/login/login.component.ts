import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements OnInit {

  public user: string = "";
  public pass: string = "";

  constructor(private router: Router, private http: HttpClient) { }

  ngOnInit(): void {
  }

  login() {
    const url = 'http://localhost:8080/auth/token';
    const body = {
      user: this.user,
      pass: this.pass,
      scope: ['GUEST']
    };

    this.http.post(url, body, {observe: 'response'}).subscribe({
      next: (response: any) => {
        if (response.status === 200) {
          const token = response.body.token;
          console.log(token);
          sessionStorage.setItem('token', token);
          this.router.navigate(['/tickets']);
        }
      },
      error: (error) => {
        if (error.status === 403) {
          alert('Não autorizado');
        } else {
          console.error(error);
        }
      }
    });
  }
}
