import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegistroSocio } from '../models/registro-socio';
import { Socio } from '../models/socio.model';

@Injectable({
  providedIn: 'root'
})
export class SocioService {

  private apiUrl = 'http://localhost:8080/socios';

  constructor(private http: HttpClient) { }

  getAllSocios(): Observable<Socio[]> {
    return this.http.get<Socio[]>(`${this.apiUrl}/all`)
  }

  registrarSocio(socio: RegistroSocio): Observable<any> {
    return this.http.post(`${this.apiUrl}`, socio);
  }

  obtenerSocioPorId(id: number | null): Observable<Socio> {
    return this.http.get<Socio>(`${this.apiUrl}/socio/${id}`);
  }

  deleteSocioById(idSocio: number | undefined): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${idSocio}`);
  }

  updateSocio(socio: Socio): Observable<Socio> {
    return this.http.put<Socio>(`${this.apiUrl}/${socio.id}`, socio);
  }



}
