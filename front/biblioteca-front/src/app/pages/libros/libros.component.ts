import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { LibrosService } from '../../core/services/libros.service';
import { AuthService } from '../../core/services/auth.service';
import { Libro } from '../../core/models/libro.model';
import { NotificationService } from '../../core/services/notification.service';

@Component({
  selector: 'app-libros',
  standalone: true,
  imports: [CommonModule, FormsModule],
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

  constructor(
    private librosService: LibrosService,
    private authService: AuthService,
    private notificationService: NotificationService

  ) { }

  ngOnInit(): void {
    this.librosService.obtenerLibros().subscribe((libros: Libro[]) => {
      this.librosDestacados = libros.slice(0, 6);
      this.librosFiltrados = [...this.librosDestacados];
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
  
    if (!query) {
      this.librosFiltrados = this.librosDestacados;
      return;
    }
  
    this.librosFiltrados = this.librosDestacados.filter(libro =>
      libro.titulo.toLowerCase().includes(query) ||
      libro.autor.toLowerCase().includes(query)
    );
  }
  
}
