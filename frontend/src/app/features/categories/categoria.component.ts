import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule, Router } from '@angular/router';
import { Produto } from '../../core/models/produto.models';
import { Categoria } from '../../core/models/categoria.models';
import { ProductService } from '../../core/services/product.service';
import { MatCard, MatCardContent } from "@angular/material/card";
import { MatIcon } from "@angular/material/icon";
import { DecimalPipe, CommonModule } from '@angular/common';
import { MaterialModule } from '../../shared/material/material.module';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatToolbarModule } from '@angular/material/toolbar';

@Component({
  selector: 'app-categoria',
  standalone: true,
  imports: [CommonModule, RouterModule, MaterialModule, DecimalPipe, MatProgressSpinnerModule, MatToolbarModule],
  templateUrl: './categoria.component.html',
  styleUrls: ['./categoria.component.scss']
})
export class CategoriaComponent implements OnInit {
  nomeCategoria = '';
  produtos: Produto[] = [];
  carregando = false;
  erro = '';
  
  categorias: Categoria[] = [
    { nome: 'Placa-Mãe',       quantidade: 142, icon: 'developer_board', bgColor: '#FFF0E6', iconColor: '#E8650A' },
    { nome: 'Placas de Vídeo', quantidade: 387, icon: 'memory',          bgColor: '#F0EDE8', iconColor: '#3A3A3C' },
    { nome: 'Processadores',   quantidade: 219, icon: 'cpu',             bgColor: '#E6F5F0', iconColor: '#0F6E56' },
    { nome: 'Memórias RAM',    quantidade: 98,  icon: 'sim_card',        bgColor: '#E6F0FA', iconColor: '#185FA5' },
    { nome: 'SSD',             quantidade: 564, icon: 'sd_card',         bgColor: '#FFF0E6', iconColor: '#E8650A' },
    { nome: 'HDD',             quantidade: 76,  icon: 'storage',         bgColor: '#F5EAF0', iconColor: '#993556' },
    { nome: 'Cabos',           quantidade: 105, icon: 'cable',           bgColor: '#F0F5E6', iconColor: '#3B6D11' },
    { nome: 'Monitores',       quantidade: 61,  icon: 'monitor',         bgColor: '#F0EDE8', iconColor: '#3A3A3C' },
  ];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private produtoService: ProductService
  ) {}

  ngOnInit() {
    this.route.paramMap.subscribe(params => {
      this.nomeCategoria = params.get('nome') || '';
      if (this.nomeCategoria) {
        this.carregarProdutos();
      } else {
        this.produtos = [];
        this.carregando = false;
      }
    });
  }

  carregarProdutos() {
    if (!this.nomeCategoria) {
      this.produtos = [];
      this.carregando = false;
      return;
    }

    this.carregando = true;
    this.erro = '';
    
    this.produtoService.getPorCategoria(this.nomeCategoria).subscribe({
      next: (produtos: Produto[]) => {
        this.produtos = produtos || [];
        this.carregando = false;
      },
      error: (error) => {
        console.error('Erro ao carregar produtos:', error);
        this.erro = 'Não foi possível carregar os produtos.';
        this.produtos = [];
        this.carregando = false;
      }
    });
  }

  selecionarCategoria(categoria: string) {
    this.router.navigate(['/categoria', categoria]);
  }

  pesquisar() {
    // Integrar com SearchService no futuro
  }

  adicionarAoCarrinho(prod: Produto) {
    // Integrar com CartService no futuro
    if (prod.quantidadeDisponivel && prod.quantidadeDisponivel > 0) {
      alert(`Adicionado "${prod.nome}" ao carrinho!`);
    }
  }

  getEmojiFromCategoria(categoria?: string): string {
    if (!categoria) return '📦';
    
    const cat = categoria.toLowerCase();
    if (cat.includes('placa') && cat.includes('mãe')) return '🖥️';
    if (cat.includes('video') || cat.includes('gpu') || cat.includes('gráfica')) return '🎮';
    if (cat.includes('processador') || cat.includes('cpu')) return '⚙️';
    if (cat.includes('memoria') || cat.includes('ram')) return '🧠';
    if (cat.includes('ssd') || cat.includes('nvme')) return '💾';
    if (cat.includes('hdd') || cat.includes('disco')) return '🗄️';
    if (cat.includes('monitor')) return '🖨️';
    if (cat.includes('cabo')) return '🔌';
    
    return '📦';
  }
}