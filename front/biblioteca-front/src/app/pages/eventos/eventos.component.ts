import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EventoService } from '../../core/services/evento.service';
import { Evento } from '../../core/models/evento.model';
import { jwtDecode } from 'jwt-decode';
import { EmpleadoService } from '../../core/services/empleado.service';

interface JwtPayload {
  sub: string;
  role: string;
  id: number;
}

@Component({
  standalone: true,
  selector: 'app-evento',
  templateUrl: './eventos.component.html',
  styleUrls: ['./eventos.component.scss'],
  imports: [CommonModule, FormsModule]
})
export class EventoComponent implements OnInit {
  eventos: Evento[] = [];
  eventosFiltrados: Evento[] = [];
  eventosPaginados: Evento[] = [];
  idEmpleado: number | null | undefined = null;


  nuevoEvento: Evento = {
    nombre: '',
    descripcion: '',
    fecha: '',
    idEmpleadoOrganizador: 0
  };

  eventoEditando: Evento | null = null;

  userId: number | null = null;
  esEmpleado: boolean = false;
  mostrarModalCrear: boolean = false;

  filtroNombre: string = '';
  paginaActual: number = 1;
  elementosPorPagina: number = 5;
  totalPaginas: number = 1;

  constructor(private eventoService: EventoService, private empleadoService: EmpleadoService) { }

  ngOnInit(): void {
    this.obtenerRolDesdeToken();

    if (this.esEmpleado && this.userId) {
      this.empleadoService.getEmpleadoByIdUsuario(this.userId).subscribe(empleado => {
        this.idEmpleado = empleado.id;
      });
    }

    this.cargarEventos();
  }

  obtenerRolDesdeToken(): void {
    const token = localStorage.getItem('token');
    if (token) {
      const decoded = jwtDecode<JwtPayload>(token);
      this.esEmpleado = decoded.role === 'EMPLEADO';
      this.userId = decoded.id;
    }
  }

  cargarEventos(): void {
    this.eventoService.getAll().subscribe(data => {
      this.eventos = data;
      this.filtrarEventos();
    });
  }

  filtrarEventos(): void {
    if (this.filtroNombre.trim()) {
      this.eventosFiltrados = this.eventos.filter(evento =>
        evento.nombre.toLowerCase().includes(this.filtroNombre.toLowerCase())
      );
    } else {
      this.eventosFiltrados = [...this.eventos];
    }
    this.totalPaginas = Math.ceil(this.eventosFiltrados.length / this.elementosPorPagina);
    this.paginaActual = 1;
    this.actualizarPaginacion();
  }

  actualizarPaginacion(): void {
    const inicio = (this.paginaActual - 1) * this.elementosPorPagina;
    const fin = inicio + this.elementosPorPagina;
    this.eventosPaginados = this.eventosFiltrados.slice(inicio, fin);
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

  abrirModalCrear(): void {
    this.mostrarModalCrear = true;
  }

  cerrarModalCrear(): void {
    this.mostrarModalCrear = false;
  }

  crearEvento(): void {
    if (!this.idEmpleado) return;

    this.nuevoEvento.idEmpleadoOrganizador = this.idEmpleado;

    this.eventoService.create(this.nuevoEvento).subscribe(() => {
      this.nuevoEvento = {
        nombre: '',
        descripcion: '',
        fecha: '',
        idEmpleadoOrganizador: 0
      };
      this.mostrarModalCrear = false;
      this.cargarEventos();
    });
  }


  eliminarEvento(id: number): void {
    if (confirm('¿Estás seguro de eliminar este evento?')) {
      this.eventoService.delete(id).subscribe(() => {
        this.cargarEventos();
      });
    }
  }

  abrirEdicion(evento: Evento): void {
    this.eventoEditando = { ...evento };
  }

  cerrarModalEdicion(): void {
    this.eventoEditando = null;
  }

  guardarEdicion(): void {
    if (this.eventoEditando?.id) {
      this.eventoService.update(this.eventoEditando).subscribe(() => {
        this.cerrarModalEdicion();
        this.cargarEventos();
      });
    }
  }

  unirseAEvento(eventoId: number): void {
  if (!this.userId) return;

  this.eventoService.addAsistencia(eventoId, this.userId).subscribe((eventoActualizado) => {
    const index = this.eventos.findIndex(e => e.id === eventoId);
    if (index !== -1) {
      this.eventos[index] = eventoActualizado; 
      this.filtrarEventos(); 
    }
  });
}

}
