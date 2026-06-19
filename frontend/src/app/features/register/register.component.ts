import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { ApiService } from '../../core/services/api.service';
import { ToastService } from '../../shared/services/toast.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  template: `
    <div class="bg-ck-bg min-h-[80vh] flex items-center justify-center px-4">
      <div class="w-full max-w-[380px]">

        <div class="text-center mb-8">
          <a routerLink="/" class="inline-flex items-center gap-2 hover:opacity-80 transition mb-6">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 500 500" width="22" height="22">
              <defs><linearGradient id="rGrad" x1="0%" y1="0%" x2="100%" y2="100%">
                <stop offset="0%" style="stop-color:#0ea5e9;stop-opacity:1" />
                <stop offset="100%" style="stop-color:#2563eb;stop-opacity:1" />
              </linearGradient></defs>
              <rect x="125" y="100" width="250" height="250" rx="30" fill="none" stroke="url(#rGrad)" stroke-width="20"/>
              <rect x="175" y="150" width="150" height="150" rx="15" fill="url(#rGrad)"/>
            </svg>
            <span class="text-white font-bold text-lg">Hardware<span class="text-ck-accent">Store</span></span>
          </a>
          <h1 class="text-xl font-bold text-white uppercase">Criar Conta</h1>
          <p class="text-xs text-ck-muted mt-1">Cadastre-se para fazer pedidos</p>
        </div>

        <div class="bg-ck-surface border border-ck-border rounded-md p-6 shadow-xl">
          <form (ngSubmit)="onSubmit()" class="space-y-4">

            <div *ngIf="errorMessage" class="flex items-center gap-2 p-3 bg-red-900/30 border border-red-800 rounded text-red-400 text-xs font-medium">
              <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="10"/><line x1="12" y1="8" x2="12" y2="12"/><line x1="12" y1="16" x2="12.01" y2="16"/></svg>
              {{ errorMessage }}
            </div>

            <div *ngIf="successMessage" class="flex items-center gap-2 p-3 bg-green-900/30 border border-green-800 rounded text-green-400 text-xs font-medium">
              <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><polyline points="20 6 9 17 4 12"/></svg>
              {{ successMessage }}
            </div>

            <div>
              <label class="block text-[11px] text-ck-muted uppercase font-semibold mb-1">Nome Completo</label>
              <input type="text" [(ngModel)]="nome" name="nome" required
                class="w-full px-3 py-2.5 text-sm bg-[#111] border border-ck-border text-ck-text placeholder-ck-muted rounded focus:outline-none focus:border-ck-accent transition-colors"
                placeholder="Seu nome" />
            </div>

            <div>
              <label class="block text-[11px] text-ck-muted uppercase font-semibold mb-1">E-mail</label>
              <input type="email" [(ngModel)]="email" name="email" required
                class="w-full px-3 py-2.5 text-sm bg-[#111] border border-ck-border text-ck-text placeholder-ck-muted rounded focus:outline-none focus:border-ck-accent transition-colors"
                placeholder="seu@email.com" />
            </div>

            <div>
              <label class="block text-[11px] text-ck-muted uppercase font-semibold mb-1">Senha</label>
              <input type="password" [(ngModel)]="senha" name="senha" required minlength="6"
                class="w-full px-3 py-2.5 text-sm bg-[#111] border border-ck-border text-ck-text placeholder-ck-muted rounded focus:outline-none focus:border-ck-accent transition-colors"
                placeholder="Mínimo 6 caracteres" />
            </div>

            <button type="submit" [disabled]="isLoading || !nome || !email || !senha"
              class="w-full py-2.5 mt-2 bg-ck-accent hover:bg-ck-accentHover text-white font-bold rounded uppercase tracking-wide text-sm transition-colors disabled:opacity-40">
              {{ isLoading ? 'Criando...' : 'Criar Conta' }}
            </button>
          </form>

          <div class="mt-5 pt-5 border-t border-ck-border text-center">
            <p class="text-xs text-ck-muted">
              Já tem conta?
              <a routerLink="/login" class="text-ck-accent hover:text-white font-semibold transition-colors">Entrar</a>
            </p>
          </div>
        </div>
      </div>
    </div>
  `
})
export class RegisterComponent {
  nome = '';
  email = '';
  senha = '';
  isLoading = false;
  errorMessage = '';
  successMessage = '';

  constructor(
    private apiService: ApiService,
    private toast: ToastService,
    private router: Router
  ) {}

  onSubmit() {
    this.isLoading = true;
    this.errorMessage = '';
    this.successMessage = '';

    this.apiService.registrar(this.nome, this.email, this.senha).subscribe({
      next: () => {
        this.successMessage = 'Conta criada com sucesso! Redirecionando...';
        this.isLoading = false;
        setTimeout(() => this.router.navigate(['/login']), 2000);
      },
      error: (err: any) => {
        this.errorMessage = err.error?.error || 'Erro ao criar conta. Tente novamente.';
        this.isLoading = false;
      }
    });
  }
}
