import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

const API_URL = environment.apiUrl;

@Injectable({ providedIn: 'root' })
export class ApiService {
  constructor(private http: HttpClient) {}

  login(email: string, senha: string): Observable<{ token: string; type: string }> {
    return this.http.post<{ token: string; type: string }>(`${API_URL}/auth/login`, { email, senha });
  }

  getMe(): Observable<any> {
    return this.http.get(`${API_URL}/auth/me`);
  }

  getVendedores(): Observable<any[]> {
    return this.http.get<any[]>(`${API_URL}/auth/vendedores`);
  }

  getProdutos(page = 0, size = 20): Observable<any> {
    return this.http.get(`${API_URL}/produtos?p=${page}&s=${size}`);
  }

  getProdutoById(id: number): Observable<any> {
    return this.http.get(`${API_URL}/produtos/${id}`);
  }

  criarProduto(produto: any): Observable<any> {
    return this.http.post(`${API_URL}/produtos`, produto);
  }

  atualizarProduto(id: number, produto: any): Observable<any> {
    return this.http.put(`${API_URL}/produtos/${id}`, produto);
  }

  getEstoque(produtoId: number): Observable<any> {
    return this.http.get(`${API_URL}/estoque/${produtoId}`);
  }

  getEstoquesCriticos(): Observable<any[]> {
    return this.http.get<any[]>(`${API_URL}/estoque/criticos`);
  }

  reporEstoque(produtoId: number, quantidade: number): Observable<any> {
    return this.http.put(`${API_URL}/estoque/${produtoId}/repor`, { quantidade });
  }

  baixarEstoque(produtoId: number, quantidade: number): Observable<any> {
    return this.http.put(`${API_URL}/estoque/${produtoId}/baixar`, { quantidade });
  }

  registrarVenda(itens: { produtoId: number; quantidade: number }[], vendedorId?: number): Observable<any> {
    const body: any = { itens };
    if (vendedorId) body.vendedorId = vendedorId;
    return this.http.post(`${API_URL}/vendas`, body);
  }

  getVendas(): Observable<any> {
    return this.http.get(`${API_URL}/vendas`);
  }

  getMinhasVendas(): Observable<any> {
    return this.http.get(`${API_URL}/vendas/minhas`);
  }

  getVendasPorVendedor(id: number): Observable<any> {
    return this.http.get(`${API_URL}/vendas/vendedor/${id}`);
  }

  getVendasTotais(): Observable<any> {
    return this.http.get(`${API_URL}/vendas/totais`);
  }

  getVendaById(id: number): Observable<any> {
    return this.http.get(`${API_URL}/vendas/${id}`);
  }

  cancelarVenda(id: number, motivo: string): Observable<any> {
    return this.http.post(`${API_URL}/vendas/${id}/cancelar`, { motivo });
  }

  getFornecedores(): Observable<any[]> {
    return this.http.get<any[]>(`${API_URL}/fornecedores`);
  }

  getFornecedorById(id: number): Observable<any> {
    return this.http.get(`${API_URL}/fornecedores/${id}`);
  }

  criarFornecedor(f: any): Observable<any> {
    return this.http.post(`${API_URL}/fornecedores`, f);
  }

  atualizarFornecedor(id: number, f: any): Observable<any> {
    return this.http.put(`${API_URL}/fornecedores/${id}`, f);
  }

  deletarFornecedor(id: number): Observable<any> {
    return this.http.delete(`${API_URL}/fornecedores/${id}`);
  }
}