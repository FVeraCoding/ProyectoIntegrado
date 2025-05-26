import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Ejemplar } from '../models/ejemplar.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EjemplaresService {

  private baseUrl = "http://localhost:8080/ejemplares"

  constructor(private http: HttpClient) { }


  addEjemplar(ejemplar: Ejemplar): Observable<Ejemplar> {
    return this.http.post<Ejemplar>(this.baseUrl, ejemplar);
  }

  findAllEjemplares(): Observable<Ejemplar[]> {
    return this.http.get<Ejemplar[]>(`${this.baseUrl}/all`);
  }

  findEjemplarById(id: number): Observable<Ejemplar> {
    return this.http.get<Ejemplar>(`${this.baseUrl}/${id}`);
  }

  updateEjemplarById(id: number, ejemplar: Ejemplar): Observable<void> {
    return this.http.put<void>(`${this.baseUrl}/${id}`, ejemplar);
  }


  deleteEjemplarById(id: number): Observable<Ejemplar> {
    return this.http.delete<Ejemplar>(`${this.baseUrl}/${id}`);
  }

}
