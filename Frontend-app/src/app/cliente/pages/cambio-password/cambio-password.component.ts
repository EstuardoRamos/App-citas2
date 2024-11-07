import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from '../../../services/user.service';
import { AuthService } from '../../../auth/services/auth.service';
import { User } from '../../../interfaces/user.interface';

@Component({
  selector: 'app-cambio-password',
  templateUrl: './cambio-password.component.html',
  styleUrls: ['./cambio-password.component.css']
})
export class CambioPasswordComponent {
  passwordForm: FormGroup;
  idUser!: number;

  usuario:User = {
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

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private fb: FormBuilder
  ) {
    this.passwordForm = this.fb.group({
      currentPassword: ['', Validators.required],
      newPassword: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required]
    }, { validator: this.passwordsMatchValidator });
  }

  ngOnInit() {
    this.obtenerUserLog()
  }

  // Validador personalizado para comprobar si las contraseñas coinciden
  passwordsMatchValidator(form: FormGroup) {
    const newPassword = form.get('newPassword')?.value;
    const confirmPassword = form.get('confirmPassword')?.value;
    return newPassword === confirmPassword ? null : { mismatch: true };
  }

  onSubmit() {
    if (this.passwordForm.valid) {
      // Lógica para enviar el formulario
      console.log("Formulario de cambio de contraseña enviado:", this.passwordForm.value);

      // Llamada a cambioPassword con los valores de los campos
      this.cambioPassword(
        this.passwordForm.get('currentPassword')?.value,
        this.passwordForm.get('confirmPassword')?.value,
        this.usuario.id
      );

      //alert("Contraseña cambiada con éxito");
    }
  }

  cambioPassword(actual: string, nueva: string, id: number){
    this.userService.cambioPassword(actual, nueva, id).subscribe({
      next: (data) => {
        console.log(data);
        alert('Contraseña cambiada exitosamente');
      },
      error: (error) => {
        console.error(error);
        alert(error.error.mensaje);
      }
    });
  }

  obtenerUserLog(){
    this.authService.getUser().subscribe({
      next: (data) => {
        //console.log(data);
        this.usuario = data;
        this.idUser= this.usuario.id;
      },
      error: (error) => {
        console.error(error);
      }
    });
  }
}
