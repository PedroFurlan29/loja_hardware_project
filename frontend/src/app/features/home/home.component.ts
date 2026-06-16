import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ApiService } from '../../core/services/api.service';
import { CartService } from '../../core/services/cart.service';
import { ToastService } from '../../shared/services/toast.service';
import { map } from 'rxjs';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <div class="bg-ck-bg min-h-screen animate-fade-in">

      <!-- HERO BANNER -->
      <section class="w-full bg-gradient-to-r from-[#0a0a1a] via-[#0d1225] to-[#091020] border-b border-ck-border relative overflow-hidden">
        <div class="max-w-[1400px] mx-auto px-4 py-16 flex flex-col md:flex-row items-center gap-12">
          <div class="flex-1 z-10">
            <span class="inline-flex items-center gap-2 text-xs font-bold tracking-widest uppercase text-ck-accent bg-ck-accent/10 border border-ck-accent/30 rounded px-3 py-1 mb-6">
              <svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" viewBox="0 0 24 24" fill="currentColor"><path d="M13 2L3 14h9l-1 8 10-12h-9l1-8z"/></svg>
              Novos Produtos Chegaram
            </span>
            <h1 class="text-4xl md:text-6xl font-bold text-white leading-tight mb-6">
              Hardware de<br/>
              <span class="text-ck-price">Alta Performance</span>
            </h1>
            <p class="text-ck-muted text-lg mb-8 max-w-md">
              CPUs, GPUs, memórias e armazenamento de última geração. Os melhores preços do Brasil.
            </p>
            <div class="flex gap-4">
              <a routerLink="/produtos" class="px-8 py-3 bg-ck-accent hover:bg-ck-accentHover text-white font-semibold rounded text-sm uppercase tracking-wide transition-colors">
                Ver Catálogo
              </a>
              <a routerLink="/produtos" class="px-8 py-3 border border-ck-border hover:border-ck-accent text-ck-muted hover:text-white rounded text-sm uppercase tracking-wide transition-colors">
                Ofertas
              </a>
            </div>
          </div>
          <!-- Hero visual -->
          <div class="hidden md:flex flex-1 justify-center items-center relative">
            <div class="w-64 h-64 rounded-full bg-ck-accent/5 border border-ck-accent/20 flex items-center justify-center">
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="#2563eb" stroke-width="0.8" class="w-32 h-32 opacity-60"><rect width="18" height="18" x="3" y="3" rx="2"/><path d="M3 9h18"/><path d="M9 21V9"/><path d="m14 14 3-3-3-3"/></svg>
            </div>
          </div>
        </div>
        <!-- Decorative line -->
        <div class="absolute bottom-0 left-0 right-0 h-px bg-gradient-to-r from-transparent via-ck-accent/50 to-transparent"></div>
      </section>

      <!-- PRODUCTS SECTION -->
      <section class="max-w-[1400px] mx-auto px-4 py-10">

        <div class="flex items-center justify-between mb-6">
          <div class="flex items-center gap-3">
            <div class="w-1 h-6 bg-ck-accent rounded-full"></div>
            <h2 class="text-xl font-bold text-white">Mais Vendidos</h2>
          </div>
          <a routerLink="/produtos" class="text-xs text-ck-accent hover:text-white font-semibold uppercase tracking-wide flex items-center gap-1 transition-colors">
            Ver todos <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="m9 18 6-6-6-6"/></svg>
          </a>
        </div>

        <!-- Skeleton loading -->
        <div *ngIf="loading" class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-4">
          <div *ngFor="let i of [1,2,3,4,5]" class="bg-ck-surface border border-ck-border rounded overflow-hidden">
            <div class="skeleton aspect-square"></div>
            <div class="p-3 space-y-2">
              <div class="skeleton h-4 w-full rounded"></div>
              <div class="skeleton h-3 w-2/3 rounded"></div>
              <div class="skeleton h-6 w-1/2 rounded mt-3"></div>
              <div class="skeleton h-8 w-full rounded mt-2"></div>
            </div>
          </div>
        </div>

        <!-- Product grid -->
        <div *ngIf="!loading" class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-5 gap-4">
          <div
            *ngFor="let produto of produtos"
            class="product-card flex flex-col bg-ck-surface border border-ck-border rounded overflow-hidden cursor-pointer"
            [routerLink]="['/produtos', produto.id]"
          >
            <!-- Image -->
            <div class="aspect-square bg-[#111] p-4 flex items-center justify-center relative overflow-hidden">
              <img
                *ngIf="produto.imagemUrl"
                [src]="produto.imagemUrl"
                [alt]="produto.descricao"
                class="w-full h-full object-contain mix-blend-luminosity group-hover:scale-105 transition-transform"
                (error)="onImgError($event)"
              />
              <svg *ngIf="!produto.imagemUrl" class="w-16 h-16 opacity-10" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1"><rect width="18" height="18" x="3" y="3" rx="2"/><path d="M3 9h18"/><path d="M9 21V9"/><path d="m14 14 3-3-3-3"/></svg>

              <!-- Badge -->
              <div *ngIf="isNovo(produto)" class="absolute top-2 left-2 bg-green-700 text-white text-[10px] font-bold px-1.5 py-0.5 rounded">NOVO</div>
              <div *ngIf="isEstoqueCritico(produto)" class="absolute top-2 right-2 bg-amber-700 text-white text-[10px] font-bold px-1.5 py-0.5 rounded">Últimas unidades</div>
            </div>

            <!-- Info -->
            <div class="p-3 flex flex-col flex-1">
              <h3 class="text-xs text-ck-text font-medium line-clamp-3 min-h-[4rem] mb-3 leading-snug">
                {{ produto.descricao }}
              </h3>

              <div class="mt-auto">
                <p class="text-xl font-bold text-ck-price">
                  R$ {{ produto.precoVenda | number:'1.2-2':'pt-BR' }}
                </p>
                <p class="text-[11px] text-ck-muted mt-0.5">em até 12x no cartão</p>

                <button
                  (click)="addToCart(produto, $event)"
                  class="mt-3 w-full py-2 bg-ck-accent hover:bg-ck-accentHover text-white text-xs font-semibold rounded uppercase tracking-wide transition-colors flex items-center justify-center gap-2"
                >
                  <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="8" cy="21" r="1"/><circle cx="19" cy="21" r="1"/><path d="M2.05 2.05h2l2.66 12.42a2 2 0 0 0 2 1.58h9.78a2 2 0 0 0 1.95-1.57l1.65-7.43H5.12"/></svg>
                  Adicionar
                </button>
              </div>
            </div>
          </div>
        </div>

      </section>
    </div>
  `,
})
export class HomeComponent implements OnInit {
  produtos: any[] = [];
  loading = true;

  constructor(
    private apiService: ApiService,
    private cartService: CartService,
    private toast: ToastService
  ) {}

  ngOnInit() {
    console.log('HOME INIT');
  
    this.apiService.getProdutos(0, 10).subscribe({
      next: (res) => {
        console.log('NEXT EXECUTOU');
        console.log(res);
  
        this.produtos = res.content || [];
        this.loading = false;
      },
      error: (err) => {
        console.log('ERROR EXECUTOU');
        console.log(err);
  
        this.loading = false;
      },
      complete: () => {
        console.log('COMPLETE EXECUTOU');
      }
    });
  }

  isNovo(produto: any): boolean {
    if (!produto.createdAt) return false;
    const dias = (Date.now() - new Date(produto.createdAt).getTime()) / 86400000;
    return dias <= 30;
  }

  isEstoqueCritico(produto: any): boolean {
    return produto.quantidade != null && produto.estoque_minimo != null
      && produto.quantidade <= produto.estoque_minimo;
  }

  addToCart(produto: any, event: Event) {
    event.stopPropagation();
    event.preventDefault();
    this.cartService.addToCart({
      produtoId: produto.id,
      nome: produto.descricao,
      preco: parseFloat(produto.precoVenda),
      quantidade: 1,
    });
    this.toast.success(`"${produto.descricao.substring(0, 30)}..." adicionado ao carrinho!`);
  }

  onImgError(event: Event) {
    (event.target as HTMLImageElement).style.display = 'none';
  }
}