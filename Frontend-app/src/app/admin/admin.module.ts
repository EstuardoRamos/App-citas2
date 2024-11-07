import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RouterOutlet } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';

import { AdminRoutingModule } from './admin-routing.module';
import { InformacionEmpresaComponent } from './pages/informacion-empresa/informacion-empresa.component';
import { FormsModule } from '@angular/forms';
import { GestionRolesComponent } from './pages/gestion-roles/gestion-roles.component';
import { GestionUsariosComponent } from './pages/gestion-usarios/gestion-usarios.component';
import { HomeComponent } from './pages/home/home.component';
import { MaterialModule } from '../material/material.module';
import { TipoServicioComponent } from './pages/tipo-servicio/tipo-servicio.component';
import { EmpresaService } from '../services/empresa.service';
import { RolesService } from './services/roles.service';
import { ServiciosService } from './services/servicios.service';
import { GestionServidoresComponent } from './pages/gestion-servidores/gestion-servidores.component';
import { TranslatePipe } from '../translate.pipe';
import { AdminService } from './services/admin.service';
import { AuthService } from '../auth/services/auth.service';
import { UserService } from '../services/user.service';
import { CitasAdminComponent } from './pages/citas-admin/citas-admin.component';
import { CitasAdminService } from './services/citas-admin.service';
import { EstadoCitaDialogComponent } from './pages/estado-cita-dialog/estado-cita-dialog.component';
import { MiPerfilComponent } from './pages/mi-perfil/mi-perfil.component';
import { CitasServidorComponent } from './pages/citas-servidor/citas-servidor.component';
import { CitasService } from '../cliente/services/citas.service';
import { CambioPasswordComponent } from './pages/cambio-password/cambio-password.component';
import { VerEmpresaComponent } from './pages/ver-empresa/ver-empresa.component';


@NgModule({
  declarations: [
    InformacionEmpresaComponent,
    GestionRolesComponent,
    GestionUsariosComponent,
    HomeComponent,
    TipoServicioComponent,
    GestionServidoresComponent,
    TranslatePipe,
    CitasAdminComponent,
    EstadoCitaDialogComponent,
    MiPerfilComponent,
    CitasServidorComponent,
    CambioPasswordComponent,
    VerEmpresaComponent 
  ],
  imports: [
    CommonModule,
    AdminRoutingModule,
    FormsModule,
    RouterOutlet,
    MaterialModule,
    HttpClientModule 
    

    
  ],
  providers:[
    EmpresaService,
    RolesService,
    ServiciosService,
    AdminService,
    AuthService,
    UserService,
    CitasAdminService,
    CitasService
    
  ]
})
export class AdminModule { }
