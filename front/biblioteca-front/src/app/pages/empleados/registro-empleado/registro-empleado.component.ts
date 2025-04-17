import { Component } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  AbstractControl,
  ValidationErrors,
  ValidatorFn,
  ReactiveFormsModule
} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { EmpleadoService } from '../../../core/services/empleado.service';
import { RegistroEmpleado } from '../../../core/models/registro-empleado';
import { RouterModule, Router } from '@angular/router';

@Component({
  selector: 'app-registro-empleado',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterModule
  ],
  templateUrl: './registro-empleado.component.html',
  styleUrls: ['./registro-empleado.component.scss']
})
export class RegistroEmpleadoComponent {
  form: FormGroup;
  mensajeError: string | null = null;
  mensajeExito: string | null = null;

  constructor(
    private fb: FormBuilder,
    private empleadoService: EmpleadoService,
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
      direccion: ['', Validators.required],
      cargo: ['', [Validators.required, Validators.minLength(3)]]
    }, { validators: this.passwordsMatchValidator });
  }

  passwordsMatchValidator: ValidatorFn = (group: AbstractControl): ValidationErrors | null => {
    const pass = group.get('password')?.value;
    const repeat = group.get('repetirPassword')?.value;
    return pass === repeat ? null : { passwordMismatch: true };
  };

  fechaNoFuturaValidator: ValidatorFn = (control: AbstractControl): ValidationErrors | null => {
    const fecha = new Date(control.value);
    const hoy = new Date();
    hoy.setHours(0, 0, 0, 0);
    return fecha > hoy ? { fechaFutura: true } : null;
  };

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const datos: RegistroEmpleado = {
      ...this.form.value,
      repetirPassword: undefined
    };

    this.empleadoService.registrarEmpleado(datos).subscribe({
      next: () => {
        this.mensajeExito = 'Empleado registrado correctamente.';
        this.mensajeError = null;
        this.form.reset();
        setTimeout(() => this.router.navigate(['/login']), 2000);
      },
      error: (err: any) => {
        this.mensajeError = err?.error || 'Error al registrar el empleado.';
        this.mensajeExito = null;
      }
    });
  }
}
