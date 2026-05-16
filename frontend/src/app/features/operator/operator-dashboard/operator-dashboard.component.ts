import { Component, OnInit, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { OperatorService, DashboardResponse } from '../../../core/services/operator.service';

import { MatCardModule } from '@angular/material/card';

@Component({
  selector: 'app-operator-dashboard',
  standalone: true,
  imports: [CommonModule, MatCardModule],
  templateUrl: './operator-dashboard.component.html',
  styleUrls: ['./operator-dashboard.component.css']
})
export class OperatorDashboardComponent implements OnInit {
  stats: DashboardResponse['ticketsByStatus'] = {};

  private operatorService = inject(OperatorService);

  ngOnInit(): void {
    this.operatorService.getDashboardStats().subscribe({
      next: (response) => {
        this.stats = response.ticketsByStatus;
      },
      error: (err) => console.error('Failed to load dashboard stats', err)
    });
  }

  get keys(): string[] {
    return Object.keys(this.stats);
  }
}
