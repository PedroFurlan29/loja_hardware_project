export type UsuarioRole = 'ADMIN' | 'ESTOQUE' | 'VENDA' | 'USER';

export interface Usuario {
  id: number;
  nome: string;
  email: string;
  roles: UsuarioRole[];
  ativo: boolean;
  criadoEm?: string;
  atualizadoEm?: string;
}

export interface UsuarioCreateRequest {
  nome: string;
  email: string;
  senha: string;
  roles: UsuarioRole[];
}

export interface UsuarioUpdateRequest {
  nome?: string;
  email?: string;
  roles?: UsuarioRole[];
  ativo?: boolean;
}