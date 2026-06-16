export interface UploadResponse {
  id: number;
  url: string;
  tipo: string;
  nome: string;
  tamanho: number;
  criadoEm?: string;
}

export interface ApiPage<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  first: boolean;
  last: boolean;
}

export interface ApiListResponse<T> {
  data: T[];
  count: number;
  page?: number;
  size?: number;
}

export interface FieldError {
  field: string;
  message: string;
}

export interface ApiError {
  timestamp?: string;
  status: number;
  error: string;
  message: string;
  path?: string;
  errors?: FieldError[];
}

export interface SearchFilter {
  field: string;
  value: string | number | boolean;
}

export interface SearchRequest {
  q?: string;
  page?: number;
  size?: number;
  sort?: string;
  filters?: SearchFilter[];
}
