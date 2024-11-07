import { Horario } from "./horario.interface";
import { Servicio } from "./servico.interfaces";

export interface Servidor{
    id?: number;
    nombre: string;
    descripcion?:string;
    //servicios: Servicio[];
    horarios?: Horario[] | undefined;

}