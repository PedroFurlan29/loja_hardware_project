import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <footer class="bg-[#0a0a0a] border-t border-ck-border text-ck-muted mt-16">
      <div class="max-w-[1400px] mx-auto px-4 py-14 grid grid-cols-1 md:grid-cols-3 gap-10">
        
        <div>
          <a routerLink="/" class="flex items-center gap-2 mb-4">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 500 500" width="28" height="28">
              <defs>
                <linearGradient id="ftGrad" x1="0%" y1="0%" x2="100%" y2="100%">
                  <stop offset="0%" style="stop-color:#0ea5e9;stop-opacity:1" />
                  <stop offset="100%" style="stop-color:#2563eb;stop-opacity:1" />
                </linearGradient>
              </defs>
              <rect x="125" y="100" width="250" height="250" rx="30" fill="none" stroke="url(#ftGrad)" stroke-width="20"/>
              <rect x="175" y="150" width="150" height="150" rx="15" fill="url(#ftGrad)"/>
              <path d="M 175 100 V 60 M 212.5 100 V 60 M 250 100 V 60 M 287.5 100 V 60 M 325 100 V 60" stroke="#64748b" stroke-width="12" stroke-linecap="round"/>
              <path d="M 175 350 V 390 M 212.5 350 V 390 M 250 350 V 390 M 287.5 350 V 390 M 325 350 V 390" stroke="#64748b" stroke-width="12" stroke-linecap="round"/>
            </svg>
            <span class="text-white font-bold">Hardware<span class="text-ck-accent">Store</span></span>
          </a>
          <p class="text-sm leading-relaxed text-[#555]">
            A loja de hardware de alta performance do Brasil. CPUs, GPUs, memórias e armazenamento.
          </p>
        </div>

        <div>
          <h3 class="text-white font-bold uppercase text-xs tracking-widest mb-4">Produtos</h3>
          <ul class="space-y-2 text-sm">
            <li><a routerLink="/produtos" [queryParams]="{categoria:'CPU', label:'Processadores'}" class="hover:text-white transition-colors">Processadores</a></li>
            <li><a routerLink="/produtos" [queryParams]="{categoria:'GPU', label:'Placas de Vídeo'}" class="hover:text-white transition-colors">Placas de Vídeo</a></li>
            <li><a routerLink="/produtos" [queryParams]="{categoria:'RAM', label:'Memórias RAM'}" class="hover:text-white transition-colors">Memórias</a></li>
            <li><a routerLink="/produtos" [queryParams]="{categoria:'SSD', label:'SSDs & HDs'}" class="hover:text-white transition-colors">SSDs & HDs</a></li>
          </ul>
        </div>

        <div>
          <h3 class="text-white font-bold uppercase text-xs tracking-widest mb-4">Conta</h3>
          <ul class="space-y-2 text-sm">
            <li><a routerLink="/login" class="hover:text-white transition-colors">Entrar</a></li>
            <li><a routerLink="/registrar" class="hover:text-white transition-colors">Criar Conta</a></li>
            <li><a routerLink="/admin/vendas" class="hover:text-white transition-colors">Área do Vendedor</a></li>
          </ul>
        </div>

      </div>
      <div class="border-t border-ck-border py-5 text-center text-[#444] text-xs max-w-[1400px] mx-auto px-4">
        © 2026 HardwareStore. Todos os direitos reservados.
      </div>
    </footer>
  `,
})
export class FooterComponent {}
