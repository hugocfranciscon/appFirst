import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-tickets',
  templateUrl: './tickets.component.html',
  styleUrl: './tickets.component.scss'
})
export class TicketsComponent implements OnInit {

  public tickets: any = [];
  public typeUser: string = "A";
  public loading: boolean = false;
  public page: number = 1;
  public pageSize: number = 10;
  public totalItens: number = 0;

  constructor(private router: Router, private http: HttpClient) {}

  ngOnInit(): void {
    this.typeUser = window.sessionStorage.getItem('typeUser')!;
    this.loadTickets();
  }

  newTicket() {
    this.router.navigate(['/ticket']);
  }

  exit() {
    if (!confirm("Deseja realmente sair do sistema?")) {
      return;
    }
    this.removeCredentials();
  }

  loadTickets() {
    const url = environment.url+'api/tickets/findByUser/'+sessionStorage.getItem('userId');
    this.http.get(url).subscribe({
      next: (response: any) => {
        if (response.status === 200) {

        }
      },
      error: (error) => {
        if (error.status === 403) {
          alert('Não autorizado');
          this.removeCredentials();
        } else {
          console.error(error);
        }
      }
    });    
  }

  closeTicket(t: any) {

  }

  removeCredentials() {
    sessionStorage.removeItem('token');
    this.router.navigate(['/login']);    
  }
}
