import { Component, OnInit } from '@angular/core';
import { Servidor} from '../../../interfaces/servidor.interface';  // Ajusta la ruta según tu estructura de carpetas
import { Servicio } from '../../../interfaces/servico.interfaces';
import { ServiciosService } from '../../services/servicios.service';
import { Horario } from '../../../interfaces/horario.interface';

@Component({
  selector: 'app-gestion-servidores',
  templateUrl: './gestion-servidores.component.html',
  styleUrls: ['./gestion-servidores.component.css']
})
export class GestionServidoresComponent implements OnInit {
  servidores: Servidor[] = [];
  servicios: Servicio[] = [];
  serviciosServidor: Servicio[] = [];  // Servicios seleccionados para el servidor
  isEdit: boolean = false;
  horaiosSelect: Horario[]
  diasSemana = [
    { nombreEsp: 'Lunes', nombreEng: 'MONDAY' },
    { nombreEsp: 'Martes', nombreEng: 'TUESDAY' },
    { nombreEsp: 'Miércoles', nombreEng: 'WEDNESDAY' },
    { nombreEsp: 'Jueves', nombreEng: 'THURSDAY' },
    { nombreEsp: 'Viernes', nombreEng: 'FRIDAY' },
    { nombreEsp: 'Sábado', nombreEng: 'SATURDAY' },
    { nombreEsp: 'Domingo', nombreEng: 'SUNDAY' }
  ];
  


  // Definir el objeto servidor con el tipo adecuado
  servidor: Servidor = {
    nombre: '',
    descripcion: '',
    horarios: []  
  };

  constructor(private servidorService: ServiciosService) { }

  ngOnInit(): void {
    this.cargarServidores();
    this.cargarServicios();
    this.inicializarHorarios();
  }

  inicializarHorarios() {
    this.servidor.horarios = this.diasSemana.map(dia => ({
      dia: dia.nombreEng,  // Guardamos el día en inglés internamente
      nombreEsp: dia.nombreEsp,  // Usaremos el nombre en español en la vista
      seleccionado: false,
      horaInicio: '09:00:00',
      horaFin: '17:00:00'
    }));
  }

  // Método para guardar un servidor (crear o editar)
  guardarServidor() {
  const serviciosSeleccionados = this.servicios.filter(s => s.seleccionado);
  this.serviciosServidor = serviciosSeleccionados;

  // Filtrar solo los días seleccionados y eliminar el nombre en español antes de enviar
  

  if (this.isEdit) {
    this.servidorService.updateServidor(this.servidor, this.serviciosServidor).subscribe({
      next: (data) => {
        console.log(data);
        alert('Servidor actualizado')
        this.ngOnInit();
      },
      error: (error) => {
        console.error(error);
      }
    });
    this.isEdit = false;
  } else {
    const horarios = this.servidor.horarios;
    this.servidor.horarios
    .filter(horario => horario.seleccionado)
    .map(horario => ({
      dia: horario.dia,  // Conservamos el nombre en inglés para el envío
      horaInicio: this.convertirHora(horario.horaInicio),
      horaFin: this.convertirHora(horario.horaFin)
    }));

// Filtra el arreglo
    const horariosSeleccionados = horarios.filter(horario => horario.seleccionado);

// Si deseas almacenar el resultado en `this.servidor.horarios`, puedes hacer lo siguiente:
    this.servidor.horarios = horariosSeleccionados;
    this.servidorService.agregarServidor(this.servidor, this.serviciosServidor, this.servidor.horarios).subscribe({
      next: (data) => {
        console.log(data);
        alert("Servidor creado");
        this.ngOnInit();
      },
      error: (error) => {
        console.error(error);
      }
    });
  }

  this.resetForm();
  this.ngOnInit();
}


  // Método para editar un servidor existente
  editarServidor(servidor: Servidor) {
    this.servidor = { ...servidor };
    this.isEdit = true;
    this.listarServiciosServidor(servidor.id!);
  }

  // Método para eliminar un servidor
  eliminarServidor(servidor: Servidor) {
    this.servidores = this.servidores.filter(s => s.id !== servidor.id);
    this.servidorService.deleteServidor(servidor.id!).subscribe({
      next: (data) => {
        console.log(data);
        this.ngOnInit();
      },
      error: (error) => {
        console.error(error);
      }
    });
  }

  cargarServidores() {
    this.servidorService.getServidores().subscribe({
      next: (servidores) => {
        this.servidores = servidores as Servidor[];
      },
      error: (error) => {
        console.error(error);
      }
    });
  }

  cargarServicios() {
    this.servidorService.getServicios().subscribe({
      next: (servicios) => {
        this.servicios = servicios as Servicio[];
      },
      error: (error) => {
        console.error(error);
      }
    });
  }

  listarServiciosServidor(id: number) {
    this.servidorService.listarServiciosServidor(id).subscribe({
      next: (data) => {
        this.serviciosServidor = data as Servicio[];
        this.servicios.forEach(servicio => {
          this.serviciosServidor.forEach(servicioSeleccionado => {
            if (servicio.id === servicioSeleccionado.id) {
              servicio.seleccionado = true;
            }
          });
        });
      },
      error: (error) => {
        console.error(error);
      }
    });
  }

  convertirHora(hora: string): { hour: number, minute: number, second: number, nano: number } {
    const [hour, minute] = hora.split(':').map(Number);
    return { hour, minute, second: 0, nano: 0 };
  }

  resetForm() {
    this.servidor = { nombre: '', descripcion: '', horarios: [] };
    this.inicializarHorarios();
    this.servicios.forEach(s => s.seleccionado = false);
  }
}
