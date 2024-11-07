import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CitasComponent } from './pages/citas/citas.component';
import { HomeComponent } from './pages/home/home.component';
import { AgendaComponent } from './pages/agenda/agenda.component';
import { CalendarComponent } from './pages/calendar/calendar.component';
import { CalendarioComponent } from './pages/calendario/calendario.component';
import { MiPerfilComponent } from './pages/mi-perfil/mi-perfil.component';
import { CambioPasswordComponent } from './pages/cambio-password/cambio-password.component';

const routes: Routes = [
  {
    path: '',
    component:HomeComponent,
    children:[
      {
        path: 'citas',
        component: CitasComponent
      },
      {
        path: 'agenda',
        component: AgendaComponent
      },
      {
        path: 'perfil',
        component: MiPerfilComponent
      },
      {
        path: 'calendar',
        component: CalendarComponent
      },
      {
        path: 'calendario',
        component: CalendarioComponent
      },
      {
        path: 'cambio-contrasenia',
        component: CambioPasswordComponent
      },
      {
        path: '**',
        component: AgendaComponent,
        //canActivate: [loginGuard]
      },
      
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClienteRoutingModule { }
