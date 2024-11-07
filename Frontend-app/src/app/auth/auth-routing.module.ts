import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'login',
        component: LoginComponent,
        //canActivate: [loginGuard],
      },
      {
        path: 'register',
        component: RegisterComponent,
        //canActivate: [loginGuard],
      },
      {
        path: '**',
        component: LoginComponent,
        //canActivate: [loginGuard]
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthRoutingModule { }
