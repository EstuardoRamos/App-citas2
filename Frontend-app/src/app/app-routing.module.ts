import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ClienteModule } from './cliente/cliente.module';

const routes: Routes = [
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
