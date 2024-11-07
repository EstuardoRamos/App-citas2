import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CalendarModule, DateAdapter } from 'angular-calendar';
import { adapterFactory } from 'angular-calendar/date-adapters/date-fns';

import { ClienteRoutingModule } from './cliente-routing.module';
import { MaterialModule } from '../material/material.module';
import { CitasComponent } from './pages/citas/citas.component';
import { AgendaComponent} from './pages/agenda/agenda.component';
import { FormsModule } from '@angular/forms';
import { CalendarComponent } from './pages/calendar/calendar.component';
import { DialogoServicioComponent } from './pages/dialogo-servicio/dialogo-servicio.component';
import { CalendarioComponent } from './pages/calendario/calendario.component';
import { ServiciosService } from '../admin/services/servicios.service';
import {  HttpClientModule } from '@angular/common/http';
import { UserService } from '../services/user.service';
import { CitasService } from './services/citas.service';
import { HomeComponent } from './pages/home/home.component';
import { AuthService } from '../auth/services/auth.service';
import { ClienteService } from './services/cliente.service';
import { MiPerfilComponent } from './pages/mi-perfil/mi-perfil.component';
import { CambioPasswordComponent } from './pages/cambio-password/cambio-password.component';


@NgModule({
  declarations: [
    CitasComponent,
    HomeComponent,
    AgendaComponent,
    CalendarComponent,
    DialogoServicioComponent,
    CalendarioComponent,
    MiPerfilComponent,
    CambioPasswordComponent
  ],
  imports: [
    CommonModule,
    ClienteRoutingModule,
    MaterialModule,
    FormsModule,
    HttpClientModule,
    //BrowserModule,
    //BrowserAnimationsModule,
    CalendarModule.forRoot({ provide: DateAdapter, useFactory: adapterFactory })

  ],
  providers: [
    ServiciosService,
    UserService,
    CitasService,
    ClienteService,
    AuthService
  ]
})
export class ClienteModule { }
