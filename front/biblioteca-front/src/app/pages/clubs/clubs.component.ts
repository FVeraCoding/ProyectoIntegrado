import { Component, OnInit } from '@angular/core';
import { ClubService } from '../../core/services/club.service';
import { Club } from '../../core/models/clubs.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { jwtDecode } from 'jwt-decode';
import { EmpleadoService } from '../../core/services/empleado.service';
import { Socio } from '../../core/models/socio.model';

interface JwtPayload {
  sub: string;
  role: string;
  id: number;
}

@Component({
  standalone: true,
  selector: 'app-club',
  templateUrl: './clubs.component.html',
  styleUrls: ['./clubs.component.scss'],
  imports: [CommonModule, FormsModule]
})
export class ClubComponent implements OnInit {
  clubs: Club[] = [];
  userId: number | null = null;
  clubsFiltrados: Club[] = [];
  clubsPaginados: Club[] = [];
  nuevoClub: Club = { nombre: '', descripcion: '', idEmpleadoOrganizador: 0, sociosId: [] };
  clubEditando: Club | null = null;
  filtroNombre: string = '';

  esEmpleado: boolean = false;
  mostrarModalCrear: boolean = false;

  paginaActual: number = 1;
  elementosPorPagina: number = 6;
  totalPaginas: number = 1;

  mostrarModalSocios: boolean = false;
  sociosDelClub: Socio[] = [];


  constructor(private clubService: ClubService, private empleadoService: EmpleadoService) { }

  ngOnInit(): void {
    this.obtenerRolDesdeToken();

    if (this.esEmpleado && this.userId) {
      this.empleadoService.getEmpleadoByIdUsuario(this.userId).subscribe(empleado => {
        this.nuevoClub.idEmpleadoOrganizador = empleado.id!;
      });
    }

    this.cargarClubs();
  }

  obtenerRolDesdeToken(): void {
    const token = localStorage.getItem('token');
    if (token) {
      const decoded = jwtDecode<JwtPayload>(token);
      this.esEmpleado = decoded.role === 'EMPLEADO';
      this.userId = decoded.id;
    }
  }


  abrirModalCrear(): void {
    this.mostrarModalCrear = true;
  }

  cerrarModalCrear(): void {
    this.mostrarModalCrear = false;
  }

  cargarClubs(): void {
    this.clubService.getAllClubs().subscribe(data => {
      this.clubs = data;
      this.filtrarClubs();
    });
  }

  crearClub(): void {
    if (!this.nuevoClub.idEmpleadoOrganizador) return; // Verificamos que esté asignado correctamente

    this.clubService.addClub(this.nuevoClub).subscribe(() => {
      this.nuevoClub = {
        nombre: '',
        descripcion: '',
        idEmpleadoOrganizador: this.nuevoClub.idEmpleadoOrganizador, // mantener el organizador
        sociosId: []
      };
      this.mostrarModalCrear = false;
      this.cargarClubs();
    });
  }


  eliminarClub(id: number): void {
    if (confirm('¿Estás seguro de eliminar este club?')) {
      this.clubService.deleteClub(id).subscribe(() => this.cargarClubs());
    }
  }

  filtrarClubs(): void {
    if (this.filtroNombre.trim()) {
      this.clubsFiltrados = this.clubs.filter(club =>
        club.nombre.toLowerCase().includes(this.filtroNombre.toLowerCase())
      );
    } else {
      this.clubsFiltrados = [...this.clubs];
    }
    this.totalPaginas = Math.ceil(this.clubsFiltrados.length / this.elementosPorPagina);
    this.paginaActual = 1;
    this.actualizarPaginacion();
  }

  actualizarPaginacion(): void {
    const inicio = (this.paginaActual - 1) * this.elementosPorPagina;
    const fin = inicio + this.elementosPorPagina;
    this.clubsPaginados = this.clubsFiltrados.slice(inicio, fin);
  }

  paginaAnterior(): void {
    if (this.paginaActual > 1) {
      this.paginaActual--;
      this.actualizarPaginacion();
    }
  }

  paginaSiguiente(): void {
    if (this.paginaActual < this.totalPaginas) {
      this.paginaActual++;
      this.actualizarPaginacion();
    }
  }

  abrirEdicion(club: Club): void {
    this.clubEditando = { ...club };
  }

  cerrarModalEdicion(): void {
    this.clubEditando = null;
  }

  guardarEdicion(): void {
    if (this.clubEditando && this.clubEditando.id != null) {
      this.clubService.updateClub(this.clubEditando.id, this.clubEditando).subscribe(() => {
        this.cerrarModalEdicion();
        this.cargarClubs();
      });
    }
  }

  unirseAClub(clubId: number): void {
    console.log(this.userId);
    if (!this.userId) return;

    this.clubService.addSocioClub(this.userId, clubId).subscribe(() => {
      this.cargarClubs();
    });
  }


  onRetirarSocioClub(idClub: number, idSocio: number): void {
    this.clubService.retirarSocioDelClub(idClub, idSocio).subscribe({
      next: (clubActualizado) => {
        const index = this.clubs.findIndex(c => c.id === idClub); // ← corregido
        if (index !== -1) {
          this.clubs[index] = clubActualizado;
          this.filtrarClubs(); // recarga paginación
        }
      },
      error: (err) => {
        console.error('Error al retirar socio del club:', err);
      }
    });
  }

  verSocios(idClub: number): void {
    this.clubService.getSociosByClubId(idClub).subscribe({
      next: (socios) => {
        this.sociosDelClub = socios;
        this.mostrarModalSocios = true;
      },
      error: (err) => {
        console.error('Error al obtener socios del club', err);
      }
    });
  }

  cerrarModalSocios(): void {
    this.mostrarModalSocios = false;
    this.sociosDelClub = [];
  }



}
