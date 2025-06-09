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

@Component({
  selector: 'app-usuario',
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.scss'],
  imports: [CommonModule]
})

export class UsuarioComponent implements OnInit {
  socio: Socio | null = null;
  listaReservas: Reserva[] | null = [];

  constructor(
    private socioService: SocioService,
    private authService: AuthService,
    private reservaService: ReservaService
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
        this.listaReservas = null;
      }
    });
  }

}
