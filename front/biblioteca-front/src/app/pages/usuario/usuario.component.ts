import { Component, OnInit } from '@angular/core';
import { Socio } from '../../core/models/socio.model';
import { Evento } from '../../core/models/evento.model';
import { Club } from '../../core/models/clubs.model';
import { EventoService } from '../../core/services/evento.service';
import { ClubService } from '../../core/services/club.service';
import { AuthService } from '../../core/services/auth.service';
import { SocioService } from '../../core/services/socio.service';
import { CommonModule } from '@angular/common';
import { ReservaService } from '../../core/services/reserva.service';
import { Reserva } from '../../core/models/reserva.model';
import { Asistente } from '../../core/models/asistente.model';
import { forkJoin } from 'rxjs';


@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.scss'],
  imports: [CommonModule]
})

export class UsuarioComponent implements OnInit {
  socio: Socio | null = null;
  listaReservas: Reserva[] = [];
  listaAsistencias: Asistente[] = [];
  eventosAsistidos: Evento[] = [];
  listaClubs: Club[] = [];

  constructor(
    private socioService: SocioService,
    private authService: AuthService,
    private reservaService: ReservaService,
    private eventoService: EventoService,
    private clubService: ClubService
  ) { }

  ngOnInit(): void {
    this.obtenerSocio();
  }


  obtenerSocio(): void {
    const idUsuario = this.authService.getUserId();

    this.socioService.obtenerSocioPorId(idUsuario).subscribe({
      next: (socio: Socio) => {
        this.socio = socio;
        this.obtenerReservas(this.socio!.id);
        this.obtenerEventos(this.socio!.id);
        if (this.socio && this.socio.id !== undefined && this.socio.id !== null) {
          this.obtenerClubs(this.socio.id);
        }
      },
      error: (err) => {
        console.error('Error al obtener el socio', err);
        this.socio = null;
      }
    })

  }

  obtenerReservas(idSocio: number | null | undefined): void {
    this.reservaService.getReservaBySocioId(idSocio).subscribe({
      next: (reservas: Reserva[]) => {
        this.listaReservas = reservas;
        console.log(this.listaReservas)

      },
      error: (err) => {
        console.error("Error al obtener las reservas", err);
        this.listaReservas = [];
      }
    });
  }

  obtenerEventos(idSocio: number | undefined): void {
    this.eventoService.getAsistenciasEventoBySocioId(idSocio).subscribe({
      next: (asistencias: Asistente[]) => {
        this.listaAsistencias = asistencias;
        console.log('Asistencias:', this.listaAsistencias);

        const solicitudesEventos = asistencias.map(a =>
          this.eventoService.getById(a.idEvento)
        );

        forkJoin(solicitudesEventos).subscribe({
          next: eventos => {
            this.eventosAsistidos = eventos;
            console.log('Eventos asistidos:', this.eventosAsistidos);
          },
          error: err => {
            console.error('Error al obtener eventos', err);
            this.eventosAsistidos = [];
          }
        });
      },
      error: err => {
        console.error("Error al obtener las asistencias", err);
        this.listaAsistencias = [];
      }
    });
  }

  obtenerClubs(idSocio: number): void {
    this.clubService.getClubsBySocioId(idSocio).subscribe({
      next: (clubs: Club[]) => {
        this.listaClubs = clubs;
        console.log(clubs);
      },
      error: err => {
        console.error('Error al obtener los clubs', err);
        this.listaClubs = [];
      }
    })
  }



}
