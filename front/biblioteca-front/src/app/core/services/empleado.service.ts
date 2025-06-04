import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Empleado } from '../models/empleado.model';
import { RegistroEmpleado } from '../models/registro-empleado';

@Injectable({
  providedIn: 'root'
})
export class EmpleadoService {

  private apiUrl = 'http://localhost:8080/empleados';

  constructor(private http: HttpClient) {}

  registrarEmpleado(empleado: RegistroEmpleado): Observable<Empleado> {
    return this.http.post<Empleado>(`${this.apiUrl}`, empleado);
  }


  getAll(): Observable<Empleado[]> {
    return this.http.get<Empleado[]>(`${this.apiUrl}/all`);
  }


  getEmpleadoByIdUsuario(idUsuario: number): Observable<Empleado> {
    return this.http.get<Empleado>(`${this.apiUrl}/${idUsuario}`);
  }


  deleteEmpleadoByIdUsuario(idUsuario: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${idUsuario}`);
  }
}
