import { Component } from '@angular/core';
import { EmpresaService } from '../../../services/empresa.service';
import { Empresa } from '../../../interfaces/empresa.interface';

@Component({
  selector: 'app-ver-empresa',
  templateUrl: './ver-empresa.component.html',
  styleUrl: './ver-empresa.component.css'
})
export class VerEmpresaComponent {

  constructor(private empresaService: EmpresaService) { }
  empresa: Empresa = {
    nombre: 'Mi Empresa',
    urlImagen: 'https://example.com/logo.png',  // Cambia esta URL a la imagen de la empresa
    direccion: '123 Calle Principal, Ciudad',
    horaInicio: '08:00 AM',
    horaCierre: '06:00 PM'
  };

  ngOnInit(): void {
    // Cargar datos existentes si es edición
    this.cargarDatosEmpresa();
  
    //this.cargarTiposEmpresa(); // Cargar tipos de empresa
  }


  cargarDatosEmpresa() {
    // Lógica para cargar los datos de la empresa
    this.empresaService.getEmpresa().subscribe({
      next: (data) => {
        this.empresa = data as Empresa;
        console.log(this.empresa);
      },
      error: (error) => {
        console.error(error);
      }
    });
    return this.empresa;
  }

}
