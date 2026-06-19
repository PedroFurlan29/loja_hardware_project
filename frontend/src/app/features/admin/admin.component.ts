import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule, DecimalPipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ApiService } from '../../core/services/api.service';
import { AuthService } from '../../core/services/auth.service';
import { ToastService } from '../../shared/services/toast.service';

type Tab = 'dashboard' | 'vendas' | 'estoque' | 'fornecedores';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink, DecimalPipe],
  template: `
    <div class="bg-ck-bg min-h-screen flex">

      <!-- Sidebar -->
      <aside class="w-56 bg-[#0a0a0a] border-r border-ck-border flex-shrink-0 sticky top-0 h-screen overflow-y-auto hidden md:block">
        <div class="p-4 border-b border-ck-border">
          <p class="text-[11px] text-ck-muted uppercase font-semibold">Painel</p>
          <p class="text-sm font-bold text-white mt-0.5">{{ authService.user()?.nome }}</p>
          <span class="inline-block mt-1 text-[9px] px-1.5 py-0.5 rounded bg-ck-accent/20 text-ck-accent font-bold uppercase">
            {{ authService.user()?.perfil }}
          </span>
        </div>
        <nav class="p-2">
          <button *ngFor="let item of navItems" (click)="activeTab = item.tab"
            class="w-full flex items-center gap-3 px-3 py-2.5 rounded text-sm transition-colors mb-0.5 text-left"
            [class]="activeTab === item.tab ? 'bg-ck-accent text-white font-semibold' : 'text-ck-muted hover:text-white hover:bg-ck-surface'">
            <span [innerHTML]="item.icon"></span>
            {{ item.label }}
          </button>
          <div class="my-3 border-t border-ck-border"></div>
          <a routerLink="/" class="flex items-center gap-3 px-3 py-2.5 rounded text-sm text-ck-muted hover:text-white hover:bg-ck-surface transition-colors">
            <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="m3 9 9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"/><polyline points="9 22 9 12 15 12 15 22"/></svg>
            Ver Loja
          </a>
        </nav>
      </aside>

      <!-- Main content -->
      <main class="flex-1 min-w-0 p-6">

        <!-- Mobile tab bar -->
        <div class="flex gap-2 mb-6 md:hidden overflow-x-auto pb-2">
          <button *ngFor="let item of navItems" (click)="activeTab = item.tab"
            class="flex-shrink-0 px-3 py-1.5 rounded text-xs font-semibold transition-colors"
            [class]="activeTab === item.tab ? 'bg-ck-accent text-white' : 'bg-ck-surface text-ck-muted border border-ck-border'">
            {{ item.label }}
          </button>
        </div>

        <!-- DASHBOARD -->
        <div *ngIf="activeTab === 'dashboard'">
          <div class="flex items-center gap-3 mb-6">
            <div class="w-1 h-6 bg-ck-accent rounded-full"></div>
            <h1 class="text-xl font-bold text-white">Dashboard</h1>
          </div>
          <div class="grid grid-cols-2 md:grid-cols-4 gap-4 mb-8">
            <div class="bg-ck-surface border border-ck-border rounded p-4">
              <p class="text-xs text-ck-muted uppercase font-semibold mb-1">Total Vendas</p>
              <p class="text-2xl font-bold text-white">{{ vendas.length }}</p>
            </div>
            <div class="bg-ck-surface border border-ck-border rounded p-4">
              <p class="text-xs text-ck-muted uppercase font-semibold mb-1">Produtos</p>
              <p class="text-2xl font-bold text-white">{{ totalProdutos }}</p>
            </div>
            <div class="bg-ck-surface border border-ck-border rounded p-4">
              <p class="text-xs text-ck-muted uppercase font-semibold mb-1">Estoque Crítico</p>
              <p class="text-2xl font-bold text-amber-400">{{ estoquesCriticos.length }}</p>
            </div>
            <div class="bg-ck-surface border border-ck-border rounded p-4">
              <p class="text-xs text-ck-muted uppercase font-semibold mb-1">Fornecedores</p>
              <p class="text-2xl font-bold text-white">{{ fornecedores.length }}</p>
            </div>
          </div>

          <!-- Estoque crítico alert -->
          <div *ngIf="estoquesCriticos.length > 0" class="bg-amber-900/20 border border-amber-700 rounded p-4 mb-6">
            <div class="flex items-center gap-2 mb-3">
              <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="#f59e0b" stroke-width="2"><path d="m21.73 18-8-14a2 2 0 0 0-3.48 0l-8 14A2 2 0 0 0 4 21h16a2 2 0 0 0 1.73-3Z"/><path d="M12 9v4"/><path d="M12 17h.01"/></svg>
              <span class="text-amber-400 font-bold text-sm">{{ estoquesCriticos.length }} produto(s) em estoque crítico</span>
            </div>
            <div class="space-y-2">
              <div *ngFor="let e of estoquesCriticos" class="flex justify-between items-center bg-ck-surface rounded p-2 text-xs">
                <span class="text-ck-text">{{ e.produto?.descricao || 'Produto #'+e.produto?.id }}</span>
                <span class="text-amber-400 font-bold">{{ e.quantidade }} unidades</span>
              </div>
            </div>
          </div>
        </div>

        <!-- VENDAS -->
        <div *ngIf="activeTab === 'vendas'">
          <div class="flex items-center gap-3 mb-6">
            <div class="w-1 h-6 bg-ck-accent rounded-full"></div>
            <h1 class="text-xl font-bold text-white">Gerenciar Vendas</h1>
          </div>

          <div *ngIf="loadingVendas" class="space-y-2">
            <div *ngFor="let i of [1,2,3]" class="skeleton h-16 rounded"></div>
          </div>

          <div *ngIf="!loadingVendas" class="bg-ck-surface border border-ck-border rounded overflow-hidden">
            <table class="w-full text-sm">
              <thead class="bg-[#111] border-b border-ck-border">
                <tr>
                  <th class="px-4 py-3 text-left text-[11px] text-ck-muted uppercase font-semibold">ID</th>
                  <th class="px-4 py-3 text-left text-[11px] text-ck-muted uppercase font-semibold">Status</th>
                  <th class="px-4 py-3 text-left text-[11px] text-ck-muted uppercase font-semibold">Itens</th>
                  <th class="px-4 py-3 text-right text-[11px] text-ck-muted uppercase font-semibold">Total</th>
                  <th class="px-4 py-3 text-right text-[11px] text-ck-muted uppercase font-semibold">Ações</th>
                </tr>
              </thead>
              <tbody>
                <tr *ngIf="vendas.length === 0"><td colspan="5" class="px-4 py-10 text-center text-ck-muted text-xs">Nenhuma venda registrada.</td></tr>
                <tr *ngFor="let v of vendas" class="border-b border-ck-border/50 hover:bg-ck-surface/50">
                  <td class="px-4 py-3 text-ck-muted font-mono text-xs">#{{ v.id }}</td>
                  <td class="px-4 py-3">
                    <span class="text-[10px] px-2 py-0.5 rounded font-bold uppercase"
                      [class]="v.status === 'CONCLUIDA' ? 'bg-green-900/40 text-green-400' : v.status === 'CANCELADA' ? 'bg-red-900/40 text-red-400' : 'bg-yellow-900/40 text-yellow-400'">
                      {{ v.status }}
                    </span>
                  </td>
                  <td class="px-4 py-3 text-xs text-ck-muted">{{ v.itens?.length || 0 }} item(ns)</td>
                  <td class="px-4 py-3 text-right font-bold text-ck-price text-sm">R$ {{ v.valorTotal | number:'1.2-2':'pt-BR' }}</td>
                  <td class="px-4 py-3 text-right">
                    <button *ngIf="v.status !== 'CANCELADA'" (click)="cancelarVenda(v)"
                      class="text-[11px] text-red-400 hover:text-red-300 font-semibold border border-red-900 hover:border-red-500 px-2 py-1 rounded transition-colors">
                      Cancelar
                    </button>
                    <span *ngIf="v.status === 'CANCELADA'" class="text-[11px] text-ck-muted italic">{{ v.motivoCancelamento }}</span>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

        <!-- ESTOQUE -->
        <div *ngIf="activeTab === 'estoque'">
          <div class="flex items-center gap-3 mb-6">
            <div class="w-1 h-6 bg-ck-accent rounded-full"></div>
            <h1 class="text-xl font-bold text-white">Controle de Estoque</h1>
          </div>

          <div *ngIf="estoquesCriticos.length > 0" class="mb-5 p-3 bg-amber-900/20 border border-amber-700 rounded flex items-center gap-2 text-amber-400 text-sm font-medium">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="m21.73 18-8-14a2 2 0 0 0-3.48 0l-8 14A2 2 0 0 0 4 21h16a2 2 0 0 0 1.73-3Z"/><path d="M12 9v4"/><path d="M12 17h.01"/></svg>
            {{ estoquesCriticos.length }} produto(s) com estoque abaixo do mínimo
          </div>

          <!-- Repor estoque form -->
          <div class="bg-ck-surface border border-ck-border rounded p-5 mb-5">
            <h2 class="text-sm font-bold text-white uppercase mb-4">Reposição Manual de Estoque</h2>
            <div class="flex gap-3 flex-wrap">
              <select [(ngModel)]="reporProdutoId"
                class="flex-1 min-w-[200px] px-3 py-2 text-sm bg-[#111] border border-ck-border text-ck-text rounded focus:outline-none focus:border-ck-accent">
                <option value="">-- Selecione um produto --</option>
                <option *ngFor="let e of estoquesCriticos" [value]="e.produto?.id">
                  #{{ e.produto?.id }} — {{ e.produto?.descricao }} ({{ e.quantidade }} un.)
                </option>
              </select>
              <input type="number" [(ngModel)]="reporQtd" min="1" placeholder="Quantidade"
                class="w-32 px-3 py-2 text-sm bg-[#111] border border-ck-border text-ck-text rounded focus:outline-none focus:border-ck-accent" />
              <button (click)="reporEstoque()"
                class="px-4 py-2 bg-green-700 hover:bg-green-600 text-white text-sm font-semibold rounded uppercase tracking-wide transition-colors">
                Repor Estoque
              </button>
            </div>
          </div>

          <!-- Critical stock table -->
          <div class="bg-ck-surface border border-ck-border rounded overflow-hidden">
            <div class="px-4 py-3 border-b border-ck-border bg-[#111] text-[11px] text-ck-muted uppercase font-semibold">
              Produtos em Estoque Crítico
            </div>
            <div *ngIf="estoquesCriticos.length === 0" class="px-4 py-10 text-center text-ck-muted text-xs">
              ✅ Todos os produtos estão com estoque normal.
            </div>
            <div *ngFor="let e of estoquesCriticos" class="flex items-center justify-between px-4 py-3 border-b border-ck-border/50">
              <div>
                <p class="text-sm text-ck-text font-medium">{{ e.produto?.descricao }}</p>
                <p class="text-xs text-ck-muted">SKU: {{ e.produto?.sku }}</p>
              </div>
              <div class="text-right">
                <p class="text-lg font-bold text-amber-400">{{ e.quantidade }}</p>
                <p class="text-[10px] text-ck-muted">mín: {{ e.estoque_minimo }}</p>
              </div>
            </div>
          </div>
        </div>

        <!-- FORNECEDORES -->
        <div *ngIf="activeTab === 'fornecedores'">
          <div class="flex items-center justify-between mb-6">
            <div class="flex items-center gap-3">
              <div class="w-1 h-6 bg-ck-accent rounded-full"></div>
              <h1 class="text-xl font-bold text-white">Fornecedores</h1>
            </div>
            <button (click)="showFornecedorForm = !showFornecedorForm"
              class="px-4 py-2 bg-ck-accent hover:bg-ck-accentHover text-white text-sm font-semibold rounded uppercase tracking-wide transition-colors flex items-center gap-2">
              <svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><line x1="12" y1="5" x2="12" y2="19"/><line x1="5" y1="12" x2="19" y2="12"/></svg>
              Novo Fornecedor
            </button>
          </div>

          <!-- New Fornecedor form -->
          <div *ngIf="showFornecedorForm" class="bg-ck-surface border border-ck-border rounded p-5 mb-5">
            <h2 class="text-sm font-bold text-white uppercase mb-4">Cadastrar Fornecedor</h2>
            <div class="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div>
                <label class="block text-[11px] text-ck-muted uppercase font-semibold mb-1">Razão Social *</label>
                <input [(ngModel)]="novoFornecedor.nome"
                  class="w-full px-3 py-2 text-sm bg-[#111] border border-ck-border text-ck-text rounded focus:outline-none focus:border-ck-accent" />
              </div>
              <div>
                <label class="block text-[11px] text-ck-muted uppercase font-semibold mb-1">CNPJ *</label>
                <input [(ngModel)]="novoFornecedor.cnpj" placeholder="00.000.000/0001-00"
                  class="w-full px-3 py-2 text-sm bg-[#111] border border-ck-border text-ck-text rounded focus:outline-none focus:border-ck-accent" />
              </div>
              <div>
                <label class="block text-[11px] text-ck-muted uppercase font-semibold mb-1">E-mail</label>
                <input [(ngModel)]="novoFornecedor.email"
                  class="w-full px-3 py-2 text-sm bg-[#111] border border-ck-border text-ck-text rounded focus:outline-none focus:border-ck-accent" />
              </div>
              <div>
                <label class="block text-[11px] text-ck-muted uppercase font-semibold mb-1">Telefone</label>
                <input [(ngModel)]="novoFornecedor.telefone"
                  class="w-full px-3 py-2 text-sm bg-[#111] border border-ck-border text-ck-text rounded focus:outline-none focus:border-ck-accent" />
              </div>
              <div class="md:col-span-2">
                <label class="block text-[11px] text-ck-muted uppercase font-semibold mb-1">Endereço</label>
                <input [(ngModel)]="novoFornecedor.endereco"
                  class="w-full px-3 py-2 text-sm bg-[#111] border border-ck-border text-ck-text rounded focus:outline-none focus:border-ck-accent" />
              </div>
            </div>
            <div class="flex gap-3 mt-4">
              <button (click)="criarFornecedor()"
                class="px-5 py-2 bg-ck-accent hover:bg-ck-accentHover text-white text-sm font-semibold rounded uppercase tracking-wide transition-colors">
                Salvar
              </button>
              <button (click)="showFornecedorForm = false"
                class="px-5 py-2 bg-ck-surface border border-ck-border text-ck-muted hover:text-white text-sm rounded transition-colors">
                Cancelar
              </button>
            </div>
          </div>

          <!-- Fornecedores table -->
          <div class="bg-ck-surface border border-ck-border rounded overflow-hidden">
            <table class="w-full text-sm">
              <thead class="bg-[#111] border-b border-ck-border">
                <tr>
                  <th class="px-4 py-3 text-left text-[11px] text-ck-muted uppercase font-semibold">Razão Social</th>
                  <th class="px-4 py-3 text-left text-[11px] text-ck-muted uppercase font-semibold">CNPJ</th>
                  <th class="px-4 py-3 text-left text-[11px] text-ck-muted uppercase font-semibold">E-mail</th>
                  <th class="px-4 py-3 text-left text-[11px] text-ck-muted uppercase font-semibold">Telefone</th>
                  <th class="px-4 py-3 text-right text-[11px] text-ck-muted uppercase font-semibold">Ações</th>
                </tr>
              </thead>
              <tbody>
                <tr *ngIf="fornecedores.length === 0"><td colspan="5" class="px-4 py-10 text-center text-ck-muted text-xs">Nenhum fornecedor cadastrado.</td></tr>
                <tr *ngFor="let f of fornecedores" class="border-b border-ck-border/50 hover:bg-ck-surface/50">
                  <td class="px-4 py-3 font-medium text-ck-text">{{ f.nome }}</td>
                  <td class="px-4 py-3 text-ck-muted font-mono text-xs">{{ f.cnpj }}</td>
                  <td class="px-4 py-3 text-ck-muted text-xs">{{ f.email }}</td>
                  <td class="px-4 py-3 text-ck-muted text-xs">{{ f.telefone }}</td>
                  <td class="px-4 py-3 text-right">
                    <button (click)="deletarFornecedor(f.id)"
                      class="text-[11px] text-red-400 hover:text-red-300 font-semibold border border-red-900 hover:border-red-500 px-2 py-1 rounded transition-colors">
                      Remover
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

      </main>
    </div>
  `,
})
export class AdminComponent implements OnInit {
  activeTab: Tab = 'dashboard';
  vendas: any[] = [];
  fornecedores: any[] = [];
  estoquesCriticos: any[] = [];
  totalProdutos = 0;
  loadingVendas = true;
  showFornecedorForm = false;
  reporProdutoId = '';
  reporQtd = 1;
  novoFornecedor = { nome: '', cnpj: '', email: '', telefone: '', endereco: '' };

