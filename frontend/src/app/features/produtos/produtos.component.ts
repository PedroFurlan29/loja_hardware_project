import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ApiService } from '../../core/services/api.service';
import { CartService } from '../../core/services/cart.service';
import { ToastService } from '../../shared/services/toast.service';

@Component({
  selector: 'app-produtos',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  template: `
    <div class="bg-ck-bg min-h-screen">
      <div class="max-w-[1400px] mx-auto px-4 py-8">

        <!-- Breadcrumb -->
        <nav class="flex items-center gap-2 text-xs text-ck-muted mb-6 font-medium">
          <a routerLink="/" class="hover:text-white transition-colors">Home</a>
          <svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="m9 18 6-6-6-6"/></svg>
          <span class="text-white">Catálogo</span>
        </nav>

        <div class="flex gap-6">

          <!-- Sidebar -->
          <aside class="hidden lg:block w-56 flex-shrink-0">
            <div class="bg-ck-surface border border-ck-border rounded p-4 sticky top-28">
              <h3 class="text-sm font-bold text-white uppercase tracking-wide mb-4 flex items-center gap-2">
                <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><line x1="4" y1="6" x2="20" y2="6"/><line x1="8" y1="12" x2="16" y2="12"/><line x1="12" y1="18" x2="12" y2="18"/></svg>
                Filtros
              </h3>
              <div class="space-y-4">
                <div>
                  <label class="block text-[11px] text-ck-muted uppercase font-semibold mb-2">Buscar</label>
                  <input
                    [(ngModel)]="searchTerm"
                    (ngModelChange)="onSearch($event)"
                    type="text"
                    placeholder="Nome do produto..."
                    class="w-full px-3 py-2 text-xs bg-[#111] border border-ck-border text-ck-text placeholder-ck-muted rounded focus:outline-none focus:border-ck-accent transition-colors"
                  />
                </div>
                <div>
                  <label class="block text-[11px] text-ck-muted uppercase font-semibold mb-2">Ordenar</label>
                  <select
                    [(ngModel)]="sortBy"
                    (ngModelChange)="onSortChange($event)"
                    class="w-full px-3 py-2 text-xs bg-[#111] border border-ck-border text-ck-text rounded focus:outline-none focus:border-ck-accent transition-colors"
                  >
                    <option value="nome">Nome A–Z</option>
                    <option value="preco_asc">Menor Preço</option>
                    <option value="preco_desc">Maior Preço</option>
                  </select>
                </div>
              </div>
            </div>
          </aside>

          <!-- Main content -->
          <div class="flex-1 min-w-0">

            <!-- Header bar -->
          <div class="flex items-center justify-between mb-4 bg-ck-surface border border-ck-border rounded p-3">
              <div class="flex items-center gap-2">
                <div class="w-1 h-5 bg-ck-accent rounded-full"></div>
                <h1 class="text-base font-bold text-white uppercase tracking-wide">{{ categoriaLabel || 'Todos os Produtos' }}</h1>
                <span *ngIf="categoriaLabel" (click)="clearCategoria()"
                  class="text-[10px] text-ck-muted hover:text-white cursor-pointer border border-ck-border rounded px-1.5 py-0.5 ml-2 transition-colors">✕ Limpar</span>
              </div>
              <!-- Mobile filter -->
              <div class="flex lg:hidden gap-2">
                <input
                  [(ngModel)]="searchTerm"
                  (ngModelChange)="onSearch($event)"
                  type="text"
                  placeholder="Buscar..."
                  class="px-3 py-1.5 text-xs bg-[#111] border border-ck-border text-ck-text placeholder-ck-muted rounded focus:outline-none focus:border-ck-accent"
                />
              </div>
            </div>

            <!-- Skeleton -->
            <div *ngIf="loading" class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
              <div *ngFor="let i of [1,2,3,4,5,6,7,8]" class="bg-ck-surface border border-ck-border rounded overflow-hidden">
                <div class="skeleton aspect-square"></div>
                <div class="p-3 space-y-2">
                  <div class="skeleton h-3 w-full rounded"></div>
                  <div class="skeleton h-3 w-3/4 rounded"></div>
                  <div class="skeleton h-5 w-1/2 rounded mt-2"></div>
                  <div class="skeleton h-7 w-full rounded mt-2"></div>
                </div>
              </div>
            </div>

            <!-- Products grid -->
            <div *ngIf="!loading && produtos.length > 0" class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
              <div
                *ngFor="let produto of produtos"
                class="product-card flex flex-col bg-ck-surface border border-ck-border rounded overflow-hidden cursor-pointer"
                [routerLink]="['/produtos', produto.id]"
              >
                <div class="aspect-square bg-[#111] p-4 flex items-center justify-center relative overflow-hidden">
                  <img
                    *ngIf="produto.imagemUrl"
                    [src]="produto.imagemUrl"
                    [alt]="produto.descricao"
                    class="w-full h-full object-contain"
                    (error)="onImgError($event)"
                  />
                  <svg *ngIf="!produto.imagemUrl" class="w-12 h-12 opacity-10" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1"><rect width="18" height="18" x="3" y="3" rx="2"/><path d="M3 9h18"/><path d="M9 21V9"/><path d="m14 14 3-3-3-3"/></svg>
                  <div *ngIf="isNovo(produto)" class="absolute top-2 left-2 bg-green-700 text-white text-[10px] font-bold px-1.5 py-0.5 rounded">NOVO</div>
                  <div *ngIf="isEstoqueCritico(produto)" class="absolute top-2 right-2 bg-amber-700 text-white text-[10px] font-bold px-1.5 py-0.5 rounded">Últimas!</div>
                </div>

                <div class="p-3 flex flex-col flex-1">
                  <h3 class="text-xs text-ck-text font-medium line-clamp-3 min-h-[3.5rem] leading-snug mb-3">{{ produto.descricao }}</h3>
                  <div class="mt-auto">
                    <p class="text-lg font-bold text-ck-price">R$ {{ produto.precoVenda | number:'1.2-2':'pt-BR' }}</p>
                    <p class="text-[10px] text-ck-muted mt-0.5">12x s/ juros no cartão</p>
                    <button
                      (click)="addToCart(produto, $event)"
                      class="mt-2 w-full py-2 bg-ck-accent hover:bg-ck-accentHover text-white text-xs font-semibold rounded uppercase tracking-wide transition-colors flex items-center justify-center gap-1"
                    >
                      <svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="8" cy="21" r="1"/><circle cx="19" cy="21" r="1"/><path d="M2.05 2.05h2l2.66 12.42a2 2 0 0 0 2 1.58h9.78a2 2 0 0 0 1.95-1.57l1.65-7.43H5.12"/></svg>
                      Comprar
                    </button>
                  </div>
                </div>
              </div>
            </div>

            <!-- Empty state -->
            <div *ngIf="!loading && produtos.length === 0" class="py-20 text-center bg-ck-surface border border-ck-border rounded">
              <svg class="mx-auto mb-4 opacity-20 w-16 h-16" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1"><circle cx="11" cy="11" r="8"/><path d="m21 21-4.3-4.3"/></svg>
              <p class="text-ck-muted text-sm">Nenhum produto encontrado.</p>
            </div>

            <!-- Pagination -->
            <div class="mt-8 flex items-center justify-center gap-2">
              <button
                (click)="previousPage()"
                [disabled]="page === 0"
                class="px-4 py-2 text-xs font-semibold bg-ck-surface border border-ck-border text-ck-muted hover:text-white hover:border-ck-accent rounded transition disabled:opacity-30 disabled:cursor-not-allowed"
              >← Anterior</button>
              <span class="px-4 py-2 text-xs text-ck-muted bg-ck-surface border border-ck-border rounded">Página {{ page + 1 }}</span>
              <button
                (click)="nextPage()"
                [disabled]="!hasMore"
                class="px-4 py-2 text-xs font-semibold bg-ck-surface border border-ck-border text-ck-muted hover:text-white hover:border-ck-accent rounded transition disabled:opacity-30 disabled:cursor-not-allowed"
              >Próxima →</button>
            </div>

          </div>
        </div>
      </div>
    </div>
  `,
})
export class ProdutosComponent implements OnInit {
  produtos: any[] = [];
  loading = true;
  page = 0;
  hasMore = true;
  searchTerm = '';
  sortBy = 'nome';
  categoria = '';
  categoriaLabel = '';

