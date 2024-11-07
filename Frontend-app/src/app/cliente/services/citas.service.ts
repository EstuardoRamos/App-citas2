import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { Cita } from '../../interfaces/citas.interface';

@Injectable({
  providedIn: 'root'
})
export class CitasService {

  private baseUrl = environment.baseUrlEnv

  constructor(
    private http: HttpClient,

  ) { }

  crearCita(cita: Cita){
    return this.http.post(`${this.baseUrl}/cliente/cita`,cita);
  }

  listarTodasCitas(){
    return this.http.get(`${this.baseUrl}/admin/citas`);
  }

  listarCitas(id: number){
    return this.http.get(`${this.baseUrl}/citas/${id}`);
  }

  listarCitasPorFecha(id: number, fecha: string){
    return this.http.get(`${this.baseUrl}/cliente/citas/${id}/${fecha}`);
  }

  listarCitasServidor(id: number){
    return this.http.get(`${this.baseUrl}/admin/citas/servidor/${id}`);
  }

  listarCitasServidorFecha(id: number, fecha: string){
    return this.http.get(`${this.baseUrl}/admin/citas/servidor/${id}/${fecha}`);
  }
}
