import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Club } from "../models/clubs.model";
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ClubService {
  private apiUrl = 'http://localhost:8080/club';
  constructor(private http: HttpClient) { }

  getAllClubs(): Observable<Club[]> {
    return this.http.get<Club[]>(`${this.apiUrl}/all`);
  }

  getClubById(id: number): Observable<Club>{
    return this.http.get<Club>(`${this.apiUrl}/${id}`);
  }

  getClubsBySocioId(id: number): Observable<Club[]>{
    return this.http.get<Club[]>(`${this.apiUrl}/socio/${id}`);
  }

  addClub(club: Club): Observable<Club>{
    return this.http.post<Club>(this.apiUrl, club);
  }

  updateClub(id: number, club: Club): Observable<Club>{
    return this.http.put<Club>(`${this.apiUrl}/${id}`, club);
  }

  addSocioClub(idSocio: number, idClub: number){
    return this.http.put<Club>(`${this.apiUrl}/${idClub}/${idSocio}`, null);
  }

  deleteClub(idClub: number){
    return this.http.delete<void>(`${this.apiUrl}/${idClub}`);
  }

}
