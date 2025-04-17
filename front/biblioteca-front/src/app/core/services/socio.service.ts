import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegistroSocio } from '../models/registro-socio';

@Injectable({
  providedIn: 'root'
})
export class SocioService {

  private apiUrl = 'http://localhost:8080/socios'; 

  constructor(private http: HttpClient) {}

  registrarSocio(socio: RegistroSocio): Observable<any> {
    return this.http.post(`${this.apiUrl}`, socio);
  }
}
