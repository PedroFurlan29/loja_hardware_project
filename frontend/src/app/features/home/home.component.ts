import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ProductService } from '../../core/services/product.service';
import { CartService } from '../../core/services/cart.service';
import { ToastService } from '../../shared/services/toast.service';
import { Produto } from '../../core/models/produto.models';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  produtos: Produto[] = [];
  loading = true;

  constructor(
    private productService: ProductService,
    private cartService: CartService,
    private toast: ToastService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.productService.getProdutos(0, 100).subscribe({
      next: (res) => {
        this.produtos = res.data || [];
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }

  isNovo(produto: Produto): boolean {
    if (!produto.criadoEm) return false;
    const dias = (Date.now() - new Date(produto.criadoEm).getTime()) / 86400000;
    return dias <= 30;
  }

  isEstoqueCritico(produto: Produto): boolean {
    return produto.quantidadeDisponivel != null && produto.estoqueMinimo != null
      && produto.quantidadeDisponivel <= produto.estoqueMinimo;
  }

  addToCart(produto: Produto, event: Event) {
    event.stopPropagation();
    event.preventDefault();
    this.cartService.addToCart({
      produtoId: produto.id,
      nome: produto.nome,
      preco: produto.preco,
      quantidade: 1,
    });
    this.toast.success(`"${produto.nome.substring(0, 30)}..." adicionado ao carrinho!`);
  }

  onImgError(event: Event) {
    (event.target as HTMLImageElement).style.display = 'none';
  }
}