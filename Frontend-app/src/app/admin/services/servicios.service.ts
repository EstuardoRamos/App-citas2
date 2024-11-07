import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { Servicio } from '../../interfaces/servico.interfaces';
import { Servidor } from '../../interfaces/servidor.interface';
import { Horario } from '../../interfaces/horario.interface';

@Injectable({
  providedIn: 'root'
})
export class ServiciosService {

  baseUrl = environment.baseUrlEnv;

  constructor(
    private http: HttpClient
  ) { }


  getServicios(){
    return this.http.get(`${this.baseUrl}/citas/servicio`);
  }

  crearServicio(servicio: Servicio){
    return this.http.post(`${this.baseUrl}/citas/servicio`, servicio);
  }

  updateServicio(servicio: Servicio){
    return this.http.put(`${this.baseUrl}/citas/servicio`, servicio);
  }

  getServidores(){
    return this.http.get(`${this.baseUrl}/citas/servidor`);
  }
  updateServidor(servidor: Servidor, servicios: Servicio[]){
    console.log({servidor, servicios})
    return this.http.put(`${this.baseUrl}/citas/servidor`, {servidor, servicios});
  }
  deleteServidor(id: number){
    return this.http.delete(`${this.baseUrl}/citas/servidor`);
  }
  agregarServidor(servidor: Servidor, servicios: Servicio[], horarios: Horario[]){
    console.log({servidor, servicios})
    return this.http.post(`${this.baseUrl}/citas/servidor`, {servidor, servicios, horarios});

  }

  listarServiciosServidor(id: number){
    return this.http.get(`${this.baseUrl}/citas/servicios/${id}`);
  }
  listarServidoresServicio(id: number){
    return this.http.get(`${this.baseUrl}/cliente/citas/servidores/${id}`);
  }

  obtenerHorariosDisponibles(datos: any){
    console.log(datos);
    
    return this.http.post(`${this.baseUrl}/cliente/cita/consultarHorario`, datos);
  }
}
