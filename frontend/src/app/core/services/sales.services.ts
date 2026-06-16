import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Venda, VendaCreateRequest, VendaUpdateRequest } from '../models/venda.models';
import { ApiListResponse } from '../models/common.models';
import { environment } from '../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class SalesService {
  private apiUrl = `${environment.apiUrl}/vendas`;

  constructor(private http: HttpClient) {}

  getVendas(filters?: any): Observable<ApiListResponse<Venda>> {
    let params = new HttpParams();
    if (filters) {
      if (filters.dataInicio) params = params.set('dataInicio', filters.dataInicio);
      if (filters.dataFim) params = params.set('dataFim', filters.dataFim);
      if (filters.status) params = params.set('status', filters.status);
      if (filters.page !== undefined) params = params.set('page', filters.page.toString());
      if (filters.size !== undefined) params = params.set('size', filters.size.toString());
    }
    return this.http.get<ApiListResponse<Venda>>(this.apiUrl, { params });
  }

  getVendaPorId(id: number): Observable<Venda> {
    return this.http.get<Venda>(`${this.apiUrl}/${id}`);
  }

  createVenda(venda: VendaCreateRequest): Observable<Venda> {
    return this.http.post<Venda>(this.apiUrl, venda);
  }

  updateVenda(id: number, venda: VendaUpdateRequest): Observable<Venda> {
    return this.http.put<Venda>(`${this.apiUrl}/${id}`, venda);
  }

  confirmVenda(id: number): Observable<Venda> {
    return this.http.post<Venda>(`${this.apiUrl}/${id}/confirm`, {});
  }

  getVendasPorStatus(status: string): Observable<Venda[]> {
    return this.http.get<Venda[]>(`${this.apiUrl}/status/${encodeURIComponent(status)}`);
  }

  cancelarVenda(id: number, motivo?: string): Observable<void> {
    const body = motivo ? { motivo } : {};
    return this.http.post<void>(`${this.apiUrl}/${id}/cancel`, body);
  }
}
