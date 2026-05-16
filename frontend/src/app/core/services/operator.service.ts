import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TicketResponse } from './ticket.service';

export interface DashboardResponse {
  ticketsByStatus: { [key: string]: number };
}

@Injectable({
  providedIn: 'root'
})
export class OperatorService {

  private readonly apiUrl = 'http://localhost:8080/api/operator';

  constructor(private http: HttpClient) { }

  getAllTickets(status?: string, priority?: string, category?: string): Observable<TicketResponse[]> {
    let params = new HttpParams();
    if (status) params = params.set('status', status);
    if (priority) params = params.set('priority', priority);
    if (category) params = params.set('category', category);

    return this.http.get<TicketResponse[]>(`${this.apiUrl}/tickets`, { params });
  }

  getDashboardStats(): Observable<DashboardResponse> {
    return this.http.get<DashboardResponse>(`${this.apiUrl}/dashboard`);
  }

  updateTicketStatus(id: number, status: string): Observable<TicketResponse> {
    return this.http.patch<TicketResponse>(`${this.apiUrl}/tickets/${id}/status`, { status });
  }

  updateTicketPriority(id: number, priority: string): Observable<TicketResponse> {
    return this.http.patch<TicketResponse>(`${this.apiUrl}/tickets/${id}/priority`, { priority });
  }
}
