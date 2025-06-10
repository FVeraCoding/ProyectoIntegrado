import { Component } from '@angular/core';
import { Socio } from '../../../core/models/socio.model';
import { SocioService } from '../../../core/services/socio.service';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';


@Component({
  selector: 'app-socios',
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './socios.component.html',
  styleUrl: './socios.component.scss'
})
export class SociosComponent {

  pageSize = 5;
  currentPage = 1;
  allSocios: Socio[] = [];
  socioEditando: Socio | null = null;
  form!: FormGroup;
  mostrarModalEdicion = false;



  constructor(private socioService: SocioService, private fb: FormBuilder) { }

  ngOnInit() {
    this.getAllSocios();
  }

  getAllSocios(): void {
    this.socioService.getAllSocios().subscribe({
      next: (listaSocios: Socio[]) => {
        this.allSocios = listaSocios;
        console.log(this.allSocios);
      },
      error: (err) => {
        console.error('Error al obtener todos los socios', err);
        this.allSocios = [];
      }
    })
  }

  get totalPages(): number {
    return Math.ceil(this.allSocios.length / this.pageSize);
  }

  get sociosPaginados(): any[] {
    const start = (this.currentPage - 1) * this.pageSize;
    return this.allSocios.slice(start, start + this.pageSize);
  }

  siguientePagina() {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
    }
  }

  paginaAnterior() {
    if (this.currentPage > 1) {
      this.currentPage--;
    }
  }

  eliminarSocio(socio: Socio): void {
    if (confirm(`¿Estás seguro de que deseas eliminar a ${socio.nombre} ${socio.apellidos}?`)) {
      this.socioService.deleteSocioById(socio.id).subscribe({
        next: () => {
          this.allSocios = this.allSocios.filter(s => s.id !== socio.id);
        },
        error: (err) => {
          console.error('Error al eliminar el socio', err);
        }
      });
    }
  }

  abrirModalEdicion(socio: Socio): void {
    this.socioEditando = socio;
    this.form = this.fb.group({
      nombre: [socio.nombre, [Validators.required]],
      apellidos: [socio.apellidos, [Validators.required]],
      correoElectronico: [socio.correoElectronico, [Validators.required, Validators.email]],
      fechaNacimiento: [socio.fechaNacimiento, [Validators.required]]
    });
    this.mostrarModalEdicion = true;
  }

  cerrarModalEdicion(): void {
    this.mostrarModalEdicion = false;
    this.socioEditando = null;
  }

  onSubmitEdicion(): void {
    if (this.form.invalid || !this.socioEditando) return;

    const socioActualizado: Socio = {
      ...this.socioEditando,
      ...this.form.value
    };

    this.socioService.updateSocio(socioActualizado).subscribe({
      next: () => {
        const index = this.allSocios.findIndex(s => s.id === socioActualizado.id);
        if (index !== -1) this.allSocios[index] = socioActualizado;
        this.cerrarModalEdicion();
      },
      error: err => {
        console.error('Error al actualizar el socio', err);
      }
    });
  }



}
