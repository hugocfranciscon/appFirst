import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastService } from '../../services/toast.service';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-ticket',
  templateUrl: './ticket.component.html',
  styleUrl: './ticket.component.scss'
})
export class TicketComponent implements OnInit {

  public subject: string = "";
  public description: string = "";

  constructor(
    private router: Router, 
    private toast: ToastService,
    private http: HttpClient
  ) {}

  ngOnInit(): void {      
  }

  cancel() {
    this.router.navigate(['/tickets']);
  }

  newTicket() {

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
        if (response.status === 200) {
          if (response.body.status == 'ok') {
            this.toast.success("Chamado cadastrado com sucesso.");
            this.router.navigate(['/tickets']);
          }
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

}
