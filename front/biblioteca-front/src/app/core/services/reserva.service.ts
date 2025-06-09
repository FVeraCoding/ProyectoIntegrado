import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Reserva } from '../models/reserva.model';

@Injectable({
  providedIn: 'root'
})
export class ReservaService {

  constructor(private http: HttpClient) { }

  private apiUrl = 'http://localhost:8080/reservas';

  getReservaBySocioId(id: number | null | undefined): Observable<Reserva[]> {
    return this.http.get<Reserva[]>(`${this.apiUrl}/${id}`);
  }

  crearReserva(reserva: Reserva): Observable<boolean>{
    return this.http.post<boolean>(`${this.apiUrl}/crear-reserva`, reserva);
  }

}

