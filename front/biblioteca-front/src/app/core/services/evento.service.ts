import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Evento } from '../models/evento.model'; 

@Injectable({ providedIn: 'root' })
export class EventoService {
  private baseUrl = 'http://localhost:8080/evento';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Evento[]> {
    return this.http.get<Evento[]>(this.baseUrl);
  }

  getById(id: number): Observable<Evento> {
    return this.http.get<Evento>(`${this.baseUrl}/${id}`);
  }

  getByNombre(nombre: string): Observable<Evento> {
    return this.http.get<Evento>(`${this.baseUrl}/nombre/${nombre}`);
  }

  create(evento: Evento): Observable<Evento> {
    return this.http.post<Evento>(this.baseUrl, evento);
  }

  update(evento: Evento): Observable<Evento> {
    return this.http.put<Evento>(`${this.baseUrl}/${evento.id}`, evento);
  }

  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${id}`);
  }

  addAsistencia(idEvento: number, idSocio: number): Observable<Evento> {
    return this.http.put<Evento>(`${this.baseUrl}/asistencia`, { idEvento, idSocio });
  }
}
