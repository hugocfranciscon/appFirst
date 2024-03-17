import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-tickets',
  templateUrl: './tickets.component.html',
  styleUrl: './tickets.component.scss'
})
export class TicketsComponent implements OnInit {

  public typeUser: string = "A";

  constructor(private router: Router) {

  }

  ngOnInit(): void {
    this.typeUser = window.sessionStorage.getItem('typeUser')!;
  }

  newTicket() {
    this.router.navigate(['/ticket']);
  }

  exit() {
    if (!confirm("Deseja realmente sair do sistema?")) {
      return;
    }
    sessionStorage.removeItem('token');
    this.router.navigate(['/login']);
  }
}
