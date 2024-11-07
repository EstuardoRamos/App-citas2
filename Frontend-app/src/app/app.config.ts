import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';

import { AuthModule } from './auth/auth.module';

import { routes } from './app.routes';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { MaterialModule } from './material/material.module';
import { ClienteModule } from './cliente/cliente.module';

export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes), 
    AuthModule,
    ClienteModule,
    MaterialModule, 
    provideAnimationsAsync(),
  ],
  
};
