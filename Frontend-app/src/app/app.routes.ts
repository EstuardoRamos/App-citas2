import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: 'auth',
        loadChildren: () => import('./auth/auth.module').then((m) => m.AuthModule),
      },
      {
        path: 'cliente',
        loadChildren: () => import('./cliente/cliente.module').then((m) => m.ClienteModule),
      },
      {
        path: 'admin',
        loadChildren: () => import('./admin/admin.module').then((m) => m.AdminModule),
      },
      {
        path: '**',
        loadChildren: () => import('./auth/auth.module').then((m) => m.AuthModule),
      },
];
