import { Component, OnInit } from '@angular/core';
import { Libro } from '../../../core/models/libro.model';
import { LibrosService } from '../../../core/services/libros.service';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { RouterModule } from '@angular/router';
import { EjemplaresService } from '../../../core/services/ejemplares.service';
import { Ejemplar } from '../../../core/models/ejemplar.model';
import { AuthService } from '../../../core/services/auth.service';
import { ReservaService } from '../../../core/services/reserva.service';
import { Reserva } from '../../../core/models/reserva.model';
import { SocioService } from '../../../core/services/socio.service';
import { Socio } from '../../../core/models/socio.model';

@Component({
  selector: 'app-libro-detalle',
  imports: [CommonModule, RouterModule],
  templateUrl: './libro-detalle.component.html',
  styleUrl: './libro-detalle.component.scss'
})
export class LibroDetalleComponent implements OnInit {

  public libro: Libro | null = null;
  public librosRelacionados: Libro[] = [];
  public ejemplaresRelacionados: Ejemplar[] = [];
  private idLibro: number = 0;
  public ejemplaresDisponibles: Ejemplar[] = [];

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private libroService: LibrosService,
    private ejemplarService: EjemplaresService,
    private authService: AuthService,
    private reservaService: ReservaService,
    private socioService: SocioService
  ) { }


  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.idLibro = Number(params.get('id'));
      if (!isNaN(this.idLibro)) {
        this.cargarLibro(this.idLibro);
        this.cargarEjemplaresDisponibles(this.idLibro);
        this.cargarLibrosRelacionados(this.idLibro);
      }
    });
  }

  private cargarLibro(idLibro: number): void {
    this.libroService.getLibroPorId(idLibro).subscribe((libro: Libro) => {
      this.libro = libro;
    });
  }

 private cargarEjemplaresDisponibles(idLibro: number): void {
  this.ejemplarService.findAllEjemplares().subscribe((todos: Ejemplar[]) => {

    const filtrados = todos.filter(e => e.idLibro === this.idLibro && !e.reservado);

    this.ejemplaresDisponibles = filtrados;
  });
}


  private cargarLibrosRelacionados(idLibro: number): void {
    this.libroService.getLibroPorId(idLibro).subscribe((libro: Libro) => {
      this.libroService.obtenerLibros().subscribe((todos: Libro[]) => {
        this.librosRelacionados = todos
          .filter(l => l.id !== libro.id && l.genero === libro.genero)
          .slice(0, 6);
      });
    });
  }

  public reservarEjemplar(): void {
  if (this.ejemplaresDisponibles.length > 0) {
    const ejemplar = this.ejemplaresDisponibles[0];
    const ejemplarReservado: Ejemplar = { ...ejemplar, reservado: true };

    const idSocio = this.authService.getUserId();
    if (!idSocio) {
      console.error('No se encontró el ID del socio');
      return;
    }

    const hoy = new Date();
    const fechaInicio = hoy.toISOString().split('T')[0]; // yyyy-MM-dd
    const fechaFin = new Date(hoy);
    fechaFin.setDate(hoy.getDate() + 7); // préstamo de 7 días
    const fechaFinStr = fechaFin.toISOString().split('T')[0];

    const reserva: Reserva = {
      socioID: idSocio,
      ejemplarID: ejemplar.id,
      nombreSocio: '',      // backend lo rellenará
      nombreLibro: '',      // backend lo rellenará
      fechaInicio: fechaInicio,
      fechaFin: fechaFinStr
    };

    this.reservaService.crearReserva(reserva).subscribe({
      next: (creada) => {
        if (creada) {
          this.ejemplarService.updateEjemplarById(ejemplar.id, ejemplarReservado).subscribe({
            next: () => {
              console.log("Reserva y ejemplar actualizados correctamente");
              this.cargarEjemplaresDisponibles(this.idLibro);
            },
            error: (e) => {
              console.error("Error al actualizar ejemplar", e);
            }
          });
        } else {
          console.error("No se pudo crear la reserva");
        }
      },
      error: (err) => {
        console.error("Error al crear reserva:", err);
      }
    });
  }
}


isSocio(): boolean {
  return !this.authService.isEmpleado();
}


public addEjemplar(): void {
  if (!this.libro || !this.libro.id) return;

  const nuevoEjemplar: Ejemplar = {
    id: 0, 
    reservado: false,
    idLibro: this.libro.id
  };

  this.ejemplarService.addEjemplar(nuevoEjemplar).subscribe({
    next: (ejemplarCreado) => {
      this.ejemplaresDisponibles.push(ejemplarCreado);
    },
    error: (error) => {
      console.error('Error al añadir ejemplar:', error);
    }
  });
}

isEmpleado(): boolean {
  return this.authService.isEmpleado(); // ya lo tienes en el servicio
}


}
