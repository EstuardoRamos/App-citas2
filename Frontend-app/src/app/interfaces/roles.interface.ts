export interface Permiso {
    id?: number;
    nombre: string;
    seleccionado?: boolean;
  }
  
  export interface Rol {
    id?: number;
    nombre: string;
    //permisos?: Permiso[];
  }

  