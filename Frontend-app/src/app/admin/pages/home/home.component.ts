import { Component } from '@angular/core';
import { MaterialModule } from '../../../material/material.module';
import { RouterOutlet } from '@angular/router';
import { AdminService } from '../../services/admin.service';
import { Permiso } from '../../../interfaces/roles.interface';
import { AuthService } from '../../../auth/services/auth.service';
import { User } from '../../../interfaces/user.interface';
import { Empresa } from '../../../interfaces/empresa.interface';
import { EmpresaService } from '../../../services/empresa.service';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  user:User ={
    nombre: '',
    correoElectronico: '',
    tipoUsuario: {
      id: 0,
      nombre: 'cliente',
    },
    cui: 0,
    telefono:0,
    nit:0, // Campo opcional
    fechaNacimiento: new Date() ,
  }

  permisos!: Permiso[] ;
  empresa: boolean;
  servidores: boolean;
  rolesPermisos: boolean;
  usuarios: boolean;
  citas: boolean;
  citasServidor: boolean;
  servicios: boolean;
  reportes: boolean;

  empresa1: Empresa = {
    nombre: 'Mi Empresa',
    urlImagen: 'https://example.com/logo.png',  // Cambia esta URL a la imagen de la empresa
    direccion: '123 Calle Principal, Ciudad',
    horaInicio: '08:00 AM',
    horaCierre: '06:00 PM'
  };

  constructor(
    private adminService:AdminService,
    private authService: AuthService,
    private empresaService: EmpresaService
  ){}

  ngOnInit(){
    this.obtenerUsuario();
    this.cargarDatosEmpresa()
    
  }

  cargarDatosEmpresa() {
    // Lógica para cargar los datos de la empresa
    this.empresaService.getEmpresa().subscribe({
      next: (data) => {
        this.empresa1 = data as Empresa;
        console.log(this.empresa);
      },
      error: (error) => {
        console.error(error);
      }
    });
    return this.empresa;
  }


  obtenerUsuario(){
    this.authService.getUser().subscribe({
      next: (user:User) => {
        
        this.user = user;
        this.definirTipo(this.user.tipoUsuario.id)
        
      },
      error: (error) => {
        console.error(error);
      }
    })
  }

  definirTipo(idTipo: number){
    
    
    if (idTipo==1) {
      this.asignarPermisosAdmin()
      //alert('todos los permisos asignados');
    }else{
      this.listarPermisosUsuario();
    }
  }

  listarPermisosUsuario(){
    this.adminService.permisosUsuario(this.user.id).subscribe({
      next: (data) => {
        this.permisos = data as Permiso[];
        if (this.permisos.length>0) {
          this.asignarPermisos()
        }else{
          alert('No tienes asignado ningun rol en la organizacion.')
        }
      },
      error: (error) => {
        console.error(error);
      }
    })

  }

  asignarPermisos(){
    for (let index = 0; index < this.permisos.length; index++) {
      const element = this.permisos[index];
      switch(this.permisos[index].id){
        case 1:
          this.empresa = true;
          break;
        case 2:
          this.servidores = true;
          break;
        case 3:
          this.rolesPermisos = true;
          break;
        case 4:
          this.usuarios = true;
          break;
        case 5:
          this.citas = true;
          break;
        case 6:
          this.citasServidor = true;
          break;
        case 7:
          this.servicios = true;
          break;
        case 8:
          this.reportes = true;
          break;
      }
      
    }
  }

  asignarPermisosAdmin(){
    this.empresa=true ;
    this.servidores=true;
    this.rolesPermisos=true;
    this.usuarios=true;
    this.citas= true;
    this.citasServidor= true;
    this.servicios= true;
    this.reportes= true;

  }

}
