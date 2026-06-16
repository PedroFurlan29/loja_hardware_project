import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

interface Fornecedor {
  id: number;
  nome: string;
  email: string;
  telefone?: string;
  endereco?: string;
  cidade?: string;
  estado?: string;
  cep?: string;
  contato?: string;
  ativo: boolean;
  criadoEm?: string;
  atualizadoEm?: string;
}

interface FornecedorCreateRequest {
  nome: string;
  email: string;
  telefone?: string;
  endereco?: string;
  cidade?: string;
  estado?: string;
  cep?: string;
  contato?: string;
}

interface FornecedorUpdateRequest {
  nome?: string;
  email?: string;
  telefone?: string;
  endereco?: string;
  cidade?: string;
  estado?: string;
  cep?: string;
  contato?: string;
  ativo?: boolean;
}

@Injectable({ providedIn: 'root' })
export class FornecedoresService {
    
    private apiUrl = `${environment.apiUrl}/fornecedores`;

    constructor(private http: HttpClient) {}

    getFornecedores(): Observable<Fornecedor[]> {
        return this.http.get<Fornecedor[]>(this.apiUrl);
    }

    getFornecedorPorId(id: number): Observable<Fornecedor> {
        return this.http.get<Fornecedor>(`${this.apiUrl}/${id}`);
    }

    createFornecedor(fornecedor: FornecedorCreateRequest): Observable<Fornecedor> {
        return this.http.post<Fornecedor>(this.apiUrl, fornecedor);
    }

    updateFornecedor(id: number, fornecedor: FornecedorUpdateRequest): Observable<Fornecedor> {
        return this.http.put<Fornecedor>(`${this.apiUrl}/${id}`, fornecedor);
    }

    deleteFornecedor(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
}