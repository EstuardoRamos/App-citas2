import { Component } from '@angular/core';
import { User } from '../../../interfaces/user.interface';
import { AuthService } from '../../../auth/services/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'] // Cambiar styleUrl a styleUrls
})
export class HomeComponent {
  user:User = {
    id: 0,
    nombre: "",
    tipoUsuario: {
      id: 2,
      nombre: "Cliente"
    },
    correoElectronico: " ",
    telefono: 0,
    nit: 0,
    cui: 0,
    a2f: false,
    fechaNacimiento: new Date()
  }; // El uso de '!' indica que la propiedad será inicializada más tarde.

  constructor(private authService: AuthService) {
    
  }

  ngOnInit(){
    this.obtenerUserLog();
    
  }


  obtenerUserLog(){
    this.authService.getUser().subscribe({
      next: (user: User) => {
        this.user = user; // Asignar el usuario obtenido.
      },
      error: (error) => {
        console.error('Error al obtener el usuario:', error); // Mensaje de error más claro.
      }
    });

  }
}
