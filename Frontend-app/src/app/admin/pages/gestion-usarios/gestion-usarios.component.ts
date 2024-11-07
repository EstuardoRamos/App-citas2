import { Component } from '@angular/core';
import { User } from '../../../interfaces/user.interface';
import { Rol } from '../../../interfaces/roles.interface';
import { RolesService } from '../../services/roles.service';
import { UserService } from '../../../services/user.service';
import { AuthService } from '../../../auth/services/auth.service';
import { error } from 'console';

import {
  FormBuilder,
  FormGroup,
  Validators,
  AbstractControl,
  ValidationErrors,
  ValidatorFn,
} from '@angular/forms';

import Swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
  selector: 'app-gestion-usarios',
  templateUrl: './gestion-usarios.component.html',
  styleUrl: './gestion-usarios.component.css',
})
export class GestionUsariosComponent {
  usuarios: User[];
  displayedColumns: string[] = [
    'nombre',
    'correoElectronico',
    'rol',
    'acciones',
  ];
  userForm: FormGroup;
  usuario: User = this.inicializarUsuario();
  selectedService: string | null = null;
  verificacion:boolean =false;
  codigo:string;
  tipo1 = {
    id: 2,
    nombre: 'Cliente',
  };
  tipo2 = {
    id: 3,
    nombre: 'Moderador',
  };
  //tipos: any[];
  //userForm: FormGroup;
  constructor(
    private rolService: RolesService,
    private userService: UserService,
    private authService: AuthService,
    private fb: FormBuilder
  ) {
    this.listarRoles();
    this.listarTipoUser();
    this.listarUsuariosModeradores();
    this.userForm = this.fb.group({
      nombre: ['', Validators.required],
      correoElectronico: ['', [Validators.required, Validators.email]],
      contrasenia: ['', Validators.required],
      nit: [''],
      cui: ['', Validators.required],
      telefono: ['', Validators.required],
      fechaNacimiento: ['', Validators.required],
    });
  }

  confirmarCorreo(){
    console.log(this.usuario,this.codigo);
    
    this.authService.verificacionCorreo(this.usuario,this.codigo).subscribe({
      next: (response: object) => {
        console.log(response);
        this.usuario = response as User;
        alert('Usuario registrado');
        this.asignarRol(this.usuario.id, this.usuario.rol.id);
        alert('Codigo correcto: Usuario registrado')
        
      },
      error: (err) => {
        console.error(err);
        alert('Error: ' + err.error.mensaje);
      }
    })
  }
  inicializarUsuario(): User {
    return {
      id: 0,
      nombre: '',
      correoElectronico: '',
      contrasenia: '',
      nit: 0,
      cui: 0,
      telefono: 0,
      fechaNacimiento: undefined,
      rol: { id: 0, nombre: '' },
    };
  }

  tipos = [
    { id: 3, nombre: 'Moderador/Ayudante' },
    { id: 2, nombre: 'Cliente' },
  ];
  esModerador: boolean = true;
  // Lista de roles disponibles
  roles: Rol[];

  // Definición del objeto usuario inicial

  confirmarContrasena: string = '';

  onServiceChange() {
    this.listarUsuarios(parseInt(this.selectedService));
  }

  passwordsMatchValidator: ValidatorFn = (
    formGroup: AbstractControl
  ): ValidationErrors | null => {
    const password = formGroup.get('contrasenia')?.value;
    const confirmPassword = formGroup.get('confirmarContrasenia')?.value;

    // Si las contraseñas no coinciden, devolvemos el error personalizado
    return password && confirmPassword && password !== confirmPassword
      ? { contraseniasNoCoinciden: true }
      : null;
  };

