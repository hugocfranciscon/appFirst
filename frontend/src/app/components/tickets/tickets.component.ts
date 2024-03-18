import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { NgbRatingConfig } from '@ng-bootstrap/ng-bootstrap';
import { ToastService } from '../../services/toast.service';

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
  public totalElements: number = 0;
  public totalPages: number = 0;

  constructor(
    private router: Router, 
    private http: HttpClient,
    private toast: ToastService
  ) {}

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

  loadTickets(page: number = 0, size: number = 10) {
    const userId = sessionStorage.getItem('userId');
    const url = `${environment.url}api/tickets/findByUser/${userId}?page=${page}&size=${size}`;    
    this.http.get(url).subscribe({
      next: (response: any) => {        
        this.tickets = response.content;
        this.totalPages = response.totalPages;
        this.totalElements = response.totalElements;
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

  updateTicketRating(ticket: any, rating: number, ratingDescription: string = ""): void {
    const url = `${environment.url}api/tickets/${ticket.id}/rating`;
    const body = { rating, ratingDescription };  
    this.http.patch(url, body).subscribe({
      next: (response: any) => {      
        this.toast.success('Ticket atualizado com sucesso', response);      
      },
      error: (error) => {      
        if (error.status === 403) {
          this.toast.danger('Não autorizado');
          this.removeCredentials();
        } else {        
          this.toast.danger('Erro ao atualizar o ticket', error);
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
