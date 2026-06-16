export type ProdutoStatus = 'ATIVO' | 'INATIVO';

export interface Produto {
  id: number;
  nome: string;
  descricao?: string;
  preco: number;
  sku: string;
  categoria?: string;
  estoqueMinimo?: number;
  quantidadeDisponivel?: number;
  ativo: boolean;
  imagemUrl?: string;
  criadoEm?: string;
  atualizadoEm?: string;
}

export interface ProdutoCreateRequest {
  nome: string;
  descricao?: string;
  preco: number;
  sku: string;
  categoria?: string;
  estoqueMinimo?: number;
  ativo: boolean;
}

export interface ProdutoUpdateRequest {
  nome?: string;
  descricao?: string;
  preco?: number;
  sku?: string;
  categoria?: string;
  estoqueMinimo?: number;
  ativo?: boolean;
}