import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { LibrosService } from '../../core/services/libros.service';
import { AuthService } from '../../core/services/auth.service';
import { Libro } from '../../core/models/libro.model';
import { NotificationService } from '../../core/services/notification.service';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-libros',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './libros.component.html',
  styleUrl: './libros.component.scss'
})
export class LibrosComponent implements OnInit {

  queryBusquedaSocio: string = '';
  librosFiltrados: Libro[] = [];
  librosDestacados: Libro[] = [];
  librosBuscados: Libro[] = [];
  queryBusqueda: string = '';
  buscando: boolean = false;
  paginacionInicio: number = 0;
  paginacionFinal: number = 6;

  constructor(
    private librosService: LibrosService,
    private authService: AuthService,
    private notificationService: NotificationService

  ) { }

  ngOnInit(): void {
    this.librosService.obtenerLibros().subscribe((libros: Libro[]) => {
      this.librosDestacados = libros;
      this.librosFiltrados = [...this.librosDestacados.slice(this.paginacionInicio, this.paginacionFinal)];
    });
  }


  buscarLibros(): void {
    if (!this.queryBusqueda.trim()) return;

    this.buscando = true;
    this.librosService.buscarLibrosGoogle(this.queryBusqueda).subscribe((libros: Libro[]) => {
      this.librosBuscados = libros;
      this.buscando = false;
    }, (error: any) => {
      console.error('Error buscando libros:', error);
      this.buscando = false;
    });
  }


  addBook(libro: Libro): void {
    this.librosService.guardarLibro(libro).subscribe({
      next: (libroGuardado: Libro) => {
        this.librosDestacados.push(libroGuardado);
        this.notificationService.show(`Libro "${libroGuardado.titulo}" añadido a la biblioteca.`, 'success');
      },
      error: (error: any) => {
        console.error('Error añadiendo libro:', error);
        this.notificationService.show('Error al añadir el libro.', 'error');
      }
    });
  }

  isEmpleado(): boolean {
    return this.authService.isEmpleado();
  }

  isSocio(): boolean {
    return !this.authService.isEmpleado();
  }

  buscarLibrosLocales(): void {
    const query = this.queryBusquedaSocio.trim().toLowerCase();

    console.log(this.librosDestacados);
    if (!query) {
      this.librosFiltrados = this.librosDestacados;
      return;
    }

    this.librosFiltrados = this.librosDestacados.filter(libro =>
      libro.titulo.toLowerCase().includes(query) ||
      libro.autor.toLowerCase().includes(query)
    );
  }


pasarPaginaAfter(): void {
  const totalLibros = this.librosDestacados.length;

  const siguienteInicio = this.paginacionInicio + 7;
  if (siguienteInicio >= totalLibros) return; // No hay más libros

  this.paginacionInicio = siguienteInicio;
  this.paginacionFinal = Math.min(this.paginacionInicio + 6, totalLibros);

  this.librosFiltrados = this.librosDestacados.slice(this.paginacionInicio, this.paginacionFinal);
}


pasarPaginaBefore(): void {
  if (this.paginacionInicio === 0) return;

  this.paginacionInicio = Math.max(this.paginacionInicio - 7, 0);
  this.paginacionFinal = this.paginacionInicio + 6;

  this.librosFiltrados = this.librosDestacados.slice(this.paginacionInicio, Math.min(this.paginacionFinal, this.librosDestacados.length));
}


}
