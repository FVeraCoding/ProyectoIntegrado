import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LibrosService } from '../../core/services/libros.service'; 
import { Libro } from '../../core/models/libro.model';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent{

  librosDestacados: Libro[] = [];

  constructor(private librosService: LibrosService) {}

  ngOnInit(): void {
    this.librosService.obtenerLibros().subscribe(libros => {
      this.librosDestacados = libros.slice(0, 6);
    });
  }

}
