import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  {
    path: 'login',
    loadComponent: () => import('./features/auth/login/login.component').then(m => m.LoginComponent)
  },
  {
    path: 'register',
    loadComponent: () => import('./features/auth/register/register.component').then(m => m.RegisterComponent)
  },
  {
    path: 'tickets',
    loadComponent: () => import('./features/tickets/ticket-list/ticket-list.component').then(m => m.TicketListComponent),
    canActivate: [authGuard]
  },
  {
    path: 'tickets/new',
    loadComponent: () => import('./features/tickets/new-ticket/new-ticket.component').then(m => m.NewTicketComponent),
    canActivate: [authGuard]
  },
  {
    path: 'tickets/:id',
    loadComponent: () => import('./features/tickets/ticket-detail/ticket-detail.component').then(m => m.TicketDetailComponent),
    canActivate: [authGuard]
  },
  {
    path: 'operator/dashboard',
    loadComponent: () => import('./features/operator/operator-dashboard/operator-dashboard.component').then(m => m.OperatorDashboardComponent),
    canActivate: [authGuard],
    data: { roles: ['OPERATOR'] }
  },
  {
    path: 'operator/tickets',
    loadComponent: () => import('./features/operator/all-tickets/all-tickets.component').then(m => m.AllTicketsComponent),
    canActivate: [authGuard],
    data: { roles: ['OPERATOR'] }
  }
];
