export type EstoqueMovimentoTipo = 'IN' | 'OUT';

export interface EstoqueItem {
  id: number;
  produtoId: number;
  produtoNome?: string;
  quantidade: number;
  local?: string;
  disponivel: boolean;
  criadoEm?: string;
  atualizadoEm?: string;
}

export interface EstoqueRequest {
  produtoId: number;
  quantidade: number;
  tipo: EstoqueMovimentoTipo;
  referenciaId?: number;
  observacao?: string;
  local?: string;
}

export interface EstoqueTransferRequest {
  produtoId: number;
  fromLocation: string;
  toLocation: string;
  quantidade: number;
}

export interface EstoqueSaldo {
  produtoId: number;
  quantidadeDisponivel: number;
  reservado?: number;
}