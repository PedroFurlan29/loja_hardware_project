import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { EstoqueItem, EstoqueRequest, EstoqueSaldo, EstoqueTransferRequest } from '../models/estoque.models';
import { ApiListResponse } from '../models/common.models';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class StockService {
  private apiUrl = `${environment.apiUrl}/estoque`;

  constructor(private http: HttpClient) {}

  getEstoque(filters?: any): Observable<ApiListResponse<EstoqueItem>> {
    let params = new HttpParams();
    if (filters) {
      if (filters.produtoId !== undefined) params = params.set('produtoId', filters.produtoId.toString());
      if (filters.page !== undefined) params = params.set('page', filters.page.toString());
      if (filters.size !== undefined) params = params.set('size', filters.size.toString());
    }
    return this.http.get<ApiListResponse<EstoqueItem>>(this.apiUrl, { params });
  }

  getEstoqueItem(id: number): Observable<EstoqueItem> {
    return this.http.get<EstoqueItem>(`${this.apiUrl}/${id}`);
  }

  getEstoquePorProduto(produtoId: number): Observable<EstoqueItem[]> {
    return this.http.get<EstoqueItem[]>(`${this.apiUrl}?produtoId=${produtoId}`);
  }

  updateEstoque(id: number, updates: Partial<EstoqueItem>): Observable<EstoqueItem> {
    return this.http.put<EstoqueItem>(`${this.apiUrl}/${id}`, updates);
  }

  getSaldoProduto(produtoId: number): Observable<EstoqueSaldo> {
    return this.http.get<EstoqueSaldo>(`${this.apiUrl}/saldo/${produtoId}`);
  }

  registrarMovimento(request: EstoqueRequest): Observable<EstoqueItem> {
    return this.http.post<EstoqueItem>(this.apiUrl, request);
  }

  transferir(request: EstoqueTransferRequest): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/transferir`, request);
  }
}
