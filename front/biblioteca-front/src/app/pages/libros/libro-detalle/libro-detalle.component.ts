import { Component, OnInit } from '@angular/core';
import { Libro } from '../../../core/models/libro.model';
import { LibrosService } from '../../../core/services/libros.service';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-libro-detalle',
  imports: [CommonModule, RouterModule],
  templateUrl: './libro-detalle.component.html',
  styleUrl: './libro-detalle.component.scss'
})
export class LibroDetalleComponent implements OnInit{
  
  public libro: Libro | null = null;
  public librosRelacionados: Libro[] = [];


constructor(
    private route: ActivatedRoute,
    private libroService: LibrosService
  ) {}

  ngOnInit(): void {
  this.route.paramMap.subscribe(params => {
    const idLibro = Number(params.get('id'));

    if (!isNaN(idLibro)) {
      this.libroService.getLibroPorId(idLibro).subscribe((libro: Libro) => {
        this.libro = libro;

        this.libroService.obtenerLibros().subscribe((todos: Libro[]) => {
          this.librosRelacionados = todos
            .filter(l => l.id !== libro.id && l.genero === libro.genero)
            .slice(0, 6);
        });
      });
    }
  });
}



}
