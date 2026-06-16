import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { Produto, ProdutoCreateRequest, ProdutoUpdateRequest } from '../models/produto.models';
import { ApiListResponse, ApiPage } from '../models/common.models';
import { environment } from '../../../environments/environment';

interface BackendProduto {
  id: number;
  sku: string;
  descricao: string;
  precoVenda: number;
  precoOferta?: number;
  quantidade: number;
  estoque_minimo: number;
  imagemUrl?: string;
  marca?: string;
  modelo?: string;
  nucleos?: number;
  frequenciaGHz?: number;
  socket?: string;
  arquitetura?: string;
  descricaoTecnica?: string;
  estoqueCritico?: boolean;
  emOferta?: boolean;
  custoAquisicao?: number;
}

@Injectable({ providedIn: 'root' })
export class ProductService {
  private apiUrl = `${environment.apiUrl}/produtos`;

  constructor(private http: HttpClient) {}

  getProdutos(page?: number, size?: number, filters?: any): Observable<ApiListResponse<Produto>> {
    let params = new HttpParams();
    if (page !== undefined) params = params.set('p', page.toString());
    if (size !== undefined) params = params.set('s', size.toString());
    if (filters) {
      Object.keys(filters).forEach(key => {
        if (filters[key] !== undefined) {
          params = params.set(key, filters[key]);
        }
      });
    }
    
    return this.http.get<ApiPage<BackendProduto>>(this.apiUrl, { params }).pipe(
      map(response => this.mapPageResponse(response))
    );
  }

  getProdutoPorId(id: number): Observable<Produto> {
    return this.http.get<BackendProduto>(`${this.apiUrl}/${id}`).pipe(
      map(p => this.mapBackendToFrontend(p))
    );
  }

  getPorCategoria(nomeCategoria: string): Observable<Produto[]> {
    return this.http.get<ApiPage<BackendProduto>>(`${this.apiUrl}?categoria=${encodeURIComponent(nomeCategoria)}`).pipe(
      map(response => response.content.map(p => this.mapBackendToFrontend(p)))
    );
  }

  getProdutoPorSku(sku: string): Observable<Produto> {
    return this.http.get<BackendProduto>(`${this.apiUrl}?sku=${encodeURIComponent(sku)}`).pipe(
      map(p => this.mapBackendToFrontend(p))
    );
  }

  createProduto(produto: ProdutoCreateRequest): Observable<Produto> {
    return this.http.post<BackendProduto>(this.apiUrl, produto).pipe(
      map(p => this.mapBackendToFrontend(p))
    );
  }

  updateProduto(id: number, produto: ProdutoUpdateRequest): Observable<Produto> {
    return this.http.put<BackendProduto>(`${this.apiUrl}/${id}`, produto).pipe(
      map(p => this.mapBackendToFrontend(p))
    );
  }

  updateParcial(id: number, updates: Partial<ProdutoUpdateRequest>): Observable<Produto> {
    return this.http.patch<BackendProduto>(`${this.apiUrl}/${id}`, updates).pipe(
      map(p => this.mapBackendToFrontend(p))
    );
  }

  deleteProduto(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  searchProdutos(term: string, page?: number, size?: number): Observable<ApiListResponse<Produto>> {
    let params = new HttpParams().set('q', term);
    if (page !== undefined) params = params.set('p', page.toString());
    if (size !== undefined) params = params.set('s', size.toString());
    return this.http.get<ApiPage<BackendProduto>>(this.apiUrl, { params }).pipe(
      map(response => this.mapPageResponse(response))
    );
  }

  private mapPageResponse(response: ApiPage<BackendProduto>): ApiListResponse<Produto> {
    return {
      data: response.content.map(p => this.mapBackendToFrontend(p)),
      count: response.totalElements,
      page: response.number,
      size: response.size
    };
  }

  private mapBackendToFrontend(p: BackendProduto): Produto {
    return {
      id: p.id,
      nome: p.descricao,
      descricao: p.descricaoTecnica || p.descricao,
      preco: p.precoOferta || p.precoVenda,
      sku: p.sku,
      categoria: this.inferCategoria(p.sku),
      estoqueMinimo: p.estoque_minimo,
      quantidadeDisponivel: p.quantidade,
      ativo: true,
      imagemUrl: p.imagemUrl,
      criadoEm: undefined,
      atualizadoEm: undefined
    };
  }

  private inferCategoria(sku: string): string {
    if (sku.startsWith('CPU')) return 'CPU';
    if (sku.startsWith('GPU')) return 'GPU';
    if (sku.startsWith('RAM')) return 'RAM';
    if (sku.startsWith('SSD')) return 'SSD';
    return 'Outros';
  }
}