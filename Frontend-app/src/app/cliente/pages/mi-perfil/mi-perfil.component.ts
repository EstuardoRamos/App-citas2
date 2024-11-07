import { Component } from '@angular/core';
import { AuthService } from '../../../auth/services/auth.service';
import { UserService } from '../../../services/user.service';
import { User } from '../../../interfaces/user.interface';

@Component({
  selector: 'app-mi-perfil',
  templateUrl: './mi-perfil.component.html',
  styleUrl: './mi-perfil.component.css'
})
export class MiPerfilComponent {
  
  constructor(
    private authService:AuthService,
    private userService: UserService
  ){

  }

  user:User = {
    id: 0,
    nombre: "",
    tipoUsuario: {
      id: 2,
      nombre: "Cliente"
    },
    correoElectronico: "",
    telefono: 0,
    nit: 0,
    cui: 0,
    a2f: false,
    fechaNacimiento: new Date()
  };

  
  obtenerUserLog(){
    this.authService.getUser().subscribe({
      next: (data) => {
        this.user= data
        this.originalData = { ...this.user };
      },
      error: (error) => {
        console.error(error);
      }
    })
  }

  // Variable para alternar entre modo vista y modo edición
  editMode: boolean = false;
  originalData: any;

  // Guardar los cambios realizados en el perfil
  guardarCambios() {
    this.editMode = false;
    this.userService.updateUser(this.user).subscribe({
      next: (data) => {
        console.log(data);
        alert('Perfil actualizado exitosamente');
      },
      error: (error) => {

      }
    })
    
    // Aquí puedes llamar al servicio para actualizar el perfil en el backend
  }

  // Cancelar edición y restaurar datos originales
  cancelarEdicion() {
    this.editMode = false;
    this.user = { ...this.originalData };
  }

  ngOnInit() {
    // Guardar datos originales para restaurar si se cancela la edición
    this.obtenerUserLog()
    //this.originalData = { ...this.user };
  }

}
