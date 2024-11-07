import { Component, OnInit } from '@angular/core';
import { EmpresaService } from '../../../services/empresa.service';
import { Empresa, EmpresaModel } from '../../../interfaces/empresa.interface';
import { TipoEmpresa } from '../../../interfaces/empresa.interface'; // Interfaz para tipos de empresa
import { error } from 'console';

@Component({
  selector: 'app-informacion-empresa',
  templateUrl: './informacion-empresa.component.html',
  styleUrl: './informacion-empresa.component.css'
})
export class InformacionEmpresaComponent implements OnInit {
  isEditMode: boolean = false; // Cambiar a true si se está editando
  empresa: Empresa = {
    nombre: '',
    urlImagen: '',
    direccion: '',
    horaInicio: '',
    horaCierre: '',
    tipoEmpresa: { id: undefined } // Añadimos tipoEmpresa
  };

  formData = new FormData();

  //tiposEmpresa: TipoEmpresa[] = []; // Array para los tipos de empresa
  empresaModel: EmpresaModel ={
    empresaModel: undefined,
    imagen: undefined
  };
  constructor(private empresaService: EmpresaService) { }

  ngOnInit(): void {
    // Cargar datos existentes si es edición
    this.cargarDatosEmpresa();
    if (this.isEditMode) {
      this.cargarDatosEmpresa();
    }
    //this.cargarTiposEmpresa(); // Cargar tipos de empresa
  }

  cargarDatosEmpresa() {
    // Lógica para cargar los datos de la empresa
    this.empresaService.getEmpresa().subscribe({
      next: (data) => {
        this.empresa = data as Empresa;
        console.log(this.empresa);
      },
      error: (error) => {
        console.error(error);
      }
    });
    return this.empresa;
  }

  cargarTiposEmpresa() {
    // Lógica para cargar los tipos de empresa
    this.empresaService.getTiposEmpresa().subscribe({
      next: (data) => {
        //this.tiposEmpresa = data as TipoEmpresa[];
      },
      error: (error) => {
        console.error('Error al cargar tipos de empresa', error);
      }
    });
  }

  onFileSelected(event: any) {
    const file = event.target.files[0];
    // Manejar la carga de la imagen
    if (file) {
      const reader = new FileReader();
      reader.onload = () => {
        this.empresa.urlImagen = reader.result as string;
      };
      console.log(file)
      
      this.formData.append('imagen', file);
      
      //this.empresaModel.imagen = formData;
      reader.readAsDataURL(file);
    }
  }

  convertirHoraFormato24(campo: 'horaInicio' | 'horaCierre') {
    // Convertir formato de hora a "HH:MM:SS"
    const hora = this.empresa[campo];
    if (hora) {
      this.empresa[campo] = `${hora}:00`; // Agregamos segundos
    }
  }

  onSubmit() {
    
    if (this.isEditMode) {
      this.empresaModel.empresaModel=this.empresa;
      this.formData.append('empresaModel', JSON.stringify(this.empresaModel));
      console.log('Guardando cambios:', this.formData);
      

      //this.empresa.urlImagen="hola que hace"
      // Aquí va la lógica para guardar los cambios (actualizar)
      this.empresaService.updateEmpresa(this.formData).subscribe({
        next: (data) => {
          console.log('Cambios guardados:', data);
        },
        error: (error) => {
          console.error(error);
        }
      });
    } else {
      
      this.empresaModel.empresaModel=this.empresa;
      this.formData.append('empresaModel', JSON.stringify(this.empresaModel));
      console.log('Guardando nueva configuración:', this.formData);
      this.empresaService.guardarEmpresa(this.formData).subscribe({
        next: (data) => {
          console.log('Nueva configuración guardada:', data);
        },
        error: (error) => {
          console.error(error);
        }
      });
    }
  }

  
}