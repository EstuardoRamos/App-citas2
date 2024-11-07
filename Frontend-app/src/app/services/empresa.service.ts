import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { Empresa, EmpresaModel } from '../interfaces/empresa.interface';

@Injectable({
  providedIn: 'root'
})
export class EmpresaService {
  private baseUrl = environment.baseUrlEnv

  constructor(
    private http: HttpClient,

  ) { }

  getEmpresa(){
    return this.http.get(`${this.baseUrl}/empresa/1`);
  }

  updateEmpresa(empresa: FormData){
    return this.http.put(`${this.baseUrl}/empresa`, empresa);
  }

  guardarEmpresa(empresa: FormData){
    return this.http.post(`${this.baseUrl}/empresa`, empresa);
  }

  getTiposEmpresa(){
    return this.http.get(`${this.baseUrl}/empresa/tipos`);
  }
}
