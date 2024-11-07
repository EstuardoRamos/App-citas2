import { Component } from '@angular/core';
import { Cita, EstadoCita } from '../../../interfaces/citas.interface';
import { DatePipe } from '@angular/common';
import jsPDF from 'jspdf';
import autoTable from 'jspdf-autotable';
import { AuthService } from '../../../auth/services/auth.service';
import { User } from '../../../interfaces/user.interface';
import { CitasService } from '../../../cliente/services/citas.service';
import { Servidor } from '../../../interfaces/servidor.interface';
import { ServiciosService } from '../../services/servicios.service';
import { EstadoCitaDialogComponent } from '../estado-cita-dialog/estado-cita-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { CitasAdminService } from '../../services/citas-admin.service';

@Component({
  selector: 'app-citas-servidor',
  templateUrl: './citas-servidor.component.html',
  styleUrl: './citas-servidor.component.css',
  providers: [DatePipe]
})
export class CitasServidorComponent {
  citas: Cita[] = [];
  fechaSeleccionada: Date | null = null;
  user: User;
  selectedServer: string | null = null;
  servers: Servidor[];
  estadosCita: EstadoCita[];

  constructor(
    private citasService: CitasService,
    private citasServiceA: CitasAdminService,
    private datePipe: DatePipe,
    private authService: AuthService,
    private serviciosService: ServiciosService,
    public dialog: MatDialog
  ) {}

  ngOnInit(): void {
    //this.listarCitas();
    this.listarServidores();
    this.listarEstadosCitas();
    
  }

  listarCitas(): void {
    this.citasService.listarCitas(1).subscribe({
      next: (data) => {
        this.citas = data as Cita[];
      },
      error: (error) => {
        console.error(error);
      }
    });
  }

  listarEstadosCitas(){
    this.citasServiceA.listadorEstadosCitas().subscribe({
      next: (data) => {
         this.estadosCita= data as EstadoCita[] 
      },
      error: (error) => {
        console.error(error);
      }
    })
  }

  listarCitasFecha(): void {
    if (this.fechaSeleccionada) {
      const fechaFormateada = this.datePipe.transform(this.fechaSeleccionada, 'yyyy-MM-dd');
      this.citasService.listarCitasServidorFecha(parseInt(this.selectedServer), fechaFormateada!).subscribe({
        next: (data) => {
          this.citas = data as Cita[];
          if (this.citas.length<1) {
            alert('No tiene citas para este servidor')
          }
        },
        error: (error) => {
          console.error(error);
        }
      });
    }
  }

  verTodasLasCitas(): void {
    this.fechaSeleccionada = null;
    this.listarCitas();
  }

  listarServidores(){
    this.serviciosService.getServidores().subscribe({
      next: (data) => {
          this.servers = data as Servidor[]
      },
      error: (error) => {
        console.error('Error:', error);
      }
    })

  }
  
  onServerChange(){
    this.citasService.listarCitasServidor(parseInt(this.selectedServer)).subscribe({
      next: (data) => {
        this.citas = data as Cita[];
        if (this.citas.length<1) {
          alert('No tiene citas para este servidor')
        }
      },
      error: (error) => {
        console.error(error);
      }
    })

  }

  obtenerUserLog(){
    this.authService.getUser().subscribe({
      next: (data) => {
        this.user = data;
      },
      error: (error) => {

      }
    })
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


  updateEstadoCita(idCita: number, idEstado){
    this.citasServiceA.actualizarEstadoCita(idCita, idEstado).subscribe({
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
