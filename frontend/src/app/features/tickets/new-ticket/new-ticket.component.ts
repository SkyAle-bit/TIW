import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

import { TicketService } from '../../../core/services/ticket.service';

import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';

@Component({
  selector: 'app-new-ticket',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule
  ],
  templateUrl: './new-ticket.component.html',
  styleUrls: ['./new-ticket.component.css']
})
export class NewTicketComponent {
  ticketForm: FormGroup;
  errorMessage: string | null = null;
  categories: string[] = ['TECHNICAL', 'BILLING', 'GENERAL', 'OTHER'];

  private fb = inject(FormBuilder);
  private ticketService = inject(TicketService);
  private router = inject(Router);

  constructor() {
    this.ticketForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      category: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.ticketForm.invalid) {
      return;
    }

    this.ticketService.createTicket(this.ticketForm.value).subscribe({
      next: () => {
        this.router.navigate(['/tickets']);
      },
      error: (err) => {
        this.errorMessage = 'Failed to create ticket. Please try again.';
        console.error(err);
      }
    });
  }
}
