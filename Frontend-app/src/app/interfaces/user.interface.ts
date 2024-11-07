import { Rol } from "./roles.interface";

export interface User {
    id?: number;
    cui?: number;
    nombre?: string;
    contrasenia?: string;
    tipoUsuario?: TipoUsuario;
    telefono?: number;
    correoElectronico?: string;
    nit?: number;
    fechaNacimiento?:Date;
    rol?: Rol;
    a2f?: boolean
  }

export interface TipoUsuario{
  id?: number;
  nombre?: string;
}