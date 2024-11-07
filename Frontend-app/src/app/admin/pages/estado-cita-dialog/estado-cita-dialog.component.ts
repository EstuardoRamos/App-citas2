import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { EstadoCita } from '../../../interfaces/citas.interface';

@Component({
  selector: 'app-estado-cita-dialog',
  templateUrl: './estado-cita-dialog.component.html',
})
export class EstadoCitaDialogComponent {
  estadosCita: EstadoCita[];
  selectedEstado: number;

  constructor(
    public dialogRef: MatDialogRef<EstadoCitaDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { estados: EstadoCita[] }
  ) {
    this.estadosCita = data.estados;
  }

  onCancel(): void {
    this.dialogRef.close();
  }

  onConfirm(): void {
    this.dialogRef.close(this.selectedEstado);
  }
}
