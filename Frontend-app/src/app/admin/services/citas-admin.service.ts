import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { Cita } from '../../interfaces/citas.interface';

@Injectable({
  providedIn: 'root'
})
export class CitasAdminService {

  private baseUrl = environment.baseUrlEnv

  constructor(
    private http: HttpClient,

  ) { }



  listarCitasFecha(fecha: string){
    return this.http.get(`${this.baseUrl}/admin/citas/${fecha}`);
  }

  listadorEstadosCitas(){
    return this.http.get(`${this.baseUrl}/admin/citas/estado`);
  }

  actualizarEstadoCita(idCita:number, idEstado:number){
    return this.http.put(`${this.baseUrl}/admin/citas/estado`,{idCita, idEstado})
  }
}
