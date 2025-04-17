import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RegistroEmpleado } from '../models/registro-empleado'; 

@Injectable({
  providedIn: 'root'
})
export class EmpleadoService {

  private apiUrl = 'http://localhost:8080/empleados'; 

  constructor(private http: HttpClient) {}

  registrarEmpleado(empleado: RegistroEmpleado): Observable<any> {
    return this.http.post(`${this.apiUrl}`, empleado);
  }
}
