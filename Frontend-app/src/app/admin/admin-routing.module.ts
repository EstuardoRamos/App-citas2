import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { WelcomeComponent } from './pages/welcome/welcome.component';
import { InformacionEmpresaComponent } from './pages/informacion-empresa/informacion-empresa.component';
import { GestionRolesComponent } from './pages/gestion-roles/gestion-roles.component';
import { GestionUsariosComponent } from './pages/gestion-usarios/gestion-usarios.component';
import { TipoServicioComponent } from './pages/tipo-servicio/tipo-servicio.component';
import { GestionServidoresComponent } from './pages/gestion-servidores/gestion-servidores.component';
import { CitasAdminComponent } from './pages/citas-admin/citas-admin.component';
import { CitasServidorComponent } from './pages/citas-servidor/citas-servidor.component';
import { MiPerfilComponent } from './pages/mi-perfil/mi-perfil.component';
import { VerEmpresaComponent } from './pages/ver-empresa/ver-empresa.component';

const routes: Routes = [
  {
    path: '',
    component:HomeComponent,
    children:[
      {
        path: 'Welcome',
        component: WelcomeComponent
      },
      {
        path: 'info-empresa',
        component: InformacionEmpresaComponent
      },
      {
        path: 'ver-empresa',
        component: VerEmpresaComponent
      },
      {
        path: 'gestion-roles',
        component: GestionRolesComponent
      },
      {
        path: 'gestion-usuarios',
        component: GestionUsariosComponent
      },
      {
        path: 'gestion-servidores',
        component: GestionServidoresComponent
      },
      {
        path: 'servicios',
        component: TipoServicioComponent
      },
      {
        path: 'citas-admin',
        component: CitasAdminComponent
      },
      {
        path: 'citas-servidor',
        component: CitasServidorComponent
      },
      {
        path: 'mi-perfil',
        component: MiPerfilComponent
      },
      {
        path: '**',
        component: WelcomeComponent,
        //canActivate: [loginGuard]
      },
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
