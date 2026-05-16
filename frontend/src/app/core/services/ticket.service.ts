import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface TicketCreateRequest {
  title: string;
  description: string;
  category: string;
}

export interface TicketResponse {
  id: number;
  title: string;
  description: string;
  category: string;
  priority: string;
  status: string;
  createdAt: string;
  createdBy: string;
}

export interface CommentRequest {
  text: string;
}

export interface CommentResponse {
  id: number;
  text: string;
  author: string;
  createdAt: string;
}

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  private readonly apiUrl = 'http://localhost:8080/api/tickets';

  constructor(private http: HttpClient) { }

  getMyTickets(): Observable<TicketResponse[]> {
    return this.http.get<TicketResponse[]>(this.apiUrl);
  }

  getTicketById(id: number): Observable<TicketResponse> {
    return this.http.get<TicketResponse>(`${this.apiUrl}/${id}`);
  }

  createTicket(data: TicketCreateRequest): Observable<TicketResponse> {
    return this.http.post<TicketResponse>(this.apiUrl, data);
  }

  addComment(ticketId: number, text: string): Observable<CommentResponse> {
    return this.http.post<CommentResponse>(`${this.apiUrl}/${ticketId}/comments`, { text });
  }

  updateTicketRating(ticketId: number, rating: string): Observable<TicketResponse> {
    return this.http.patch<TicketResponse>(`${this.apiUrl}/${ticketId}/rating`, { rating });
  }
}
