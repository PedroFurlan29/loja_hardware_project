export type VendaStatus = 'PENDENTE' | 'CONFIRMADA' | 'CANCELADA' | 'ENTREGUE';

export interface VendaItem {
  produtoId: number;
  nome?: string;
  quantidade: number;
  precoUnitario: number;
  subtotal?: number;
}

export interface PagamentoInfo {
  tipo: 'DINHEIRO' | 'CARTAO' | 'PIX' | 'OUTRO';
  status: 'PENDENTE' | 'APROVADO' | 'CANCELADO';
  detalhes?: string;
}

export interface Venda {
  id: number;
  clienteId?: number;
  clienteNome?: string;
  itens: VendaItem[];
  total: number;
  desconto?: number;
  status: VendaStatus;
  pagamento: PagamentoInfo;
  criadoEm?: string;
  atualizadoEm?: string;
}

export interface VendaCreateRequest {
  clienteId?: number;
  itens: VendaItem[];
  desconto?: number;
  pagamento: PagamentoInfo;
}

export interface VendaUpdateRequest {
  status?: VendaStatus;
  desconto?: number;
  pagamento?: Partial<PagamentoInfo>;
}