import { Component } from '@angular/core';
import { CitasService } from '../../services/citas.service';
import { Cita } from '../../../interfaces/citas.interface';
import { DatePipe } from '@angular/common';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';
import { AuthService } from '../../../auth/services/auth.service';
import { User } from '../../../interfaces/user.interface';

@Component({
  selector: 'app-citas',
  templateUrl: './citas.component.html',
  styleUrls: ['./citas.component.css'],
  providers: [DatePipe]
})
export class CitasComponent {
  citas: Cita[] = [];
  fechaSeleccionada: Date | null = null;
  user: User;

  constructor(
    private citasService: CitasService,
    private datePipe: DatePipe,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.obtenerUserLog();
    
  }

  listarCitas(idUser:number): void {
    this.citasService.listarCitas(idUser).subscribe({
      next: (data) => {
        this.citas = data as Cita[];
      },
      error: (error) => {
        console.error(error);
      }
    });
  }

  listarCitasFecha(): void {
    if (this.fechaSeleccionada) {
      const fechaFormateada = this.datePipe.transform(this.fechaSeleccionada, 'yyyy-MM-dd');
      this.citasService.listarCitasPorFecha(this.user.id, fechaFormateada!).subscribe({
        next: (data) => {
          this.citas = data as Cita[];
        },
        error: (error) => {
          console.error(error);
        }
      });
    }
  }

  verTodasLasCitas(): void {
    this.fechaSeleccionada = null;
    this.listarCitas(this.user.id);
  }

  obtenerUserLog(){
    this.authService.getUser().subscribe({
      next: (data) => {
        this.user = data;
        this.listarCitas(this.user.id);
      },
      error: (error) => {

      }
    })
  }

  generarComprobante(cita: Cita): void {
    const doc = new jsPDF();

    // Título y datos principales
    doc.setFontSize(18);
    doc.text('Comprobante de Cita', 10, 10);
    doc.setFontSize(12);
    doc.text(`Cliente: ${cita.usuario.nombre}`, 10, 20);
    doc.text(`Correo: ${cita.usuario.correoElectronico}`, 10, 30);
    doc.text(`Teléfono: ${cita.usuario.telefono}`, 10, 40);
    doc.text(`Fecha de Cita: ${cita.fecha}`, 10, 50);
    doc.text(`Hora de Inicio: ${cita.horaInicio}`, 10, 60);
    doc.text(`Servidor: ${cita.idServidor.nombre} (${cita.idServidor.descripcion})`, 10, 70);
    doc.text(`Servicio: ${cita.servicio.nombre}`, 10, 80);
    doc.text(`Precio: Q${cita.servicio.precio}`, 10, 90);
    doc.text(`Duración: ${cita.servicio.duracion} minutos`, 10, 100);
    doc.text(`Estado: ${cita.estadoCita.nombre}`, 10, 110);

    // Tabla de detalles (por si quieres agregar más información)
    autoTable(doc, {
      startY: 120,
      head: [['Campo', 'Valor']],
      body: [
        ['Cliente', cita.usuario.nombre],
        ['Correo Electrónico', cita.usuario.correoElectronico],
        ['Teléfono', cita.usuario.telefono],
        ['NIT', cita.usuario.nit || 'N/A'],
        ['CUI', cita.usuario.cui || 'N/A'],
        ['Fecha de Cita', cita.fecha],
        ['Hora de Inicio', cita.horaInicio],
        ['Servidor', `${cita.idServidor.nombre} - ${cita.idServidor.descripcion}`],
        ['Servicio', cita.servicio.nombre],
        ['Precio', `Q${cita.servicio.precio}`],
        ['Duración', `${cita.servicio.duracion} minutos`],
        ['Estado', cita.estadoCita.nombre],
      ],
      theme: 'striped'
    });

    // Generar PDF
    doc.save(`Comprobante_Cita_${cita.fecha}.pdf`);
  }
}
