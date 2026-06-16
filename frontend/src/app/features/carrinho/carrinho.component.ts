import { Component, OnInit } from '@angular/core';
import { CommonModule, DecimalPipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { CartService } from '../../core/services/cart.service';
import { ToastService } from '../../shared/services/toast.service';

@Component({
  selector: 'app-carrinho',
  standalone: true,
  imports: [CommonModule, RouterLink, DecimalPipe],
  template: `
    <div class="bg-ck-bg min-h-screen">
      <div class="max-w-[1400px] mx-auto px-4 py-8">

        <div class="flex items-center gap-3 mb-8">
          <div class="w-1 h-6 bg-ck-accent rounded-full"></div>
          <h1 class="text-2xl font-bold text-white uppercase tracking-wide">Meu Carrinho</h1>
        </div>

        <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">

          <!-- Items list -->
          <div class="lg:col-span-2 space-y-3">
            <div *ngIf="(cart$ | async) as items">
              <div *ngIf="items.length > 0; else emptyCart">
                <div *ngFor="let item of items" class="bg-ck-surface border border-ck-border rounded p-4 flex gap-4 items-center hover:border-ck-accent transition-colors">
                  <div class="w-16 h-16 bg-[#111] rounded flex items-center justify-center flex-shrink-0">
                    <svg class="w-8 h-8 opacity-10" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1"><rect width="18" height="18" x="3" y="3" rx="2"/><path d="M3 9h18"/><path d="M9 21V9"/><path d="m14 14 3-3-3-3"/></svg>
                  </div>
                  <div class="flex-1 min-w-0">
                    <p class="text-sm text-ck-text font-medium line-clamp-2">{{ item.nome }}</p>
                    <p class="text-xs text-ck-muted mt-1">Qtd: {{ item.quantidade }}</p>
                  </div>
                  <div class="text-right flex-shrink-0">
                    <p class="text-base font-bold text-ck-price">R$ {{ (item.preco * item.quantidade) | number:'1.2-2':'pt-BR' }}</p>
                    <button (click)="removeItem(item.produtoId)" class="text-xs text-red-500 hover:text-red-400 mt-1 transition-colors">Remover</button>
                  </div>
                </div>
              </div>
              <ng-template #emptyCart>
                <div class="bg-ck-surface border border-ck-border rounded p-16 text-center">
                  <svg class="mx-auto mb-4 opacity-10 w-16 h-16" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1"><circle cx="8" cy="21" r="1"/><circle cx="19" cy="21" r="1"/><path d="M2.05 2.05h2l2.66 12.42a2 2 0 0 0 2 1.58h9.78a2 2 0 0 0 1.95-1.57l1.65-7.43H5.12"/></svg>
                  <p class="text-ck-muted mb-6">Seu carrinho está vazio.</p>
                  <a routerLink="/produtos" class="px-6 py-2 bg-ck-accent hover:bg-ck-accentHover text-white text-sm font-semibold rounded uppercase tracking-wide transition-colors">Continuar Comprando</a>
                </div>
              </ng-template>
            </div>
          </div>

          <!-- Summary -->
          <div class="bg-ck-surface border border-ck-border rounded p-6 h-fit sticky top-28 border-t-2 border-t-ck-accent">
            <h2 class="text-sm font-bold text-white uppercase mb-5 pb-3 border-b border-ck-border">Resumo do Pedido</h2>
            <div class="space-y-3 text-sm mb-6">
              <div class="flex justify-between text-ck-muted">
                <span>Subtotal</span>
                <span>R$ {{ getTotal() | number:'1.2-2':'pt-BR' }}</span>
              </div>
              <div class="flex justify-between text-green-400 font-medium">
                <span>Frete grátis</span>
                <span>R$ 0,00</span>
              </div>
              <div class="flex justify-between items-end pt-3 border-t border-ck-border">
                <span class="font-bold text-white">Total</span>
                <span class="text-2xl font-bold text-ck-price">R$ {{ getTotal() | number:'1.2-2':'pt-BR' }}</span>
              </div>
            </div>
            <a
              routerLink="/checkout"
              [class.pointer-events-none]="getTotal() === 0"
              [class.opacity-40]="getTotal() === 0"
              class="block w-full py-3 bg-ck-accent hover:bg-ck-accentHover text-white text-sm font-bold rounded uppercase tracking-wide transition-colors text-center"
            >
              Finalizar Compra
            </a>
            <a routerLink="/produtos" class="block text-center text-xs text-ck-muted hover:text-white mt-4 transition-colors">← Continuar comprando</a>
          </div>

        </div>
      </div>
    </div>
  `,
})
export class CarrinhoComponent implements OnInit {
  cart$;

  constructor(private cartService: CartService, private toast: ToastService) {
    this.cart$ = this.cartService.cart$;
  }

  ngOnInit() {}

  removeItem(produtoId: number) {
    this.cartService.removeFromCart(produtoId);
    this.toast.info('Item removido do carrinho.');
  }

  getTotal(): number {
    return this.cartService.getTotal();
  }
}