  constructor(
    private apiService: ApiService,
    private cartService: CartService,
    private toast: ToastService,
    private route: ActivatedRoute
  ) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.categoria = params['categoria'] || '';
      this.categoriaLabel = params['label'] || '';
      this.page = 0;
      this.loadProdutos();
    });
  }

  loadProdutos() {
    this.loading = true;
    this.apiService.getProdutos(this.page, 20).subscribe({
      next: (res: any) => {
        let content: any[] = res.content || [];
        // Filtro por categoria (tipo_produto discriminator)
        if (this.categoria) {
          content = content.filter((p: any) =>
            (p.tipo_produto || '').toLowerCase() === this.categoria.toLowerCase()
          );
        }
        if (this.searchTerm) {
          content = content.filter((p: any) =>
            p.descricao?.toLowerCase().includes(this.searchTerm.toLowerCase())
          );
        }
        content = this.sortProdutos(content);
        this.produtos = content;
        this.hasMore = !(res.page?.last ?? res.last ?? true);
        this.loading = false;
      },
      error: () => {
        this.toast.error('Erro ao carregar produtos.');
        this.loading = false;
      }
    });
  }

  clearCategoria() {
    this.categoria = ''; this.categoriaLabel = ''; this.page = 0; this.loadProdutos();
  }

  sortProdutos(list: any[]): any[] {
    switch (this.sortBy) {
      case 'preco_asc':  return [...list].sort((a, b) => parseFloat(a.precoVenda) - parseFloat(b.precoVenda));
      case 'preco_desc': return [...list].sort((a, b) => parseFloat(b.precoVenda) - parseFloat(a.precoVenda));
      default:           return [...list].sort((a, b) => (a.descricao || '').localeCompare(b.descricao || ''));
    }
  }

  onSearch(value: string) {
    this.searchTerm = value;
    this.page = 0;
    this.loadProdutos();
  }

  onSortChange(value: string) {
    this.sortBy = value;
    this.loadProdutos();
  }

  previousPage() {
    if (this.page > 0) { this.page--; this.loadProdutos(); }
  }

  nextPage() {
    if (this.hasMore) { this.page++; this.loadProdutos(); }
  }

  isNovo(produto: any): boolean {
    if (!produto.createdAt) return false;
    return (Date.now() - new Date(produto.createdAt).getTime()) / 86400000 <= 30;
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
    this.toast.success('Produto adicionado ao carrinho!');
  }

  onImgError(event: Event) {
    (event.target as HTMLImageElement).style.display = 'none';
  }
}
