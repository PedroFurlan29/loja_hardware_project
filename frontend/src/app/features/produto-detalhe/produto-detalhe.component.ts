import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ApiService } from '../../core/services/api.service';
import { CartService } from '../../core/services/cart.service';
import { ToastService } from '../../shared/services/toast.service';
import { getCategoryFallbackSvg } from '../../shared/utils/category-images';

@Component({
  selector: 'app-produto-detalhe',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <div class="bg-ck-bg min-h-screen">
      <div class="max-w-[1400px] mx-auto px-4 py-8">

        <!-- Breadcrumb -->
        <nav class="flex items-center gap-2 text-xs text-ck-muted mb-6 font-medium">
          <a routerLink="/" class="hover:text-white transition-colors">Home</a>
          <svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="m9 18 6-6-6-6"/></svg>
          <a routerLink="/produtos" class="hover:text-white transition-colors">Produtos</a>
          <svg xmlns="http://www.w3.org/2000/svg" width="12" height="12" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="m9 18 6-6-6-6"/></svg>
          <span class="text-ck-text truncate max-w-xs">{{ produto?.descricao }}</span>
        </nav>

        <!-- Skeleton loading -->
        <div *ngIf="loading" class="grid grid-cols-1 md:grid-cols-2 gap-10">
          <div class="skeleton aspect-square rounded"></div>
          <div class="space-y-4 pt-4">
            <div class="skeleton h-6 w-3/4 rounded"></div>
            <div class="skeleton h-4 w-1/2 rounded"></div>
            <div class="skeleton h-10 w-1/3 rounded mt-6"></div>
            <div class="skeleton h-12 w-full rounded mt-4"></div>
          </div>
        </div>

        <!-- Product detail -->
        <div *ngIf="!loading && produto" class="grid grid-cols-1 md:grid-cols-2 gap-10">

          <!-- Image -->
          <div class="bg-ck-surface border border-ck-border rounded-md p-8 flex items-center justify-center aspect-square">
            <img
              [src]="getImagemSrc(produto)"
              [alt]="produto.descricao"
              class="max-w-full max-h-full object-contain"
              (error)="onImgError($event)"
            />
          </div>

          <!-- Info -->
          <div class="py-4">
            <!-- Badges -->
            <div class="flex gap-2 mb-4">
              <span *ngIf="produto.tipo_produto" class="text-xs bg-ck-surface border border-ck-border text-ck-muted px-2 py-0.5 rounded uppercase font-semibold">{{ produto.tipo_produto }}</span>
              <span *ngIf="produto.quantidade <= produto.estoque_minimo" class="text-xs bg-amber-900 text-amber-200 px-2 py-0.5 rounded font-semibold">Últimas unidades</span>
            </div>

            <h1 class="text-2xl font-bold text-white mb-2 leading-tight">{{ produto.descricao }}</h1>
            <p class="text-xs text-ck-muted mb-6">SKU: {{ produto.sku }}</p>

            <!-- Price block -->
            <div class="bg-ck-surface border border-ck-border rounded p-4 mb-6">
              <p class="text-4xl font-bold text-ck-price mb-1">R$ {{ produto.precoVenda | number:'1.2-2':'pt-BR' }}</p>
              <p class="text-xs text-ck-muted">12x de R$ {{ (produto.precoVenda / 12) | number:'1.2-2':'pt-BR' }} sem juros</p>
              <p class="text-xs text-green-400 mt-1 font-medium">✓ Frete grátis para todo o Brasil</p>
            </div>

            <!-- Stock -->
            <p class="text-sm text-ck-muted mb-6">
              Estoque:
              <span [class]="produto.quantidade <= produto.estoque_minimo ? 'text-amber-400 font-bold' : 'text-green-400 font-bold'">
                {{ produto.quantidade }} unidades
              </span>
            </p>

            <!-- Buttons -->
            <div class="flex flex-col gap-3">
              <button
                (click)="addToCart()"
                [disabled]="produto.quantidade === 0"
                class="w-full py-3 bg-ck-accent hover:bg-ck-accentHover text-white font-bold rounded uppercase tracking-wide text-sm transition-colors disabled:opacity-40 flex items-center justify-center gap-2"
              >
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="8" cy="21" r="1"/><circle cx="19" cy="21" r="1"/><path d="M2.05 2.05h2l2.66 12.42a2 2 0 0 0 2 1.58h9.78a2 2 0 0 0 1.95-1.57l1.65-7.43H5.12"/></svg>
                Adicionar ao Carrinho
              </button>
              <button (click)="addToCartAndGo()"
                class="w-full py-3 bg-green-700 hover:bg-green-600 text-white font-bold rounded uppercase tracking-wide text-sm transition-colors text-center">
                Comprar Agora
              </button>
            </div>

            <!-- Technical specs -->
            <div *ngIf="getSpecs().length > 0" class="mt-8 border-t border-ck-border pt-6">
              <h2 class="text-sm font-bold text-white uppercase mb-4 flex items-center gap-2">
                <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M12 20h9"/><path d="M16.5 3.5a2.121 2.121 0 0 1 3 3L7 19l-4 1 1-4L16.5 3.5z"/></svg>
                Especificações Técnicas
              </h2>
              <div class="space-y-2">
                <div *ngFor="let spec of getSpecs()" class="flex gap-3 text-sm py-2 border-b border-ck-border/50">
                  <span class="text-ck-muted w-36 flex-shrink-0">{{ spec.label }}</span>
                  <span class="text-ck-text font-medium">{{ spec.value }}</span>
                </div>
              </div>
            </div>

          </div>
        </div>

        <!-- Error state -->
        <div *ngIf="!loading && !produto" class="text-center py-20 bg-ck-surface border border-ck-border rounded">
          <p class="text-ck-muted mb-4">Produto não encontrado.</p>
          <a routerLink="/produtos" class="text-ck-accent hover:text-white transition-colors text-sm font-semibold">← Voltar ao catálogo</a>
        </div>

      </div>
    </div>
  `,
})
export class ProdutoDetalheComponent implements OnInit {
  produto: any = null;
  loading = true;

  constructor(
    private route: ActivatedRoute,
    private apiService: ApiService,
    private cartService: CartService,
    private toast: ToastService,
    private router: Router,
    private cdr: ChangeDetectorRef,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (!id) { this.loading = false; return; }

    this.apiService.getProdutoById(+id).subscribe({
      next: (p: any) => { this.produto = p; this.loading = false; this.cdr.detectChanges(); },
      error: () => { this.loading = false; this.cdr.detectChanges(); this.toast.error('Produto não encontrado.'); }
    });
  }

  addToCart() {
    if (!this.produto) return;
    this.cartService.addToCart({
      produtoId: this.produto.id,
      nome: this.produto.descricao,
      preco: parseFloat(this.produto.precoVenda),
      quantidade: 1,
    });
    this.toast.success('Produto adicionado ao carrinho!');
  }

  addToCartAndGo() {
    this.addToCart();
    this.router.navigate(['/carrinho']);
  }

  getSpecs(): { label: string; value: string }[] {
    if (!this.produto) return [];
    const specs: { label: string; value: string }[] = [];
    const add = (label: string, value: any) => { if (value != null) specs.push({ label, value: String(value) }); };

    // CPU
    add('Marca', this.produto.marca);
    add('Modelo', this.produto.modelo);
    add('Núcleos', this.produto.nucleos);
    add('Frequência', this.produto.frequenciaGHz ? `${this.produto.frequenciaGHz} GHz` : null);
    add('Arquitetura', this.produto.arquitetura);
    add('Socket', this.produto.socket);
    // GPU
    add('VRAM', this.produto.vramGb ? `${this.produto.vramGb} GB` : null);
    add('Tipo de Memória', this.produto.tipoMemoria);
    add('Chipset', this.produto.chipset);
    add('Barramento', this.produto.larguraBandaBits ? `${this.produto.larguraBandaBits}-bit` : null);
    // RAM
    add('Capacidade', this.produto.capacidadeGb ? `${this.produto.capacidadeGb} GB` : null);
    add('Frequência', this.produto.frequenciaMHz ? `${this.produto.frequenciaMHz} MHz` : null);
    add('Latência', this.produto.latenciaCl ? `CL${this.produto.latenciaCl}` : null);
    // Storage
    add('Tipo', this.produto.tipoArmazenamento);
    add('Interface', this.produto.interface_);
    add('Leitura', this.produto.velocidadeLeituraMBs ? `${this.produto.velocidadeLeituraMBs} MB/s` : null);

    return specs;
  }

  onImgError(event: Event) {
    const img = event.target as HTMLImageElement;
    img.style.display = 'none';
  }

  getImagemSrc(produto: any): string {
    if (produto?.imagemUrl) return produto.imagemUrl;
    return 'data:image/svg+xml,' + encodeURIComponent(getCategoryFallbackSvg(produto?.sku?.replace(/-\d+$/, '')));
  }

  getFallbackSvg(produto: any): SafeHtml {
    return this.sanitizer.bypassSecurityTrustHtml(getCategoryFallbackSvg(produto?.sku?.replace(/-\d+$/, '')));
  }
}
