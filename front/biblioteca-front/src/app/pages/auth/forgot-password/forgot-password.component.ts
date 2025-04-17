import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-forgot-password',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule
  ],
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.scss']
})
export class ForgotPasswordComponent {
  form: FormGroup;
  mensajeExito: string | null = null;
  mensajeError: string | null = null;

  constructor(private fb: FormBuilder, private http: HttpClient) {
    this.form = this.fb.group({
      email: ['', [Validators.required, Validators.email]]
    });
  }

  onSubmit(): void {
    if (this.form.invalid) return;

    const email = this.form.value.email;

    this.http.post('http://localhost:8080/auth/forgot-password', { email }, { responseType: 'text' }).subscribe({
      next: (res: string) => {
        this.mensajeExito = res;
        this.mensajeError = null;
        this.form.reset();
      },
      error: (err) => {
        const errorMensaje = typeof err.error === 'string'
          ? err.error
          : 'No se pudo enviar el correo.';
        this.mensajeError = errorMensaje;
        this.mensajeExito = null;
      }
    });
  }    
}
