import { Usuario } from '../models/usuario.models';

export interface LoginRequest {
  username: string;
  password: string;
}

export interface RefreshTokenRequest {
  refreshToken: string;
}

export interface LoginResponse {
  accessToken: string;
  refreshToken?: string;
  tokenType: 'Bearer';
  expiresIn: number;
  user: Usuario;
}

export interface TokenPayload {
  sub: string;
  username: string;
  roles: string[];
  exp: number;
  iat: number;
}