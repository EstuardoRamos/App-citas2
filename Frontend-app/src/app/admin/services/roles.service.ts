import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { Permiso, Rol } from '../../interfaces/roles.interface';

@Injectable({
  providedIn: 'root'
})
export class RolesService {
  baseUrl = environment.baseUrlEnv;

  constructor(
    private http: HttpClient
  ) { }

  getRoles(){
    return this.http.get(`${this.baseUrl}/admin/rol`);
  }
  gerRolUsuario(id: number){
    return this.http.get(`${this.baseUrl}/admin/rol/${id}`);
  }

  getPermisos(){
    return this.http.get(`${this.baseUrl}/admin/permisos`);
  }

  agregarRol(rol: Rol, permisos: Permiso[]){
    return this.http.post(`${this.baseUrl}/admin/crearRol`, {rol, permisos});
  }

  updateRol(rol: Rol, permisos: Permiso[]){
    return this.http.put(`${this.baseUrl}/admin/rol`, {rol, permisos});
  }

  asignarRol(idUsuario: number, idRol:number){
    return this.http.post(`${this.baseUrl}/admin/permisos/asignar`, {idUsuario, idRol});
  }

  deleteRol(id: number ){
    return this.http.delete(`${this.baseUrl}/admin/rol/${id}`);
  }

  listarPermisosRol(id: number){
    return this.http.get(`${this.baseUrl}/admin/rol/permisos/${id}`);
  }

  listarUsuarios(id: number){
    return this.http.get(`${this.baseUrl}/admin/usuario/${id}`);
  }

  listarUsuarioModeradores(){
    return this.http.get(`${this.baseUrl}/admin/usuario/3`);
  }
  listarUsuarioClientes(){
    return this.http.get(`${this.baseUrl}/admin/usuario/2`);
  }
}