  // Método para guardar un nuevo usuario o editar un existente
  guardarUsuario() {
    // if (this.usuario.contrasenia !== this.confirmarContrasena) {
    //   alert('Las contraseñas no coinciden');
    //   return;
    // }

    if (this.usuario.id) {
      // Editar usuario existente
      this.updateUser(this.usuario);
      const index = this.usuarios.findIndex((u) => u.id === this.usuario.id);
      this.usuarios[index] = { ...this.usuario };
    } else {
      // Crear nuevo usuario
      this.usuario.tipoUsuario = this.tipo2;
      this.guardarUser(this.usuario);

      this.usuario.id = this.usuarios.length + 1; // Genera un nuevo ID único
      this.usuarios.push({ ...this.usuario });
    }
    this.resetForm();
    this.cargarUsers();
    //this.resetForm();
  }

  // Método para editar un usuario existente
  editarUsuario(usuario: User) {
    //this.usuario.rol=this.usuario.rol
    //this.obtnerRolUser(usuario);
    this.usuario = { ...usuario }; // Clonar el usuario para editarlo
  }

  // Método para eliminar un usuario
  eliminarUsuario(usuario: User) {
    this.usuarios = this.usuarios.filter((u) => u.id !== usuario.id);
  }

  // Método para limpiar el formulario después de guardar
  resetForm() {
    this.usuario = {
      id: undefined,
      nombre: '',
      correoElectronico: '',
      contrasenia: '',
      rol: undefined,
    };
    this.confirmarContrasena = '';
  }

  listarRoles() {
    this.rolService.getRoles().subscribe({
      next: (roles) => {
        this.roles = roles as Rol[];
      },
      error: (error) => {
        console.error(error);
      },
    });
  }
  listarTipoUser() {
    this.tipos = this.tipos;
  }

  listarUsuarios(id: number) {
    if (id === 2) {
      this.esModerador = false;
      this.usuario.tipoUsuario = this.tipo1;
    } else {
      this.esModerador = true;
      this.usuario.tipoUsuario = this.tipo2;
    }
    this.rolService.listarUsuarios(id).subscribe({
      next: (usuarios) => {
        this.usuarios = usuarios as User[];
      },
      error: (error) => {
        console.error(error);
      },
    });
  }

  listarUsuariosModeradores() {
    this.rolService.listarUsuarioModeradores().subscribe({
      next: (usuarios) => {
        this.usuarios = usuarios as User[];
        this.obtnerRolUser();
      },
      error: (error) => {
        console.error(error);
      },
    });
  }

  listarUsuariosClientes() {
    this.rolService.listarUsuarioClientes().subscribe({
      next: (usuarios) => {
        this.usuarios = usuarios as User[];
      },
      error: (error) => {
        console.error(error);
      },
    });
  }

  guardarUser(user: User) {
    this.authService.registro(user).subscribe({
      next: (data) => {
        this.verificacion=true;
        this.usuario = user;
       //alert('Usuario registrado');
        //this.asignarRol(this.usuario.id, user.rol.id);
        //this.user = user as User
      },
      error: (error) => {
        console.error(error);
        alert('Error al registrar user: ' + error.error.mensaje);
      },
    });
  }

  updateUser(user: User) {
    this.userService.updateUser(user).subscribe({
      next: (user) => {
        alert('Usuario actualizado');
        //this.user = user as User
      },
      error: (error) => {
        console.error(error);
        alert('Error al registrar user: ' + error.error.mensaje);
      },
    });
  }

  cargarUsers() {
    if (this.esModerador) {
      this.listarUsuariosModeradores;
    } else {
      this.listarUsuariosClientes;
    }
  }
  asignarRol(idUsuario: number, idRol: number) {
    this.rolService.asignarRol(idUsuario, idRol).subscribe({
      next: (data) => {
        alert('Rol asignado');
      },
      error: (error) => {
        console.error(error);
        alert('Error al asignar rol');
      },
    });
  }

  obtnerRolUser() {
    this.usuarios.forEach((element) => {
      this.rolService.gerRolUsuario(element.id).subscribe({
        next: (data) => {
          element.rol = data as Rol;
          console.log(element.rol );
          
        },
        error: (error) => {},
      });
    });
  }
}
