import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

export interface CartItem {
  produtoId: number;
  nome: string;
  preco: number;
  quantidade: number;
}

@Injectable({
  providedIn: 'root',
})
export class CartService {
  private cartSubject = new BehaviorSubject<CartItem[]>([]);
  cart$: Observable<CartItem[]> = this.cartSubject.asObservable();

  constructor() {
    this.loadCart();
  }

  addToCart(item: CartItem) {
    const current = this.cartSubject.value;
    const existing = current.find((i) => i.produtoId === item.produtoId);

    if (existing) {
      existing.quantidade += item.quantidade;
    } else {
      current.push(item);
    }

    this.cartSubject.next([...current]);
    this.saveCart();
  }

  removeFromCart(produtoId: number) {
    const current = this.cartSubject.value.filter((i) => i.produtoId !== produtoId);
    this.cartSubject.next(current);
    this.saveCart();
  }

  clearCart() {
    this.cartSubject.next([]);
    this.saveCart();
  }

  private saveCart() {
    if (typeof window === 'undefined') return;
    localStorage.setItem('cart', JSON.stringify(this.cartSubject.value));
  }

  private loadCart() {
    if (typeof window === 'undefined') return;
    const saved = localStorage.getItem('cart');
    if (saved) {
      this.cartSubject.next(JSON.parse(saved));
    }
  }

  getTotal(): number {
    return this.cartSubject.value.reduce((acc, item) => acc + item.preco * item.quantidade, 0);
  }
}
