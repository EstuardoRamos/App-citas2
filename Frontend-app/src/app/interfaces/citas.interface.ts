import { Servicio } from "./servico.interfaces";
import { Servidor } from "./servidor.interface";
import { User } from "./user.interface";

export interface Cita{
    id?: number;
    usuario?: User;
    servicio?: Servicio;
    horaInicio?: string;
    //horaFin: string;
    idServidor: Servidor;
    fecha: string;
    estadoCita: EstadoCita;

}

export interface EstadoCita{
    id: number;
    nombre: string;
}