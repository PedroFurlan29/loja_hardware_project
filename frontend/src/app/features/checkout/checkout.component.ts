import { Component, OnInit } from '@angular/core';
import { CommonModule, DecimalPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { CartService } from '../../core/services/cart.service';
import { ApiService } from '../../core/services/api.service';
import { ToastService } from '../../shared/services/toast.service';

@Component({
  selector: 'app-checkout',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, DecimalPipe],
  template: `
    <div class="bg-ck-bg min-h-screen">
      <div class="max-w-4xl mx-auto px-4 py-8">

        <div class="flex items-center gap-3 mb-8">
          <div class="w-1 h-6 bg-ck-accent rounded-full"></div>
          <h1 class="text-2xl font-bold text-white uppercase tracking-wide">Finalizar Pedido</h1>
        </div>

        <div *ngIf="(cart$ | async)?.length === 0" class="bg-ck-surface border border-ck-border rounded p-12 text-center">
          <p class="text-ck-muted mb-6">Nenhum produto no carrinho.</p>
          <a routerLink="/produtos" class="px-6 py-2 bg-ck-accent hover:bg-ck-accentHover text-white text-sm font-semibold rounded uppercase tracking-wide transition-colors">Ver Produtos</a>
        </div>

        <form (ngSubmit)="checkout()" class="grid grid-cols-1 lg:grid-cols-5 gap-6" *ngIf="(cart$ | async)?.length">

          <!-- Form -->
          <div class="lg:col-span-3 space-y-4">

            <div class="bg-ck-surface border border-ck-border rounded p-5">
              <h2 class="text-sm font-bold text-white uppercase mb-4 pb-3 border-b border-ck-border flex items-center gap-2">
                <span class="w-5 h-5 bg-ck-accent text-white text-xs flex items-center justify-center rounded-full font-bold">1</span>
                Identificação
              </h2>
              <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                  <label class="block text-[11px] text-ck-muted uppercase font-semibold mb-1">Nome Completo</label>
                  <input [(ngModel)]="nome" name="nome" required
                    class="w-full px-3 py-2 text-sm bg-[#111] border border-ck-border text-ck-text rounded focus:outline-none focus:border-ck-accent transition-colors" />
                </div>
                <div>
                  <label class="block text-[11px] text-ck-muted uppercase font-semibold mb-1">E-mail</label>
                  <input [(ngModel)]="email" name="email" type="email" required
                    class="w-full px-3 py-2 text-sm bg-[#111] border border-ck-border text-ck-text rounded focus:outline-none focus:border-ck-accent transition-colors" />
                </div>
              </div>
            </div>

            <div class="bg-ck-surface border border-ck-border rounded p-5">
              <h2 class="text-sm font-bold text-white uppercase mb-4 pb-3 border-b border-ck-border flex items-center gap-2">
                <span class="w-5 h-5 bg-ck-accent text-white text-xs flex items-center justify-center rounded-full font-bold">2</span>
                Entrega
              </h2>
              <div class="space-y-4">
                <div>
                  <label class="block text-[11px] text-ck-muted uppercase font-semibold mb-1">Endereço</label>
                  <input [(ngModel)]="endereco" name="endereco" required
                    class="w-full px-3 py-2 text-sm bg-[#111] border border-ck-border text-ck-text rounded focus:outline-none focus:border-ck-accent transition-colors" />
                </div>
                <div class="grid grid-cols-2 gap-4">
                  <div>
                    <label class="block text-[11px] text-ck-muted uppercase font-semibold mb-1">Cidade</label>
                    <input [(ngModel)]="cidade" name="cidade" required
                      class="w-full px-3 py-2 text-sm bg-[#111] border border-ck-border text-ck-text rounded focus:outline-none focus:border-ck-accent transition-colors" />
                  </div>
                  <div>
                    <label class="block text-[11px] text-ck-muted uppercase font-semibold mb-1">CEP</label>
                    <input [(ngModel)]="cep" name="cep" required
                      class="w-full px-3 py-2 text-sm bg-[#111] border border-ck-border text-ck-text rounded focus:outline-none focus:border-ck-accent transition-colors" />
                  </div>
                </div>
              </div>
            </div>

          </div>

          <!-- Order summary -->
          <div class="lg:col-span-2">
            <div class="bg-ck-surface border border-t-2 border-ck-border border-t-ck-accent rounded p-5 sticky top-28">
              <h2 class="text-sm font-bold text-white uppercase mb-4 pb-3 border-b border-ck-border">Seu Pedido</h2>

              <div class="space-y-2 max-h-56 overflow-y-auto mb-4 pr-1">
                <div *ngFor="let item of (cart$ | async)" class="flex justify-between items-start text-xs text-ck-muted gap-2">
                  <span class="line-clamp-2 flex-1">{{ item.quantidade }}× {{ item.nome }}</span>
                  <span class="font-bold text-ck-text flex-shrink-0">R$ {{ (item.preco * item.quantidade) | number:'1.2-2':'pt-BR' }}</span>
                </div>
              </div>

              <div class="border-t border-ck-border pt-3 mb-5">
                <div class="flex justify-between items-end">
                  <span class="text-xs text-ck-muted uppercase font-semibold">Total</span>
                  <span class="text-2xl font-bold text-ck-price">R$ {{ getTotal() | number:'1.2-2':'pt-BR' }}</span>
                </div>
              </div>

              <button
                type="submit"
                [disabled]="isLoading"
                class="w-full py-3 bg-green-600 hover:bg-green-700 text-white font-bold rounded uppercase tracking-wide text-sm transition-colors disabled:opacity-50 flex items-center justify-center gap-2"
              >
                <svg *ngIf="!isLoading" xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><polyline points="20 6 9 17 4 12"/></svg>
                {{ isLoading ? 'Processando...' : 'Confirmar Pedido' }}
              </button>
            </div>
          </div>

        </form>
      </div>
    </div>
  `,
})
export class CheckoutComponent implements OnInit {
  cart$;
  nome = ''; email = ''; endereco = ''; cidade = ''; cep = '';
  isLoading = false;

  constructor(
    private cartService: CartService,
    private apiService: ApiService,
    private toast: ToastService,
    public router: Router
  ) {
    this.cart$ = this.cartService.cart$;
  }

  ngOnInit() {}

  getTotal(): number { return this.cartService.getTotal(); }

  checkout() {
    if (!this.nome || !this.email || !this.endereco || !this.cidade || !this.cep) {
      this.toast.error('Por favor, preencha todos os campos.');
      return;
    }
    this.isLoading = true;

    let items: { produtoId: number; quantidade: number }[] = [];
    const sub = this.cart$.subscribe(cart => {
      items = cart.map(i => ({ produtoId: i.produtoId, quantidade: i.quantidade }));
    });
    sub.unsubscribe();

    this.apiService.registrarVenda(items).subscribe({
      next: () => {
        this.toast.success('Pedido realizado com sucesso! Obrigado pela compra.');
        this.cartService.clearCart();
        setTimeout(() => this.router.navigate(['/']), 2000);
      },
      error: (err: any) => {
        const msg = err.status === 401
          ? 'Você precisa estar logado para finalizar um pedido.'
          : 'Erro ao registrar pedido. Tente novamente.';
        this.toast.error(msg);
        if (err.status === 401) this.router.navigate(['/login']);
        this.isLoading = false;
      },
    });
  }
}
