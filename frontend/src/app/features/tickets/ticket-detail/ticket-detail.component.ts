import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';

import { TicketService, TicketResponse, CommentResponse } from '../../../core/services/ticket.service';

import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { MatDividerModule } from '@angular/material/divider';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSelectModule } from '@angular/material/select';

@Component({
  selector: 'app-ticket-detail',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    ReactiveFormsModule,
    MatCardModule,
    MatChipsModule,
    MatDividerModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatIconModule,
    MatSelectModule
  ],
  templateUrl: './ticket-detail.component.html',
  styleUrls: ['./ticket-detail.component.css']
})
export class TicketDetailComponent implements OnInit {
  ticket: TicketResponse | null = null;
  comments: CommentResponse[] = []; // In a real app we'd fetch this from the backend

  commentForm: FormGroup;
  ratingForm: FormGroup;

  private route = inject(ActivatedRoute);
  private ticketService = inject(TicketService);
  private fb = inject(FormBuilder);

  constructor() {
    this.commentForm = this.fb.group({
      text: ['', Validators.required]
    });

    this.ratingForm = this.fb.group({
      rating: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.loadTicket(Number(idParam));
    }
  }

  loadTicket(id: number): void {
    this.ticketService.getTicketById(id).subscribe({
      next: (data) => {
        this.ticket = data;
        // The backend doesn't currently return comments with the ticket or have a GET comments endpoint.
        // For level 1, we will just show newly added comments locally.
      },
      error: (err) => console.error('Error fetching ticket', err)
    });
  }

  onAddComment(): void {
    if (this.commentForm.invalid || !this.ticket) return;

    this.ticketService.addComment(this.ticket.id, this.commentForm.value.text).subscribe({
      next: (comment) => {
        this.comments.push(comment);
        this.commentForm.reset();
      },
      error: (err) => console.error('Error adding comment', err)
    });
  }

  onUpdateRating(): void {
    if (this.ratingForm.invalid || !this.ticket) return;

    this.ticketService.updateTicketRating(this.ticket.id, this.ratingForm.value.rating).subscribe({
      next: (updatedTicket) => {
        this.ticket = updatedTicket;
      },
      error: (err) => console.error('Error updating rating', err)
    });
  }
}
