import { Component } from '@angular/core';
import { ServiciosService } from '../../../admin/services/servicios.service';
import { Servicio } from '../../../interfaces/servico.interfaces';
import { Servidor } from '../../../interfaces/servidor.interface';
import { HorariosDisponibles } from '../../../interfaces/horariosDisponibles.interface';
import { UserService } from '../../../services/user.service';
import { User } from '../../../interfaces/user.interface';
import { Cita } from '../../../interfaces/citas.interface';
import { CitasService } from '../../services/citas.service';
import { AuthService } from '../../../auth/services/auth.service';

@Component({
  selector: 'app-agenda',
  templateUrl: './agenda.component.html',
  styleUrls: ['./agenda.component.css']
})
export class AgendaComponent {


  constructor(
    private serviciosService: ServiciosService,
    //private userService: UserService,
    private authService: AuthService,
    private citaService: CitasService
  ){
    this.listarServicios();
    this.obtnerUsuario();

  }
  services: Servicio[];
  servers: Servidor[];
  servicioSelect: Servicio;
  servidorSelect: Servidor;
  servicioId: number;
  servidorId: number;
  fecha: Date;
  horariosDisponibles: HorariosDisponibles[];
  user: User;
  cita: Cita;
  fechaFormateada: string;
  //services: string[] = ['Consulta', 'Terapia', 'Asesoría'];
  //servers: string[] = ['Servidor 1', 'Servidor 2', 'Servidor 3'];
  

  selectedService: string | null = null;
  selectedServer: string | null = null;
  selectedDate: Date | null = null;

  // Actualiza cuando el servicio cambia
  onServiceChange() {
    console.log(this.selectedService)
    const jsonString = JSON.stringify(this.selectedService);

    // Luego, parseamos el JSON de nuevo a un objeto
    this.servicioSelect = JSON.parse(jsonString) as Servicio;
    this.servicioId=this.servicioSelect.id;
    this.listarServidoresPorServicio(this.servicioId)
    this.selectedServer = null; // Reinicia la selección del servidor
    this.selectedDate = null;   // Reinicia la selección de la fecha
    this.horariosDisponibles = []; // Reinicia horarios
    // Aquí podrías cargar servidores relacionados con el servicio seleccionado si es necesario
  }

  // Actualiza cuando el servidor cambia
  onServerChange() {
    const jsonString = JSON.stringify(this.selectedServer);

    // Luego, parseamos el JSON de nuevo a un objeto
    this.servidorSelect = JSON.parse(jsonString) as Servidor;
    this.servidorId=this.servidorSelect.id;
    this.selectedDate = null; // Reinicia la fecha
    this.horariosDisponibles = []; // Reinicia horarios
    // Aquí podrías cargar disponibilidad de fechas para el servidor seleccionado
  }

  // Actualiza cuando la fecha cambia
  onDateChange() {

    this.fecha= this.selectedDate;
    const anio = this.fecha.getFullYear();
    const mes = String(this.fecha.getMonth() + 1).padStart(2, '0'); // +1 porque los meses empiezan desde 0
    const dia = String(this.fecha.getDate()).padStart(2, '0');

// Formatear como YYYY/MM/DD
    this.fechaFormateada = `${anio}-${mes}-${dia}`;
    const datos = {
      servicioId: this.servicioId,
      servidorId: this.servidorId,
      fecha: this.fechaFormateada
    }
    this.serviciosService.obtenerHorariosDisponibles(datos).subscribe({
      next: (horarios) => {
        this.horariosDisponibles = horarios as HorariosDisponibles[];
      },
      error: (error) => {
        alert('Error: '+error.error.mensaje)
      }
    })
    
    // Aquí podrías cargar horarios de disponibilidad para la fecha seleccionada
  }

  seleccionarHorario(horario: any) {
    
    console.log('Horario seleccionado:', horario);
    this.cita={
      usuario:this.user,
      servicio: this.servicioSelect,
      horaInicio: horario.horaInicio,
      fecha: this.fechaFormateada,
      idServidor: this.servidorSelect,
      estadoCita:{
        id:1,
        nombre:"nad"
      }

    }
    this.crearCita();
    // Aquí puedes añadir lógica adicional para manejar la selección del horario
  }

  listarServidoresPorServicio(id: number){
    this.serviciosService.listarServidoresServicio(id).subscribe({
      next: (data) => {
          this.servers = data as Servidor[]
      },
      error: (error) => {
        console.error('Error:', error);
      }
    })

  }


  crearCita(){
    
    this.citaService.crearCita(this.cita).subscribe({
      next: (data) => {
        console.log(data);
        alert('Cita se ha creado '+ data)
      },
      error: (error) => {
        console.error('Error:', error);
      }
    })
  }

  listarServicios(){
    this.serviciosService.getServicios().subscribe({
      next: (servicios) => {
        this.services = servicios as Servicio[];
      },
      error: (error) => {
        console.error('Error al obtener servicios:', error);
      }
    })

  }

  obtnerUsuario(){
    this.authService.getUser().subscribe({
      next: (user) => {

        this.user = user as User;
        console.log(this.user);
      },
      error: (error) => {
        console.error('Error al obtener usuario:', error);
      }

    });
  }
}