  navItems = [
    { tab: 'dashboard' as Tab, label: 'Dashboard', icon: '<svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect width="7" height="9" x="3" y="3" rx="1"/><rect width="7" height="5" x="14" y="3" rx="1"/><rect width="7" height="9" x="14" y="12" rx="1"/><rect width="7" height="5" x="3" y="16" rx="1"/></svg>' },
    { tab: 'vendas' as Tab, label: 'Vendas', icon: '<svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="8" cy="21" r="1"/><circle cx="19" cy="21" r="1"/><path d="M2.05 2.05h2l2.66 12.42a2 2 0 0 0 2 1.58h9.78a2 2 0 0 0 1.95-1.57l1.65-7.43H5.12"/></svg>' },
    { tab: 'estoque' as Tab, label: 'Estoque', icon: '<svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M21 16V8a2 2 0 0 0-1-1.73l-7-4a2 2 0 0 0-2 0l-7 4A2 2 0 0 0 3 8v8a2 2 0 0 0 1 1.73l7 4a2 2 0 0 0 2 0l7-4A2 2 0 0 0 21 16z"/></svg>' },
    { tab: 'fornecedores' as Tab, label: 'Fornecedores', icon: '<svg xmlns="http://www.w3.org/2000/svg" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/><path d="M23 21v-2a4 4 0 0 0-3-3.87"/><path d="M16 3.13a4 4 0 0 1 0 7.75"/></svg>' },
  ];

