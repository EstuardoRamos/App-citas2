import { Component } from '@angular/core';
import { Servicio } from '../../../interfaces/servico.interfaces';
import { ServiciosService } from '../../services/servicios.service';

@Component({
  selector: 'app-tipo-servicio',
  templateUrl: './tipo-servicio.component.html',
  styleUrl: './tipo-servicio.component.css'
})
export class TipoServicioComponent {

  constructor(
    private servicioService: ServiciosService
  ){}
  // Lista de servicios existentes (guardados en minutos)

  ngOnInit(){
    this.cargarServicios();
  }
  servicios: Servicio[] = [
    { id: 1, nombre: 'Extracción de muela', duracion: 90, precio: 100 }, // Duración en minutos
    { id: 2, nombre: 'Limpieza dental', duracion: 45, precio: 50 }
  ];

  // Objeto inicial para un nuevo servicio
  servicio: Servicio = { id: 0 , nombre: '', duracion: 0, precio: 0 };

  // Variable temporal para mostrar y editar la duración en formato HH:MM
  duracionTemporal: string = '';

  // Método para guardar un servicio (crear o editar)
  guardarServicio() {
    // Convertir la duración de formato HH:MM a minutos antes de guardarlo
    const duracionEnMinutos = this.convertirDuracionAMinutos(this.duracionTemporal);

    // Asignar la duración en minutos al servicio
    this.servicio.duracion = duracionEnMinutos;

    if (this.servicio.id) {
      // Editar servicio existente
      const index = this.servicios.findIndex(s => s.id === this.servicio.id);
      this.servicios[index] = { ...this.servicio };
      this.servicioService.updateServicio(this.servicio).subscribe({
        next: (data) => {
          console.log('Servicio editado correctamente')
          alert('Servicio editado correctamente')
          this.ngOnInit();
        } 
                    ,
        error: (error) => alert('Error: '+error.error.mensaje)
      })
    } else {
      // Crear nuevo servicio
      //this.servicio.id = this.servicios.length + 1; // Genera un nuevo ID
      //this.servicios.push({ ...this.servicio });
      this.servicioService.crearServicio(this.servicio).subscribe({
        next: (servicio) => {
          console.log('Servicio creado con éxito:', servicio);
          alert('Servico creado con exito')
        },
        error(err) {
            console.error('Error al crear servicio:', err);
            alert('Error: '+err.error.mensaje)
        },
      })
    }

    this.resetForm();
  }

  // Método para editar un servicio existente
  editarServicio(servicio: Servicio) {
    // Convertir minutos a formato HH:MM para mostrar en el formulario
    const duracionHHMM = this.convertirMinutosADuracion(servicio.duracion!); // Uso '!' para asegurar que duracion no es undefined
    this.duracionTemporal = duracionHHMM; // Asignar la duración formateada
    this.servicio = { ...servicio }; // Clonar el servicio para editarlo
  }

  // Método para eliminar un servicio
  eliminarServicio(servicio: Servicio) {
    this.servicios = this.servicios.filter(s => s.id !== servicio.id);
  }

  // Método para limpiar el formulario después de guardar
  resetForm() {
    this.servicio = { id: undefined, nombre: '', duracion: 0, precio: 0 };
    this.duracionTemporal = '';
  }

  // Función para convertir la duración de formato HH:MM a minutos (para la DB)
  convertirDuracionAMinutos(duracion: string): number {
    const [horas, minutos] = duracion.split(':').map(Number);
    return horas * 60 + minutos; // Convertir horas a minutos y sumar los minutos
  }

  // Función para convertir minutos a formato HH:MM (para mostrar en la UI)
  convertirMinutosADuracion(minutosTotales: number): string {
    const horas = Math.floor(minutosTotales / 60);
    const minutos = minutosTotales % 60;
    return `${this.padZero(horas)}:${this.padZero(minutos)}`; // Formato HH:MM
  }

  // Función para agregar un cero a las horas o minutos si es menor que 10
  padZero(valor: number): string {
    return valor < 10 ? '0' + valor : valor.toString();
  }

  cargarServicios(){
    this.servicioService.getServicios().subscribe({
      next: (servicios) => {
        this.servicios = servicios as Servicio[];
      },
      error: (error: any) => {
        console.error('Error al cargar servicios:', error);
      }
    })
  }
}
