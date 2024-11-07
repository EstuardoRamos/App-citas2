import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog'; // Importar MatDialogRef

@Component({
  selector: 'app-dialogo-servicio',
  templateUrl: './dialogo-servicio.component.html'
})
export class DialogoServicioComponent {
  servicioSeleccionado: string | null = null;

  // Ejemplo de servicios (esto podría ser dinámico si lo traes de un backend)
  servicios = [
    { id: 1, nombre: 'Extracción de muela', duracion: '01:30' },
    { id: 2, nombre: 'Limpieza dental', duracion: '00:45' }
  ];

  constructor(public dialogRef: MatDialogRef<DialogoServicioComponent>) {}

  // Método para cerrar el diálogo
  cerrarDialogo(): void {
    this.dialogRef.close(this.servicioSeleccionado); // Devuelve el servicio seleccionado (opcional)
  }
}
