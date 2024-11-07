import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';
import { User } from '../../interfaces/user.interface';
import { AuthService } from '../services/auth.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerForm: FormGroup;
  usuario!: User;
  verificacion: boolean = false;
  codigo:string;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
    
  ) {
    this.registerForm = this.fb.group(
      {
        nombre: ['', Validators.required],
        correoElectronico: ['', [Validators.required, Validators.email]],
        contrasenia: ['', Validators.required],
        confirmarContrasenia: ['', Validators.required],
        tipoUsuario: {
          id: 2,
          nombre: 'cliente',
        },
        cui: ['', Validators.required],
        telefono: ['', Validators.required],
        nit: [''], // Campo opcional
        fechaNacimiento: ['', Validators.required],
        codigo:['']
      },
      { validators: this.passwordsMatchValidator } // Asignamos la validación personalizada
    );
  }

  // Validador personalizado para verificar si las contraseñas coinciden
  passwordsMatchValidator: ValidatorFn = (formGroup: AbstractControl): ValidationErrors | null => {
    const password = formGroup.get('contrasenia')?.value;
    const confirmPassword = formGroup.get('confirmarContrasenia')?.value;

    // Si las contraseñas no coinciden, devolvemos el error personalizado
    return password && confirmPassword && password !== confirmPassword
      ? { contraseniasNoCoinciden: true }
      : null;
  };

  onSubmit() {
    if (this.registerForm.valid) {
      const user = this.registerForm.value;
      console.log('Datos de usuario registrados:', user);

      this.usuario = { ...user, tipoUsuario: { id: 2, nombre: 'cliente' } };

      this.authService.registro(this.usuario).subscribe({
        next: (response: object) => {
          this.verificacion=true;
          /*Swal.fire({
            icon: 'success',
            title: 'OK',
            text: 'Usuario creado correctamente',
          });*/
          //
        },
        error: (err) => {
          console.error(err);
          alert('Error: ' + err.error.mensaje);
        },
      });
    } else {
      // Mostramos errores si el formulario no es válido
      this.registerForm.markAllAsTouched();
    }
  }

  verificarCodigo(){
    console.log(this.usuario,this.codigo);
    
    this.authService.verificacionCorreo(this.usuario,this.codigo).subscribe({
      next: (response: object) => {
        console.log(response);
        alert('Codigo correcto: Usuario registrado')
        this.router.navigate(['/']);
      },
      error: (err) => {
        console.error(err);
        alert('Error: ' + err.error.mensaje);
      }
    })
  }
}
