import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ValidatorFn, AbstractControl, ValidationErrors, ReactiveFormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-reset-password',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ResetPasswordComponent implements OnInit {
  form: FormGroup;
  token: string = '';
  mensajeExito: string | null = null;
  mensajeError: string | null = null;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private http: HttpClient,
    private router: Router
  ) {
    this.form = this.fb.group({
      nuevaPassword: ['', [Validators.required, Validators.minLength(6)]],
      repetirPassword: ['', Validators.required]
    }, { validators: this.passwordsMatchValidator });
  }

  ngOnInit(): void {
    this.token = this.route.snapshot.queryParamMap.get('token') || '';
  }

  passwordsMatchValidator: ValidatorFn = (group: AbstractControl): ValidationErrors | null => {
    const pass = group.get('nuevaPassword')?.value;
    const repeat = group.get('repetirPassword')?.value;
    return pass === repeat ? null : { passwordMismatch: true };
  };

  onSubmit(): void {
    console.log("Formulario enviado");
    if (this.form.invalid) {
      this.mensajeError = 'Las contraseñas no coinciden o son inválidas.';
      return;
    }

    const nuevaPassword = this.form.value.nuevaPassword;

    this.http.post('http://localhost:8080/auth/reset-password', {
      token: this.token,
      nuevaPassword
    }, { responseType: 'text' }).subscribe({
      next: (res: string) => {
        console.log('Respuesta del backend:', res);
        this.mensajeExito = res;
        this.mensajeError = null;
        this.form.reset();
      },
      error: (err) => {
        console.log('Error recibido:', err);
        const errorMensaje = typeof err.error === 'string'
          ? err.error
          : 'Error al restablecer la contraseña.';
        this.mensajeError = errorMensaje;
        this.mensajeExito = null;
      }
    });
  }
}
