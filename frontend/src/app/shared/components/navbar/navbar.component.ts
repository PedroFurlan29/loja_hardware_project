import { Component, Output, EventEmitter, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { CartService } from '../../../core/services/cart.service';
import { AuthService } from '../../../core/services/auth.service';
import { map } from 'rxjs';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <header class="sticky top-0 z-50 w-full">

      <!-- Main bar -->
      <div class="bg-[#0a0a0a] border-b border-ck-border shadow-lg">
        <div class="max-w-[1400px] mx-auto px-4 h-16 flex items-center gap-4">

          <!-- Logo -->
          <a routerLink="/" class="flex items-center gap-2 flex-shrink-0 hover:opacity-90 transition">
            <svg xmlns="http://www.w3.org/2000/svg" width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="#2563eb" stroke-width="2.5"><rect width="18" height="18" x="3" y="3" rx="2"/><path d="M3 9h18"/><path d="M9 21V9"/><path d="m14 14 3-3-3-3"/></svg>
            <span class="text-white font-bold text-lg tracking-tight">Hardware<span class="text-ck-accent">Store</span></span>
          </a>

          <!-- Search -->
          <div class="flex-1 max-w-2xl mx-4">
            <div class="relative">
              <input type="text" placeholder="Busque produtos, marcas e categorias..."
                class="w-full h-10 pl-4 pr-12 text-sm bg-[#1a1a1a] border border-ck-border text-ck-text placeholder-ck-muted rounded focus:outline-none focus:border-ck-accent transition-colors"
                (keydown.enter)="router.navigate(['/produtos'])"
              />
              <button (click)="router.navigate(['/produtos'])"
                class="absolute right-0 top-0 h-10 w-12 flex items-center justify-center bg-ck-accent hover:bg-ck-accentHover rounded-r transition-colors">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2.5"><circle cx="11" cy="11" r="8"/><path d="m21 21-4.3-4.3"/></svg>
              </button>
            </div>
          </div>

          <!-- Actions -->
          <div class="flex items-center gap-1 flex-shrink-0">

            <!-- NOT LOGGED IN -->
            <ng-container *ngIf="!authService.isLoggedIn()">
              <a routerLink="/login"
                class="flex items-center gap-1.5 px-3 py-2 text-ck-muted hover:text-white hover:bg-ck-surface rounded transition-colors text-sm">
                <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5"><path d="M19 21v-2a4 4 0 0 0-4-4H9a4 4 0 0 0-4 4v2"/><circle cx="12" cy="7" r="4"/></svg>
                <span class="hidden lg:block font-medium">Entrar</span>
              </a>
            </ng-container>

            <!-- LOGGED IN -->
            <ng-container *ngIf="authService.isLoggedIn()">
              <div class="relative group">
                <button class="flex items-center gap-2 px-3 py-2 text-ck-text hover:bg-ck-surface rounded transition-colors text-sm">
                  <div class="w-7 h-7 rounded-full bg-ck-accent flex items-center justify-center text-white text-xs font-bold">
                    {{ authService.user()?.nome?.charAt(0)?.toUpperCase() }}
                  </div>
                  <div class="hidden lg:block text-left">
                    <p class="text-xs font-semibold leading-tight">{{ authService.user()?.nome }}</p>
                    <p class="text-[10px] text-ck-muted leading-tight">{{ authService.user()?.perfil }}</p>
                  </div>
                  <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" class="hidden lg:block text-ck-muted"><path d="m6 9 6 6 6-6"/></svg>
                </button>
                <!-- Dropdown -->
                <div class="absolute right-0 top-full mt-1 w-48 bg-[#111] border border-ck-border rounded shadow-xl opacity-0 invisible group-hover:opacity-100 group-hover:visible transition-all">
                  <div class="p-3 border-b border-ck-border">
                    <p class="text-xs font-bold text-white">{{ authService.user()?.nome }}</p>
                    <p class="text-[10px] text-ck-muted">{{ authService.user()?.email }}</p>
                  </div>
                  <a *ngIf="authService.isAdmin()" routerLink="/admin"
                    class="flex items-center gap-2 px-3 py-2 text-xs text-ck-muted hover:text-white hover:bg-ck-surface transition-colors">
                    <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect width="20" height="14" x="2" y="7" rx="2"/><path d="M16 21V5a2 2 0 0 0-2-2h-4a2 2 0 0 0-2 2v16"/></svg>
                    Painel Admin
                  </a>
                  <a routerLink="/meus-pedidos"
                    class="flex items-center gap-2 px-3 py-2 text-xs text-ck-muted hover:text-white hover:bg-ck-surface transition-colors">
                    <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><polyline points="14 2 14 8 20 8"/></svg>
                    Meus Pedidos
                  </a>
                  <button (click)="authService.logout()"
                    class="w-full flex items-center gap-2 px-3 py-2 text-xs text-red-400 hover:text-red-300 hover:bg-ck-surface transition-colors">
                    <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/><polyline points="16 17 21 12 16 7"/><line x1="21" y1="12" x2="9" y2="12"/></svg>
                    Sair
                  </button>
                </div>
              </div>
            </ng-container>

            <!-- Cart -->
            <a routerLink="/carrinho"
              class="flex items-center gap-1.5 px-3 py-2 text-ck-muted hover:text-white hover:bg-ck-surface rounded transition-colors relative">
              <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5"><circle cx="8" cy="21" r="1"/><circle cx="19" cy="21" r="1"/><path d="M2.05 2.05h2l2.66 12.42a2 2 0 0 0 2 1.58h9.78a2 2 0 0 0 1.95-1.57l1.65-7.43H5.12"/></svg>
              <span *ngIf="(cartCount$ | async) as count" [hidden]="count === 0"
                class="absolute -top-0.5 right-0 bg-ck-accent text-white text-[10px] font-bold rounded-full w-4 h-4 flex items-center justify-center">{{ count }}</span>
            </a>

          </div>
        </div>
      </div>

      <!-- Category bar -->
      <div class="bg-[#141414] border-b border-ck-border hidden md:block">
  <div class="max-w-[1400px] mx-auto px-4 flex items-center h-10 gap-0 text-xs font-medium text-ck-muted">
    <a routerLink="/produtos" class="px-4 h-full flex items-center hover:text-white hover:bg-ck-surface transition-colors">Todos</a>
    <a routerLink="/produtos" [queryParams]="{categoria:'CPU', label:'Processadores'}" class="px-4 h-full flex items-center hover:text-white hover:bg-ck-surface transition-colors">Processadores</a>
    <a routerLink="/produtos" [queryParams]="{categoria:'GPU', label:'Placas de Vídeo'}" class="px-4 h-full flex items-center hover:text-white hover:bg-ck-surface transition-colors">Placas de Vídeo</a>
    <a routerLink="/produtos" [queryParams]="{categoria:'RAM', label:'Memórias RAM'}" class="px-4 h-full flex items-center hover:text-white hover:bg-ck-surface transition-colors">Memórias</a>
    <a routerLink="/produtos" [queryParams]="{categoria:'SSD', label:'SSDs & HDs'}" class="px-4 h-full flex items-center hover:text-white hover:bg-ck-surface transition-colors">SSDs & HDs</a>
    <a routerLink="/produtos" [queryParams]="{oferta:'true', label:'🔥 Ofertas'}" class="px-4 h-full flex items-center text-yellow-400 hover:text-yellow-300 hover:bg-ck-surface transition-colors font-bold">🔥 Ofertas</a>
          <!-- Admin links - só ADMIN e VENDEDOR veem -->
          <ng-container *ngIf="authService.isLoggedIn()">
            <div class="w-px h-4 bg-ck-border mx-2"></div>
            <a *ngIf="authService.isAdmin()" routerLink="/admin" class="px-3 h-full flex items-center text-ck-accent hover:bg-ck-surface transition-colors">Admin</a>
            <a *ngIf="authService.isVendedor()" routerLink="/admin" [state]="{tab:'vendas'}" class="px-3 h-full flex items-center hover:text-white hover:bg-ck-surface transition-colors">Vendas</a>
            <a *ngIf="authService.isAdmin()" routerLink="/admin" [queryParams]="{tab:'estoque'}" class="px-3 h-full flex items-center hover:text-white hover:bg-ck-surface transition-colors">Estoque</a>
          </ng-container>
        </div>
      </div>

    </header>
  `,
})
export class NavbarComponent {
  @Output() themeToggle = new EventEmitter<void>();
  cartCount$;

  constructor(
    public authService: AuthService,
    private cartService: CartService,
    public router: Router
  ) {
    this.cartCount$ = this.cartService.cart$.pipe(
      map(items => items.reduce((acc, item) => acc + item.quantidade, 0))
    );
  }
}