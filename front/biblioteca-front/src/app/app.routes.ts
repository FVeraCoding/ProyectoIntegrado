import { Routes } from '@angular/router';
import { LoginComponent } from './pages/auth/login/login.component';
import { RegistroEmpleadoComponent } from './pages/empleados/registro-empleado/registro-empleado.component';
import { RegistroSocioComponent } from './pages/socios/registro-socios/registro-socios.component';
import { ForgotPasswordComponent } from './pages/auth/forgot-password/forgot-password.component';
import { ResetPasswordComponent } from './pages/auth/reset-password/reset-password.component';
import { HomeComponent } from './pages/home/home.component';
import { LibrosComponent } from './pages/libros/libros.component';
import { LibroDetalleComponent } from './pages/libros/libro-detalle/libro-detalle.component';
import { ClubComponent } from './pages/clubs/clubs.component';
import { EventoComponent } from './pages/eventos/eventos.component';
import { UsuarioComponent } from './pages/usuario/usuario.component';
import { SociosComponent } from './pages/socios/socios/socios.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'register-empleado', component: RegistroEmpleadoComponent },
  { path: 'register-socio', component: RegistroSocioComponent },
  { path: 'forgot-password', component: ForgotPasswordComponent },
  { path: 'reset-password', component: ResetPasswordComponent },
  { path: 'home', component: HomeComponent },
  { path: 'libros', component: LibrosComponent },
  { path: 'libros/:id', component: LibroDetalleComponent },
  { path: 'clubs', component: ClubComponent},
  { path: 'eventos', component: EventoComponent },
  { path: 'perfil', component: UsuarioComponent},
  { path: 'socios', component: SociosComponent }
];
