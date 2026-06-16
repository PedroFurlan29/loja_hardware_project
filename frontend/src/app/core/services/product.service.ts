import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Produto, ProdutoCreateRequest, ProdutoUpdateRequest } from '../models/produto.models';
import { ApiListResponse } from '../models/common.models';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class ProductService {
  private apiUrl = `${environment.apiUrl}/produtos`;

  constructor(private http: HttpClient) {}
  
  getProdutos(page?: number, size?: number, filters?: any): Observable<ApiListResponse<Produto>> {
    let params = new HttpParams();
    if (page !== undefined) params = params.set('page', page.toString());
    if (size !== undefined) params = params.set('size', size.toString());
    if (filters) {
      Object.keys(filters).forEach(key => {
        if (filters[key] !== undefined) {
          params = params.set(key, filters[key]);
        }
      });
    }
    return this.http.get<ApiListResponse<Produto>>(this.apiUrl, { params });
  }

  getProdutoPorId(id: number): Observable<Produto> {
    return this.http.get<Produto>(`${this.apiUrl}/${id}`);
  }

  getPorCategoria(nomeCategoria: string): Observable<Produto[]> {
    return this.http.get<Produto[]>(`${this.apiUrl}?categoria=${encodeURIComponent(nomeCategoria)}`);
  }

  getProdutoPorSku(sku: string): Observable<Produto> {
    return this.http.get<Produto>(`${this.apiUrl}?sku=${encodeURIComponent(sku)}`);
  }
  
  createProduto(produto: ProdutoCreateRequest): Observable<Produto> {
    return this.http.post<Produto>(this.apiUrl, produto);
  }

  updateProduto(id: number, produto: ProdutoUpdateRequest): Observable<Produto> {
    return this.http.put<Produto>(`${this.apiUrl}/${id}`, produto);
  }

  updateParcial(id: number, updates: Partial<ProdutoUpdateRequest>): Observable<Produto> {
    return this.http.patch<Produto>(`${this.apiUrl}/${id}`, updates);
  }

  deleteProduto(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  searchProdutos(term: string, page?: number, size?: number): Observable<ApiListResponse<Produto>> {
    let params = new HttpParams().set('q', term);
    if (page !== undefined) params = params.set('page', page.toString());
    if (size !== undefined) params = params.set('size', size.toString());
    return this.http.get<ApiListResponse<Produto>>(this.apiUrl, { params });
  }
}