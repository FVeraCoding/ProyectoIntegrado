import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { Login } from '../models/login.model'; 
import { Token } from '../models/token.model';
import { Usuario } from '../models/usuario.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/auth';
  private tokenKey = 'auth-token';

  constructor(private http: HttpClient) {}

  login(data: Login): Observable<Token> {
    return this.http.post<Token>(`${this.baseUrl}/login`, data)
      .pipe(
        tap(response => {
          this.saveToken(response.token);
        })
      );
  }

  private saveToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
  }

  getUserRole(): string | null {
    const token = this.getToken();
    if (!token) {
      return null;
    }

    const payload = token.split('.')[1];
    const decodedPayload = JSON.parse(atob(payload));

    return decodedPayload.role || null;
  }
  isEmpleado(): boolean {
  const token = this.getToken();
  if (!token) return false;

  try {
    const payload = token.split('.')[1];
    const decodedPayload = JSON.parse(atob(payload));
    return decodedPayload.role === 'EMPLEADO';
  } catch (error) {
    console.error('Error decoding token', error);
    return false;
  }
}

  
  isSocio(): boolean {
  const token = this.getToken();
  if (!token) return false;

  try {
    const payload = token.split('.')[1];
    const decodedPayload = JSON.parse(atob(payload));
    return decodedPayload.role === 'SOCIO';
  } catch (error) {
    console.error('Error decoding token', error);
    return false;
  }
}

  getUserId(): number | null {
    return null;
  }
}
