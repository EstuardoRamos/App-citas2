import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment.development';
import { User } from '../interfaces/user.interface';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = environment.baseUrlEnv

  constructor(
    private http: HttpClient,
  ) { }

  getUser(id: number){
    return this.http.get(`${this.baseUrl}/user/${id}`);
  }

  updateUser(user: User){
    return this.http.put(`${this.baseUrl}/user`, user);
  }
  cambioPassword(actual: string, nueva:string, id: number){
    console.log({actual, nueva, id});
    
    return this.http.put(`${this.baseUrl}/user/password`, {actual, nueva, id});
  }
}
