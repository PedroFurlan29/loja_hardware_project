import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authGuard: CanActivateFn = () => {
  const token = typeof window !== 'undefined' ? localStorage.getItem('token') : null;
  if (token) return true;
  return inject(Router).createUrlTree(['/login']);
};
