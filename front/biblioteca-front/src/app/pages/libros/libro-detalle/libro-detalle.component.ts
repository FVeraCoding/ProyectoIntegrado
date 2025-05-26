import { Component, OnInit } from '@angular/core';
import { Libro } from '../../../core/models/libro.model';
import { LibrosService } from '../../../core/services/libros.service';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { RouterModule } from '@angular/router';
import { EjemplaresService } from '../../../core/services/ejemplares.service';
import { Ejemplar } from '../../../core/models/ejemplar.model';

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


  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private libroService: LibrosService,
    private ejemplarService: EjemplaresService,
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
      this.ejemplaresRelacionados = [...todos.filter(e => e.idLibro === idLibro && e.reservado === false)];
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
    if (this.ejemplaresRelacionados.length > 0) {
      const ejemplar = this.ejemplaresRelacionados[0];
      const ejemplarReservado: Ejemplar = { ...ejemplar, reservado: true };

      this.ejemplarService.updateEjemplarById(ejemplar.id, ejemplarReservado)
        .subscribe({
          next: () => {
            console.log("Ejemplar reservado correctamente");
            this.cargarEjemplaresDisponibles(this.idLibro);
          },
          error: (e) => {
            console.log("Ha ocurrido un error", e);
          }
        });
    }
  }


}
