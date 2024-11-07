import { TimeInterval } from "rxjs/internal/operators/timeInterval";

export interface Empresa{
    id?: number;
    nombre: string;
    direccion: string;
    horaInicio: string;
    horaCierre: string;
    tipoEmpresa?:TipoEmpresa
    urlImagen?: string;
    imagen?: File;
}

export interface TipoEmpresa{
    id?: number;
    nombre?: string;
}

export interface EmpresaModel{
    empresaModel?: Empresa;
    imagen?: FormData
}