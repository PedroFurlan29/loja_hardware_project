import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { BehaviorSubject, Observable } from "rxjs";
import { map, tap } from "rxjs/operators";
import { LoginRequest, LoginResponse, RefreshTokenRequest } from "../models/auth.models";
import { environment } from "../../../environments/environment";
import { Usuario } from "../models/usuario.models";

@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = `${environment.apiUrl}/auth`;
  private usuarioLogadoSubject = new BehaviorSubject<Usuario | null>(this.getUsuarioAtual());
  usuarioLogado$ = this.usuarioLogadoSubject.asObservable();

  constructor(private http: HttpClient) {}

  login(credentials: LoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, credentials)
      .pipe(
        tap(response => {
          localStorage.setItem('authToken', response.accessToken);
          if (response.refreshToken) {
            localStorage.setItem('refreshToken', response.refreshToken);
          }
          localStorage.setItem('usuario', JSON.stringify(response.user));
          this.usuarioLogadoSubject.next(response.user);
        })
      );
  }

  registrar(userData: any): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/registrar`, userData)
      .pipe(
        tap(response => {
          localStorage.setItem('authToken', response.accessToken);
          if (response.refreshToken) {
            localStorage.setItem('refreshToken', response.refreshToken);
          }
          localStorage.setItem('usuario', JSON.stringify(response.user));
          this.usuarioLogadoSubject.next(response.user);
        })
      );
  }

  logout(): void {
    localStorage.removeItem('authToken');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('usuario');
    this.usuarioLogadoSubject.next(null);
  }

  getToken(): string | null {
    return localStorage.getItem('authToken');
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  getUsuarioAtual(): Usuario | null {
    const usuarioJson = localStorage.getItem('usuario');
    return usuarioJson ? JSON.parse(usuarioJson) as Usuario : null;
  }

  refreshToken(): Observable<LoginResponse> {
    const refreshToken = localStorage.getItem('refreshToken');
    if (!refreshToken) {
      throw new Error('Refresh token not found');
    }
    return this.http.post<LoginResponse>(`${this.apiUrl}/refresh`, { refreshToken } as RefreshTokenRequest)
      .pipe(
        tap(response => {
          localStorage.setItem('authToken', response.accessToken);
          if (response.refreshToken) {
            localStorage.setItem('refreshToken', response.refreshToken);
          }
        })
      );
  }

  logoutRemote(): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/logout`, {});
  }
}
