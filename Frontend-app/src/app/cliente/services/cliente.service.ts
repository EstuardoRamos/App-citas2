import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.development';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../auth/services/auth.service';
import { User } from '../../interfaces/user.interface';

@Injectable({
  providedIn: 'root'
})
export class ClienteService {

  private baseUrl = environment.baseUrlEnv;
  user!:User;

  constructor(
    private http: HttpClient,
    private authService: AuthService
  ) {}

  getUserLog() {
    this.authService.getUser().subscribe({
      next: (response) => {
        this.user=response as User 
        return this.user
      },
      error: (error) => {
        console.error(error);
        return null
      }
    })
    
  }
}
