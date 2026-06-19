import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule, DecimalPipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { ApiService } from '../../core/services/api.service';

@Component({
  selector: 'app-meus-pedidos',
  standalone: true,
  imports: [CommonModule, RouterLink, DecimalPipe],
  template: `
    <div class="bg-ck-bg min-h-screen">
      <div class="max-w-[1400px] mx-auto px-4 py-8">
        <div class="flex items-center gap-3 mb-6">
          <div class="w-1 h-6 bg-ck-accent rounded-full"></div>
          <h1 class="text-xl font-bold text-white uppercase">Meus Pedidos</h1>
        </div>

        <div *ngIf="loading" class="space-y-3">
          <div *ngFor="let i of [1,2,3]" class="skeleton h-20 rounded"></div>
        </div>

        <div *ngIf="!loading && pedidos.length === 0" class="bg-ck-surface border border-ck-border rounded p-16 text-center">
          <p class="text-ck-muted mb-4">Nenhum pedido encontrado.</p>
          <a routerLink="/produtos" class="px-6 py-2 bg-ck-accent hover:bg-ck-accentHover text-white text-sm font-semibold rounded uppercase transition-colors">Ver Produtos</a>
        </div>

        <div *ngIf="!loading && pedidos.length > 0" class="space-y-3">
          <div *ngFor="let p of pedidos" class="bg-ck-surface border border-ck-border rounded p-4">
            <div class="flex justify-between items-start mb-3">
              <div>
                <span class="text-xs text-ck-muted font-mono">Pedido #{{ p.id }}</span>
                <span class="ml-3 text-[10px] px-2 py-0.5 rounded font-bold uppercase"
                  [class]="p.status === 'CONCLUIDA' ? 'bg-green-900/40 text-green-400' : p.status === 'CANCELADA' ? 'bg-red-900/40 text-red-400' : 'bg-yellow-900/40 text-yellow-400'">
                  {{ p.status }}
                </span>
              </div>
              <span class="text-lg font-bold text-ck-price">R$ {{ p.valorTotal | number:'1.2-2':'pt-BR' }}</span>
            </div>
            <div *ngIf="p.motivoCancelamento" class="text-xs text-red-400 mb-2">Motivo: {{ p.motivoCancelamento }}</div>
            <div class="text-xs text-ck-muted">
              <span *ngFor="let item of p.itens; let last = last">
                {{ item.produtoNome }} (x{{ item.quantidade }}){{ last ? '' : ', ' }}
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>
  `
})
export class MeusPedidosComponent implements OnInit {
  pedidos: any[] = [];
  loading = true;

  constructor(
    private api: ApiService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.carregar();
  }

  private carregar() {
    this.loading = true;
    this.api.getMinhasVendas().subscribe({
      next: (v: any[]) => {
        this.pedidos = Array.isArray(v) ? v : [];
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error('Erro ao carregar pedidos:', err);
        this.pedidos = [];
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }
}
