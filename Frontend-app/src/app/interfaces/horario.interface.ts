export interface Horario {
    dia: string;  // "MONDAY", "TUESDAY", etc.
    horaInicio: string;
    horaFin: string;
    seleccionado?: boolean;
  }