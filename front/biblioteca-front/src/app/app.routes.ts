import { Routes } from '@angular/router';
import { LoginComponent } from './pages/auth/login/login.component';
import { RegistroEmpleadoComponent } from './pages/empleados/registro-empleado/registro-empleado.component';
import { RegistroSocioComponent } from './pages/socios/registro-socios/registro-socios.component';
import { ForgotPasswordComponent } from './pages/auth/forgot-password/forgot-password.component';
import { ResetPasswordComponent } from './pages/auth/reset-password/reset-password.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'register-empleado', component: RegistroEmpleadoComponent },
  { path: 'register-socio', component: RegistroSocioComponent },
  { path: 'forgot-password', component: ForgotPasswordComponent },
  { path: 'reset-password', component: ResetPasswordComponent } 
];
