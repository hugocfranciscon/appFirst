import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-ticket',
  templateUrl: './ticket.component.html',
  styleUrl: './ticket.component.scss'
})
export class TicketComponent implements OnInit {

  constructor(private router: Router) {

  }

  ngOnInit(): void {
      
  }

  cancel() {
    this.router.navigate(['/tickets']);
  }

}
