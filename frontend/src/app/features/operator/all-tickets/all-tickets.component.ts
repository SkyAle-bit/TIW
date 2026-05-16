import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { OperatorService } from '../../../core/services/operator.service';
import { TicketResponse } from '../../../core/services/ticket.service';

import { MatTableModule } from '@angular/material/table';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatChipsModule } from '@angular/material/chips';

@Component({
  selector: 'app-all-tickets',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule,
    MatTableModule,
    MatSelectModule,
    MatFormFieldModule,
    MatButtonModule,
    MatIconModule,
    MatChipsModule
  ],
  templateUrl: './all-tickets.component.html',
  styleUrls: ['./all-tickets.component.css']
})
export class AllTicketsComponent implements OnInit {
  tickets: TicketResponse[] = [];
  displayedColumns: string[] = ['id', 'title', 'category', 'status', 'priority', 'actions'];

  filterForm: FormGroup;

  statuses = ['OPEN', 'IN_PROGRESS', 'RESOLVED', 'CLOSED'];
  priorities = ['LOW', 'MEDIUM', 'HIGH', 'URGENT'];
  categories = ['TECHNICAL', 'BILLING', 'GENERAL', 'OTHER'];

  private operatorService = inject(OperatorService);
  private fb = inject(FormBuilder);

  constructor() {
    this.filterForm = this.fb.group({
      status: [''],
      priority: [''],
      category: ['']
    });
  }

  ngOnInit(): void {
    this.loadTickets();

    this.filterForm.valueChanges.subscribe(() => {
      this.loadTickets();
    });
  }

  loadTickets(): void {
    const { status, priority, category } = this.filterForm.value;
    this.operatorService.getAllTickets(status, priority, category).subscribe({
      next: (data) => this.tickets = data,
      error: (err) => console.error('Error fetching all tickets', err)
    });
  }

  onStatusChange(ticketId: number, newStatus: string): void {
    this.operatorService.updateTicketStatus(ticketId, newStatus).subscribe({
      next: (updated) => {
        const idx = this.tickets.findIndex(t => t.id === ticketId);
        if (idx !== -1) {
          this.tickets[idx] = updated;
        }
      },
      error: (err) => console.error('Error updating status', err)
    });
  }

  onPriorityChange(ticketId: number, newPriority: string): void {
    this.operatorService.updateTicketPriority(ticketId, newPriority).subscribe({
      next: (updated) => {
        const idx = this.tickets.findIndex(t => t.id === ticketId);
        if (idx !== -1) {
          this.tickets[idx] = updated;
        }
      },
      error: (err) => console.error('Error updating priority', err)
    });
  }

  resetFilters(): void {
    this.filterForm.reset({ status: '', priority: '', category: '' });
  }
}
