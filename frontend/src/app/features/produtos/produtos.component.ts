import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ProductService } from '../../core/services/product.service';
import { CartService } from '../../core/services/cart.service';
import { ToastService } from '../../shared/services/toast.service';
import { Produto } from '../../core/models/produto.models';

@Component({
  selector: 'app-produtos',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './produtos.component.html',
  styleUrls: ['./produtos.component.scss']
})
export class ProdutosComponent implements OnInit {
  produtos: Produto[] = [];
  loading = true;
  page = 0;
  hasMore = true;
  searchTerm = '';
  sortBy = 'nome';
  categoria = '';
  categoriaLabel = '';

  constructor(
    private productService: ProductService,
    private cartService: CartService,
    private toast: ToastService,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef
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
    this.productService.getProdutos(this.page, 20).subscribe({
      next: (res) => {
        let data: Produto[] = res.data || [];
        if (this.categoria) {
          data = data.filter((p) =>
            (p.categoria || '').toLowerCase() === this.categoria.toLowerCase()
          );
        }
        if (this.searchTerm) {
          data = data.filter((p) =>
            p.nome?.toLowerCase().includes(this.searchTerm.toLowerCase())
          );
        }
        data = this.sortProdutos(data);
        this.produtos = data;
        this.hasMore = res.count > (this.page + 1) * 20;
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.toast.error('Erro ao carregar produtos.');
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }

  clearCategoria() {
    this.categoria = '';
    this.categoriaLabel = '';
    this.page = 0;
    this.loadProdutos();
  }

  sortProdutos(list: Produto[]): Produto[] {
    switch (this.sortBy) {
      case 'preco_asc': return [...list].sort((a, b) => a.preco - b.preco);
      case 'preco_desc': return [...list].sort((a, b) => b.preco - a.preco);
      default: return [...list].sort((a, b) => (a.nome || '').localeCompare(b.nome || ''));
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
    if (this.page > 0) {
      this.page--;
      this.loadProdutos();
    }
  }

  nextPage() {
    if (this.hasMore) {
      this.page++;
      this.loadProdutos();
    }
  }

  isNovo(produto: Produto): boolean {
    if (!produto.criadoEm) return false;
    return (Date.now() - new Date(produto.criadoEm).getTime()) / 86400000 <= 30;
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
    this.toast.success('Produto adicionado ao carrinho!');
  }

  onImgError(event: Event) {
    (event.target as HTMLImageElement).style.display = 'none';
  }
}