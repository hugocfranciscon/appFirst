import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { NgbModal, NgbRatingConfig } from '@ng-bootstrap/ng-bootstrap';
import { ToastService } from '../../services/toast.service';

@Component({
  selector: 'app-tickets',
  templateUrl: './tickets.component.html',
  styleUrl: './tickets.component.scss'
})
export class TicketsComponent implements OnInit {

  @ViewChild('modalCloseTicket') modalCloseTicket: ElementRef | undefined;
  @ViewChild('modalRating') modalRating: ElementRef | undefined;
  @ViewChild('modalNewTicket') modalNewTicket: ElementRef | undefined;
  @ViewChild('modalConsultingTicket') modalConsultingTicket: ElementRef | undefined;
  
  public ticketSelected: any = {};
  public tickets: any = [];
  public typeUser: string = "I";
  public loading: boolean = false;
  public page: number = 1;
  public pageSize: number = 10;
  public totalElements: number = 0;
  public totalPages: number = 0;
  public filter: string = "";
  public closingDescription: string = "";
  public rating: number = 0;
  public ratingDescription: string = "";
  public subject: string = "";
  public description: string = "";
  public idTicket: string = "";

  constructor(
    private router: Router, 
    private http: HttpClient,
    private toast: ToastService,
    public modalService: NgbModal,
    public ngbRatingConfig: NgbRatingConfig
  ) {
    this.ngbRatingConfig.max = 5;
  }

  ngOnInit(): void {
    this.typeUser = window.sessionStorage.getItem('type')!;
    this.loadTickets();
  }

  newTicket() {
    this.subject = "";
    this.description = "";    
    this.modalService.open(this.modalNewTicket);
  }

  exit() {
    if (!confirm("Deseja realmente sair do sistema?")) {
      return;
    }
    this.removeCredentials();
  }  

  loadTickets() {
    const userId = sessionStorage.getItem('userId');
    const filter = encodeURIComponent(this.filter);    
    let url = `${environment.url}api/tickets/findTickets?userId=${userId}&page=${this.page-1}&size=${this.pageSize}&filter=${filter}`;
    if (this.typeUser == "A") {
      url = `${environment.url}api/tickets/findTickets?page=${this.page-1}&size=${this.pageSize}&filter=${filter}`;
    }
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

  confirmRating(): void {

    const url = `${environment.url}api/tickets/rating`;
    let body = { 
      ticketId: this.ticketSelected.id,
      rating: this.rating, 
      ratingDescription: this.ratingDescription
    };

    this.http.post(url, body, {observe: 'response'}).subscribe({
      next: (response: any) => {
        this.toast.success('Ticket atualizado com sucesso', response);
        this.exitModal();
        this.loadTickets();                
      },
      error: (error) => {
        if (error.status === 403) {
          this.toast.danger('Não autorizado');
          this.removeCredentials();
        } else {
          this.toast.danger('Erro ao atualizar o ticket', error.message || error);
        }
      }
    });
  }

  confirmClosing(): void {

    const url = `${environment.url}api/tickets/closing`;
    let body = { 
      ticketId: this.ticketSelected.id,
      userId: sessionStorage.getItem('userId'),
      closingDescription: this.closingDescription
    };

    this.http.post(url, body, {observe: 'response'}).subscribe({
      next: (response: any) => {
        this.toast.success('Ticket atualizado com sucesso', response);
        this.exitModal();
        this.loadTickets();        
      },
      error: (error) => {
        if (error.status === 403) {
          this.toast.danger('Não autorizado');
          this.removeCredentials();
        } else {
          this.toast.danger('Erro ao atualizar o ticket', error.message || error);
        }
      }
    });
  }

  closeTicket(t: any) {
    this.ticketSelected = t;
    this.closingDescription = "";
    this.modalService.open(this.modalCloseTicket);
  }

  ratingTicket(t: any) {
    if (this.typeUser == 'A') {
      return;
    }
    this.ticketSelected = t;
    this.rating = 0;
    this.ratingDescription = "";
    this.modalService.open(this.modalRating);
  }

  removeCredentials() {
    sessionStorage.removeItem('token');
    this.router.navigate(['/login']);
  }

  exitModal() {
    this.modalService.dismissAll();
  }

  confirmTicket() {

    if (this.subject == "") {
      this.toast.danger('Informe o assunto do chamado.');
      return;
    }
    if (this.description == "") {
      this.toast.danger('Informe a descrição do chamado.');
      return;
    }
    const url = environment.url+'api/tickets/create';
    const body = {
      userId: sessionStorage.getItem('userId'),
      subject: this.subject,
      description: this.description,
    };

    this.http.post(url, body, {observe: 'response'}).subscribe({
      next: (response: any) => {        
        if (response.body.status == 'ok') {
          this.toast.success("Chamado cadastrado com sucesso.");
          this.exitModal();
          this.loadTickets();          
        }
      },
      error: (error) => {
        if (error.status === 403) {
          alert('Não autorizado');
          sessionStorage.removeItem('token');
          this.router.navigate(['/login']);
        } else {
          console.error(error);
        }
      }
    });
  }

  consultingTicket(t: any) {
    this.closingDescription = t.closingDescription;
    this.rating             = t.rating;
    this.ratingDescription  = t.ratingDescription; 
    this.subject            = t.subject;
    this.description        = t.description;
    this.idTicket           = t.idTicket;
    this.modalService.open(this.modalConsultingTicket);
  }

}
