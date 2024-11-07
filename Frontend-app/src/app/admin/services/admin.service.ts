import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  baseUrl = environment.baseUrlEnv;

  constructor(
    private http: HttpClient
  ) { }

  usuariosClientes(){
    return this.http.get(`${this.baseUrl}/admin/usuario/2`);
  }

  usuariosModeradores(){
    return this.http.get(`${this.baseUrl}/admin/usuario/3`);
  }

  permisosUsuario(id: number){
    return this.http.get(`${this.baseUrl}/admin/permisos/${id}`);
  }

}
