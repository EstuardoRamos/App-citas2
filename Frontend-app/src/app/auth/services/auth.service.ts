import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { jwtDecode } from 'jwt-decode';
import { Router } from '@angular/router';
import { environment } from '../../../environments/environment.development';
import { User } from '../../interfaces/user.interface';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private baseUrl = environment.baseUrlEnv;

  constructor(
    private http: HttpClient,
    private cookieService: CookieService,
    private router: Router
  ) {}

  private cookieName = 'token';

  login(correoElectronico: string, contrasenia: string) {
    const body = { correoElectronico, contrasenia };
    console.log(body);
    return this.http.post(`${this.baseUrl}/user/login`, body);
  }

  registro(usuario: User) {
    console.log(usuario);
    return this.http.post(`${this.baseUrl}/user`, usuario);
  }

  verificacionCorreo(usuarioNuevo:User, codigo:string){
    console.log({codigo,usuarioNuevo});
    
    return this.http.post(`${this.baseUrl}/user/validarCorreo`, {codigo,usuarioNuevo});
  }

  saveToken(token: string): void {
    this.cookieService.set(this.cookieName, token, undefined, '/');
  }

  //obtengo todo el token jwt
  public getToken(): string | null {
    return this.cookieService.get(this.cookieName);
  }

  getNombre(): boolean | null {
    const decodedToken = this.decodeToken();
    console.log(this.decodeToken());
    
    if (decodedToken && decodedToken.sub) {
      return decodedToken.sub;
    }
    return null;
  }

  getIdUsuario(): number | null {
    const decodedToken = this.decodeToken();
    if (decodedToken && decodedToken.id) {
      return decodedToken.id;
    }
    return null;
  }

  public getIdTipoUsuario(): number | null {
    const decodedToken = this.decodeToken();
    if (decodedToken && decodedToken.tipoUsuario) {
      console.log(decodedToken.tipoUsuario, '---------');

      return decodedToken.tipoUsuario;
    }
    return null;
  }

  private decodeToken(): any {
    const token = this.getToken();
    if (token) {
      console.log("Si token")
      return jwtDecode(token);
    }
    return null;
  }

  getUser(){
    return this.http.get(`${this.baseUrl}/user/${this.getIdUsuario()}`);
  }

}
