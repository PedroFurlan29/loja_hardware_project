import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { ApiService } from '../../core/services/api.service';
import { AuthService } from '../../core/services/auth.service';
import { ToastService } from '../../shared/services/toast.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  template: `
    <div class="bg-ck-bg min-h-[80vh] flex items-center justify-center px-4">
      <div class="w-full max-w-[380px]">

        <div class="text-center mb-8">
          <a routerLink="/" class="inline-flex items-center gap-2 hover:opacity-80 transition mb-6">
            <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="#2563eb" stroke-width="2.5"><rect width="18" height="18" x="3" y="3" rx="2"/><path d="M3 9h18"/><path d="M9 21V9"/><path d="m14 14 3-3-3-3"/></svg>
            <span class="text-white font-bold text-lg">Hardware<span class="text-ck-accent">Store</span></span>
          </a>
          <h1 class="text-xl font-bold text-white uppercase">Entrar na Conta</h1>
          <p class="text-xs text-ck-muted mt-1">Acesse para gerenciar seus pedidos</p>
        </div>

        <div class="bg-ck-surface border border-ck-border rounded-md p-6 shadow-xl">
          <form (ngSubmit)="onSubmit()" class="space-y-4">

            <div *ngIf="errorMessage" class="flex items-center gap-2 p-3 bg-red-900/30 border border-red-800 rounded text-red-400 text-xs font-medium">
              <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="10"/><line x1="12" y1="8" x2="12" y2="12"/><line x1="12" y1="16" x2="12.01" y2="16"/></svg>
              {{ errorMessage }}
            </div>

            <div>
              <label class="block text-[11px] text-ck-muted uppercase font-semibold mb-1">E-mail</label>
              <input type="email" [(ngModel)]="email" name="email" required
                class="w-full px-3 py-2.5 text-sm bg-[#111] border border-ck-border text-ck-text placeholder-ck-muted rounded focus:outline-none focus:border-ck-accent transition-colors"
                placeholder="seu@email.com" />
            </div>

            <div>
              <div class="flex justify-between mb-1">
                <label class="text-[11px] text-ck-muted uppercase font-semibold">Senha</label>
              </div>
              <input type="password" [(ngModel)]="senha" name="senha" required
                class="w-full px-3 py-2.5 text-sm bg-[#111] border border-ck-border text-ck-text placeholder-ck-muted rounded focus:outline-none focus:border-ck-accent transition-colors"
                placeholder="••••••••" />
            </div>

            <button type="submit" [disabled]="isLoading || !email || !senha"
              class="w-full py-2.5 mt-2 bg-ck-accent hover:bg-ck-accentHover text-white font-bold rounded uppercase tracking-wide text-sm transition-colors disabled:opacity-40">
              {{ isLoading ? 'Entrando...' : 'Entrar' }}
            </button>
          </form>

          <!-- Quick login cards -->
          <div class="mt-5 pt-5 border-t border-ck-border">
            <p class="text-[10px] text-ck-muted uppercase font-semibold mb-3 text-center">Login Rápido (Demo)</p>
            <div class="grid grid-cols-3 gap-2">
              <button *ngFor="let u of demoUsers" (click)="quickLogin(u.email, u.senha)"
                class="flex flex-col items-center p-2 bg-[#111] border border-ck-border hover:border-ck-accent rounded text-center transition-colors">
                <div class="w-6 h-6 rounded-full bg-ck-accent/20 text-ck-accent flex items-center justify-center text-xs font-bold mb-1">
                  {{ u.label.charAt(0) }}
                </div>
                <span class="text-[9px] text-ck-muted">{{ u.label }}</span>
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  `,
})
export class LoginComponent {
  email = '';
  senha = '';
  isLoading = false;
  errorMessage = '';

  demoUsers = [
    { label: 'Admin', email: 'admin@loja.com', senha: 'admin123' },
    { label: 'Vendedor', email: 'vendedor@loja.com', senha: 'vendedor123' },
    { label: 'Estoquista', email: 'estoquista@loja.com', senha: 'estoquista123' },
  ];

  constructor(
    private apiService: ApiService,
    private authService: AuthService,
    private toast: ToastService,
    private router: Router
  ) {}

  quickLogin(email: string, senha: string) {
    this.email = email;
    this.senha = senha;
    this.onSubmit();
  }

  onSubmit() {
    this.isLoading = true;
    this.errorMessage = '';

    this.apiService.login(this.email, this.senha).subscribe({
      next: (res: any) => {
        if (typeof window !== 'undefined') localStorage.setItem('token', res.token);
        // Fetch user info after login
        this.apiService.getMe().subscribe({
          next: (user: any) => {
            this.authService.setUser(user);
            this.toast.success(`Bem-vindo, ${user.nome}! (${user.perfil})`);
            this.isLoading = false;
            // Redirect based on role
            if (user.perfil === 'ADMIN') {
              this.router.navigate(['/admin']);
            } else {
              this.router.navigate(['/']);
            }
          },
          error: () => {
            this.toast.success('Login realizado!');
            this.isLoading = false;
            this.router.navigate(['/']);
          }
        });
      },
      error: () => {
        this.errorMessage = 'E-mail ou senha incorretos.';
        this.isLoading = false;
      }
    });
  }
}