  constructor(
    public authService: AuthService,
    private api: ApiService,
    private toast: ToastService,
    private route: ActivatedRoute,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      if (params['tab'] && ['dashboard','vendas','estoque','fornecedores'].includes(params['tab'])) {
        this.activeTab = params['tab'] as Tab;
      }
    });
    this.loadAll();
  }

  loadAll() {
    this.api.getVendas().subscribe({ next: (v: any) => { this.vendas = Array.isArray(v) ? v : []; this.loadingVendas = false; }, error: () => this.loadingVendas = false });
    this.api.getFornecedores().subscribe({ next: (f: any[]) => this.fornecedores = f, error: () => {} });
    this.api.getEstoquesCriticos().subscribe({ next: (e: any[]) => this.estoquesCriticos = e, error: () => {} });
    this.api.getProdutos(0, 100).subscribe({ next: (p: any) => this.totalProdutos = p.page?.totalElements ?? p.totalElements ?? p.content?.length ?? 0, error: () => {} });
  }

  cancelarVenda(v: any) {
    const motivo = prompt('Motivo do cancelamento:');
    if (!motivo) return;
    this.api.cancelarVenda(v.id, motivo).subscribe({
      next: () => { this.toast.success(`Venda #${v.id} cancelada.`); v.status = 'CANCELADA'; v.motivoCancelamento = motivo; },
      error: () => this.toast.error('Erro ao cancelar venda.')
    });
  }

  reporEstoque() {
    if (!this.reporProdutoId || !this.reporQtd) { this.toast.error('Selecione o produto e a quantidade.'); return; }
    this.api.reporEstoque(+this.reporProdutoId, this.reporQtd).subscribe({
      next: () => {
        this.toast.success('Estoque reposto com sucesso!');
        this.reporProdutoId = ''; this.reporQtd = 1;
        this.api.getEstoquesCriticos().subscribe({ next: (e: any[]) => this.estoquesCriticos = e });
      },
      error: () => this.toast.error('Erro ao repor estoque.')
    });
  }

  criarFornecedor() {
    if (!this.novoFornecedor.nome || !this.novoFornecedor.cnpj) { this.toast.error('Nome e CNPJ são obrigatórios.'); return; }
    this.api.criarFornecedor(this.novoFornecedor).subscribe({
      next: (f: any) => {
        this.fornecedores = [...this.fornecedores, f];
        this.toast.success(`Fornecedor "${f.nome}" cadastrado!`);
        this.novoFornecedor = { nome: '', cnpj: '', email: '', telefone: '', endereco: '' };
        this.showFornecedorForm = false;
        this.cdr.detectChanges();
      },
      error: (e: any) => this.toast.error(e.error?.message || 'Erro ao cadastrar fornecedor.')
    });
  }

  deletarFornecedor(id: number) {
    if (!confirm('Tem certeza que deseja remover este fornecedor?')) return;
    this.api.deletarFornecedor(id).subscribe({
      next: () => { this.fornecedores = this.fornecedores.filter(f => f.id !== id); this.toast.success('Fornecedor removido.'); },
      error: () => this.toast.error('Erro ao remover fornecedor.')
    });
  }
}
