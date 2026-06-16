import { Injectable } from '@angular/core';
import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { AuthService } from '../services/auth.service';

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  intercept(req: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    return next.handle(req).pipe(
      catchError((error: unknown) => {
        if (error instanceof HttpErrorResponse) {
          if (error.status === 401) {
            this.authService.logout();
            this.router.navigate(['/login']);
          }

          const errorMessage =
            error.error?.message ||
            error.error?.erro ||
            error.statusText ||
            'Ocorreu um erro ao processar a requisição.';

          return throwError(() => new Error(errorMessage));
        }

        return throwError(() => new Error('Erro desconhecido.'));
      })
    );
  }
}
