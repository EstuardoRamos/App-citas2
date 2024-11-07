import { Component } from '@angular/core';
import { Cita, EstadoCita } from '../../../interfaces/citas.interface';
import { DatePipe } from '@angular/common';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';
import { CitasAdminService } from '../../services/citas-admin.service';
import { error } from 'console';
import { MatDialog } from '@angular/material/dialog';
import { EstadoCitaDialogComponent } from '../estado-cita-dialog/estado-cita-dialog.component';

@Component({
  selector: 'app-citas-admin',
  templateUrl: './citas-admin.component.html',
  styleUrl: './citas-admin.component.css',
  providers: [DatePipe]
})
export class CitasAdminComponent {
  citas: Cita[] = [];
  estadosCita: EstadoCita[];
  fechaSeleccionada: Date | null = null;
  fecha: string;

  constructor(
    private citasService: CitasAdminService,
    private datePipe: DatePipe,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    //this.obtenerFechaActual()
    this.fechaSeleccionada=new Date();
    this.listarCitasFecha();
    this.listarEstadosCitas();

  }

  

  

  listarCitasFecha(): void {
    if (this.fechaSeleccionada) {
      const fechaFormateada = this.datePipe.transform(this.fechaSeleccionada, 'yyyy-MM-dd');
      this.citasService.listarCitasFecha(fechaFormateada!).subscribe({
        next: (data) => {
          this.citas = data as Cita[];
        },
        error: (error) => {
          console.error(error);
        }
      });
    }
  }

  openDialog(cita: Cita): void {
    const dialogRef = this.dialog.open(EstadoCitaDialogComponent, {
      width: '300px',
      data: { estados: this.estadosCita }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result !== undefined) { // Si se seleccionó un estado
        this.updateEstadoCita(cita.id, result);
      }
    });
  }

  listarEstadosCitas(){
    this.citasService.listadorEstadosCitas().subscribe({
      next: (data) => {
         this.estadosCita= data as EstadoCita[] 
      },
      error: (error) => {
        console.error(error);
      }
    })
  }

  updateEstadoCita(idCita: number, idEstado){
    this.citasService.actualizarEstadoCita(idCita, idEstado).subscribe({
      next: (data) => {
        alert('Cita actualizada exitosamente')
        this.listarCitasFecha();
      },
      error:(error)=>{
        alert('Error al actualizar la cita')
        console.error(error)
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
