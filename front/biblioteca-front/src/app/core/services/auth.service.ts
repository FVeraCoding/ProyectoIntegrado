import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, tap, BehaviorSubject } from 'rxjs';
import { Login } from '../models/login.model'; 
import { Token } from '../models/token.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/auth';
  private tokenKey = 'auth-token';

  private roleSubject = new BehaviorSubject<string | null>(this.getUserRole());
  public role$ = this.roleSubject.asObservable();

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
    const role = this.getUserRole();
    this.roleSubject.next(role); // Notifica a los suscriptores del nuevo rol
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  logout(): void {
    localStorage.removeItem(this.tokenKey);
    this.roleSubject.next(null); // Notifica que no hay usuario logueado
  }

  getUserRole(): string | null {
    const token = this.getToken();
    if (!token) return null;

    try {
      const payload = token.split('.')[1];
      const decodedPayload = JSON.parse(atob(payload));
      return decodedPayload.role || null;
    } catch (error) {
      console.error('Error decoding token role:', error);
      return null;
    }
  }

  getUserId(): number | null {
    const token = this.getToken();
    if (!token) return null;

    try {
      const payload = token.split('.')[1];
      const decodedPayload = JSON.parse(atob(payload));
      return decodedPayload.id ?? null;
    } catch (error) {
      console.error('Error decoding token id:', error);
      return null;
    }
  }

  isEmpleado(): boolean {
    return this.getUserRole() === 'EMPLEADO';
  }

  isSocio(): boolean {
    return this.getUserRole() === 'SOCIO';
  }
}
