import { Component } from '@angular/core';
import { Permiso, Rol } from '../../../interfaces/roles.interface';
import { RolesService } from '../../services/roles.service';
import { error } from 'console';

@Component({
  selector: 'app-gestion-roles',
  templateUrl: './gestion-roles.component.html',
  styleUrl: './gestion-roles.component.css'
})
export class GestionRolesComponent {
  permisos: Permiso[] = [];
  permisosRol:Permiso[] = [] ;
  isEdit: boolean= false;

  // Definir el objeto rol con el tipo adecuado
  rol: Rol = {
    nombre: '',
    //descripcion: '',
    //permisos: []
  };

  ngOnInit(){
    this.cargarRoles();
    this.cargarPermisos();
  }

  roles: Rol[] = [];

  constructor(private rolService: RolesService) { }

  guardarRol() {
    // Filtrar los permisos seleccionados
    const permisosSeleccionados = this.permisos.filter(p => p.seleccionado);
    this.permisosRol = permisosSeleccionados;
    const permiso = permisosSeleccionados.map(permiso => permiso.id);

    // Lógica para guardar el rol (enviar al backend o agregar a la lista local)
    this.roles.push({ ...this.rol });
    if(this.isEdit){
      
      this.rolService.updateRol(this.rol, this.permisosRol).subscribe({
        next: (data) => {
          alert('Se actualizo el rol.')
        },
        error: (error)=>{
          console.error(error);
        }
      })
      this.isEdit=false;
      this.rol = { nombre: '', };
      this.permisos.forEach(p => p.seleccionado = false);
      this.ngOnInit()
      return;
    }
    console.log("guardando");
    console.log(this.rol,this.permisosRol );
    this.rolService.agregarRol(this.rol, this.permisosRol).subscribe({
      next: (data) => {
        alert('Se creo un nuevo rol.')
      },
      error:(error)=>{
        console.error(error);
      }
    })
    // Resetea el formulario
    this.rol = { nombre: '', };
    this.permisos.forEach(p => p.seleccionado = false);
  }

  editarRol(role: Rol) {
    this.cargarPermisos();
    this.rol = { ...role };
    this.isEdit = true;
    this.listarPermisosRol(role.id!);
    //this.cargarPermisos();
    
  }

  eliminarRol(role: Rol) {
    this.roles = this.roles.filter(r => r !== role);
    this.rolService.deleteRol(role.id!).subscribe({
      next: (data) => {
        console.log(data);
        alert('El rol has sido eliminado')
        this.ngOnInit();
      },
      error: (error)=>{
        console.error(error);
      }
    })
  }

  cargarRoles(){
    this.rolService.getRoles().subscribe({
      next: (roles) => {
        this.roles = roles as Rol[];
        
      },
      error: (error) => {
        console.error(error);
      }
    })
    
  }

  cargarPermisos(){
    this.rolService.getPermisos().subscribe({
      next: (roles) => {
        this.permisos = roles as Permiso[];
      },
      error: (error) => {
        console.error(error);
      }
    })
    
  }


  listarPermisosRol(id: number){
    this.rolService.listarPermisosRol(id).subscribe({
      next: (data) => {
        console.log('Permisos del rol:', data);
        this.permisosRol=data as Permiso[]
        this.permisos.forEach(element => {
          this.permisosRol.forEach(element1 => {
            console.log(element, element1);
            if(element.id===element1.id){   
              console.log(element, element1);
              element.seleccionado=true;
            }
          });
        });
      },
      error: (error)=>{
        console.error(error);
      }

    })
  }


}
