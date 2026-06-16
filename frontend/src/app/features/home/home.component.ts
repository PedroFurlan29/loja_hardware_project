import { Component  } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { MatIcon } from "@angular/material/icon";
import { MatCard, MatCardContent } from "@angular/material/card";
import { CommonModule } from '@angular/common';
import { MatToolbar } from "@angular/material/toolbar";
import { MatBadgeModule } from '@angular/material/badge';
import { Categoria } from '../../core/models/categoria.models';



@Component({
  selector: 'app-home',
  standalone: true,
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss'],
  imports: [MatIcon, MatCard, MatCardContent, CommonModule, RouterModule, MatToolbar, MatBadgeModule, MatIcon]
})
export class HomeComponent {
  cartCount = 3;

  stats = [
    { value: '12', suffix: 'k+', label: 'Produtos' },
    { value: '48', suffix: 'h',  label: 'Entrega'  },
    { value: '4',  suffix: '.8', label: 'Avaliação' },
  ];

categorias = [
  { nome: 'Placa-Mãe',       quantidade: 142, icon: 'developer_board',    bgColor: '#FFF0E6', iconColor: '#E8650A' },
  { nome: 'Placas de Vídeo', quantidade: 387, icon: 'memory',             bgColor: '#F0EDE8', iconColor: '#3A3A3C' },
  { nome: 'Processadores',   quantidade: 219, icon: 'cpu',                bgColor: '#E6F5F0', iconColor: '#0F6E56' },
  { nome: 'Memórias RAM',    quantidade: 98,  icon: 'sim_card',           bgColor: '#E6F0FA', iconColor: '#185FA5' },
  { nome: 'SSD',             quantidade: 564, icon: 'sd_card',            bgColor: '#FFF0E6', iconColor: '#E8650A' },
  { nome: 'HDD',             quantidade: 76,  icon: 'storage',            bgColor: '#F5EAF0', iconColor: '#993556' },
  { nome: 'Cabos',           quantidade: 105, icon: 'cable',              bgColor: '#F0F5E6', iconColor: '#3B6D11' },
  { nome: 'Monitores',       quantidade: 61,  icon: 'monitor',            bgColor: '#F0EDE8', iconColor: '#3A3A3C' },
];

produtosDestaque = [
  { nome: 'Ryzen 5 5600X',          marca: 'AMD',     preco: 899.90,  precoAntigo: 1099.90, emoji: '🖥️', tag: { tipo: 'sale', label: '-18%' } },
  { nome: 'GeForce RTX 4060 8GB',   marca: 'NVIDIA',  preco: 2199.90, precoAntigo: null,    emoji: '🎮', tag: { tipo: 'new',  label: 'Novo'  } },
  { nome: 'SSD NVMe 1TB 3500MB/s',  marca: 'Kingston',preco: 379.90,  precoAntigo: 449.90,  emoji: '💾', tag: { tipo: 'sale', label: '-15%' } },
  { nome: 'DDR4 16GB 3200MHz',      marca: 'Corsair', preco: 249.90,  precoAntigo: null,    emoji: '🧩', tag: null },
];

  constructor(private router: Router) {}

  pesquisar() { /* integrar com SearchService */ }
  verOfertas() { this.router.navigate(['/ofertas']); }
  explorarCatalogo() { this.router.navigate(['/produtos']); }
  irParaCategoria(cat: Categoria) { this.router.navigate(['/categoria', cat.nome]);
 }  verPromocao() { this.router.navigate(['/promocoes']); }

  adicionarAoCarrinho(prod: any) {
    this.cartCount++;
    // integrar com CartService
  }
}