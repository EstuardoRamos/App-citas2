import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginForm: FormGroup;

  constructor(private fb: FormBuilder,private router: Router, private authService:AuthService) {
    // Crear el formulario reactivo
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required]]
    });
  }

  // Función que maneja el evento de envío del formulario
  onLogin() {
    if (this.loginForm.valid) {
      const { email, password } = this.loginForm.value;
      // Aquí se realizaría el proceso de autenticación
      console.log('Email:', email, 'Password:', password);
      this.authService.login(email, password).subscribe({
        next: (response: any) => {
  
          console.log(response.token);
          if (response.token == undefined) {
            console.log('holas que hace');
            //this.verificacion = true;
            return;
          }
  
          this.inicioSesion(response.token);
        },
        error: (error) => {
  
          Swal.fire({
            icon: 'error',
            title: 'Error al iniciar sesión',
            text: error.error.mensaje || 'Ha ocurrido un error inesperado 2.',
          });
        }
  
      })
      // Redireccionar al dashboard o mostrar errores según sea el caso
    }
  }


  inicioSesion(token: string) {

    this.authService.saveToken(token);

    const message = `Bienvenido, ${this.authService.getNombre()}!`;

    Swal.fire({
      icon: 'success',
      title: 'Inicio de sesión exitoso ',
      text: message,
    });
    this.getUsuario();
    const idTipoUsuario = this.authService.getIdTipoUsuario();
    console.log(idTipoUsuario);
    if (idTipoUsuario !== null) {
      const id = idTipoUsuario;

      if (id === 1) {
        this.router.navigate(['/admin']);
      } else if (id === 2) {
        this.router.navigate(['/cliente']);
      } else {
        this.router.navigate(['/admin']);
      }

    }

  }
  getUsuario(){
    this.authService.getUser().subscribe({
      next: (response) => {
        console.log('Obteniendo Usuario');
        
        console.log(response);
      },
      error: (error) => {
        console.error(error);
      }
    })
  }

  
}
