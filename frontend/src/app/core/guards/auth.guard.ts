import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (!authService.isLoggedIn()) {
    return router.parseUrl('/login');
  }

  // Check required roles if specified in route data
  const requiredRoles = route.data?.['roles'] as string[];
  if (requiredRoles && requiredRoles.length > 0) {
    const userRole = authService.getUserRole();
    if (!userRole || !requiredRoles.includes(userRole)) {
      return router.parseUrl('/unauthorized'); // or redirect to a default home page
    }
  }

  return true;
};
