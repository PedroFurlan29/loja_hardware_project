import { Injectable, signal, computed } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { environment } from '../../../environments/environment';

export interface UserInfo {
  id: number;
  nome: string;
  email: string;
  perfil: 'ADMIN' | 'VENDEDOR' | 'CLIENTE';
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private _user = signal<UserInfo | null>(null);

  readonly user = this._user.asReadonly();
  readonly isLoggedIn = computed(() => this._user() !== null);
  readonly isAdmin = computed(() => this._user()?.perfil === 'ADMIN');
  readonly isVendedor = computed(() => this._user()?.perfil === 'VENDEDOR' || this._user()?.perfil === 'ADMIN');
  readonly isCliente = computed(() => this._user()?.perfil === 'CLIENTE');

  constructor(private http: HttpClient, private router: Router) {
    this.tryRestoreSession();
  }

  tryRestoreSession() {
    if (typeof window === 'undefined') return;
    const token = localStorage.getItem('token');
    if (!token) return;
    this.http.get<UserInfo>(`${environment.apiUrl}/auth/me`).subscribe({
      next: (user) => this._user.set(user),
      error: () => this.logout()
    });
  }

  setUser(user: UserInfo) {
    this._user.set(user);
  }

  logout() {
    if (typeof window !== 'undefined') localStorage.removeItem('token');
    this._user.set(null);
    this.router.navigate(['/']);
  }

  getToken(): string | null {
    if (typeof window === 'undefined') return null;
    return localStorage.getItem('token');
  }
}