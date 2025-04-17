import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl, ValidationErrors, ValidatorFn, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { SocioService } from '../../../core/services/socio.service';
import { RegistroSocio } from '../../../core/models/registro-socio';
import { RouterModule, Router } from '@angular/router';

@Component({
  selector: 'app-registro-socio',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule
  ],
  templateUrl: './registro-socios.component.html',
  styleUrls: ['./registro-socios.component.scss']
})
export class RegistroSocioComponent {
  form: FormGroup;
  mensajeError: string | null = null;
  mensajeExito: string | null = null;

  constructor(
    private fb: FormBuilder,
    private socioService: SocioService,
    private router: Router
  ) {
    this.form = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      repetirPassword: ['', Validators.required],
      nombre: ['', [Validators.required, Validators.pattern(/^[a-zA-ZÁÉÍÓÚáéíóúÑñ\s]+$/)]],
      apellidos: ['', [Validators.required, Validators.pattern(/^[a-zA-ZÁÉÍÓÚáéíóúÑñ\s]+$/)]],      
      fechaNacimiento: ['', [Validators.required, this.fechaNoFuturaValidator]],
      correoElectronico: ['', [Validators.required, Validators.email]],
      telefono: ['', [Validators.required, Validators.pattern(/^\d{9}$/)]],
      direccion: ['', Validators.required]
    }, { validators: this.passwordsMatchValidator });
  }

  passwordsMatchValidator: ValidatorFn = (group: AbstractControl): ValidationErrors | null => {
    const password = group.get('password')?.value;
    const repeat = group.get('repetirPassword')?.value;
    return password === repeat ? null : { passwordMismatch: true };
  };

  fechaNoFuturaValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
    const fecha = new Date(control.value);
    const hoy = new Date();
    hoy.setHours(0, 0, 0, 0); // Eliminar horas
    return fecha > hoy ? { fechaFutura: true } : null;
  };

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const datos: RegistroSocio = {
      ...this.form.value,
      repetirPassword: undefined // No enviar al backend
    };

    this.socioService.registrarSocio(datos).subscribe({
      next: () => {
        this.mensajeExito = 'Socio registrado correctamente.';
        this.mensajeError = null;
        this.form.reset();
        setTimeout(() => this.router.navigate(['/login']), 2000);
      },
      error: (err: any) => {
        this.mensajeError = err?.error || 'Error al registrar el socio.';
        this.mensajeExito = null;
      }
    });
  }
}
