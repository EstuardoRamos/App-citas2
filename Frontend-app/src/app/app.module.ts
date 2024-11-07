import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AppRoutingModule } from './app-routing.module';
import { MaterialModule } from './material/material.module';
import { AuthModule } from './auth/auth.module';
import { AppComponent } from './app.component';
import { RouterModule } from '@angular/router';


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    //AppRoutingModule,
    MaterialModule,
    AuthModule,
    RouterModule
  ],
  providers: [],
  
})
export class AppModule { }
