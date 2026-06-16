import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <footer class="bg-[#0a0a0a] border-t border-ck-border text-ck-muted mt-16">
      <div class="max-w-[1400px] mx-auto px-4 py-14 grid grid-cols-1 md:grid-cols-4 gap-10">
        
        <div>
          <a routerLink="/" class="flex items-center gap-2 mb-4">
            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="#2563eb" stroke-width="2"><rect width="18" height="18" x="3" y="3" rx="2"/><path d="M3 9h18"/><path d="M9 21V9"/><path d="m14 14 3-3-3-3"/></svg>
            <span class="text-white font-bold">Hardware<span class="text-ck-accent">Store</span></span>
          </a>
          <p class="text-sm leading-relaxed text-[#555]">
            A loja de hardware de alta performance do Brasil. CPUs, GPUs, memórias e armazenamento.
          </p>
        </div>

        <div>
          <h3 class="text-white font-bold uppercase text-xs tracking-widest mb-4">Produtos</h3>
          <ul class="space-y-2 text-sm">
            <li><a routerLink="/produtos" class="hover:text-white transition-colors">Processadores</a></li>
            <li><a routerLink="/produtos" class="hover:text-white transition-colors">Placas de Vídeo</a></li>
            <li><a routerLink="/produtos" class="hover:text-white transition-colors">Memórias</a></li>
            <li><a routerLink="/produtos" class="hover:text-white transition-colors">SSDs & HDs</a></li>
          </ul>
        </div>

        <div>
          <h3 class="text-white font-bold uppercase text-xs tracking-widest mb-4">Suporte</h3>
          <ul class="space-y-2 text-sm">
            <li><a href="#" class="hover:text-white transition-colors">FAQ</a></li>
            <li><a href="#" class="hover:text-white transition-colors">Entregas</a></li>
            <li><a href="#" class="hover:text-white transition-colors">Garantia</a></li>
            <li><a href="#" class="hover:text-white transition-colors">Trocas</a></li>
          </ul>
        </div>

        <div>
          <h3 class="text-white font-bold uppercase text-xs tracking-widest mb-4">Institucional</h3>
          <ul class="space-y-2 text-sm">
            <li><a href="#" class="hover:text-white transition-colors">Sobre nós</a></li>
            <li><a href="#" class="hover:text-white transition-colors">Privacidade</a></li>
            <li><a href="#" class="hover:text-white transition-colors">Termos de uso</a></li>
            <li><a routerLink="/login" class="hover:text-white transition-colors">Área do Vendedor</a></li>
          </ul>
        </div>

      </div>
      <div class="border-t border-ck-border py-5 text-center text-[#444] text-xs max-w-[1400px] mx-auto px-4">
        © 2026 HardwareStore. Todos os direitos reservados. CNPJ: 00.000.000/0001-00
      </div>
    </footer>
  `,
})
export class FooterComponent {}
