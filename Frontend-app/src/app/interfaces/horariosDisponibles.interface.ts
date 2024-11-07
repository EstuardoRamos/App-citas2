import { Servidor } from "./servidor.interface";

export interface HorariosDisponibles{
    horaInicio: string,
    horaFin: string,
    servidor: Servidor
}