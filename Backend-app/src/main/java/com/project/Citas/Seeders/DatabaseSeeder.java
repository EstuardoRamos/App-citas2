package com.project.Citas.Seeders;
import java.time.LocalTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.project.Citas.models.EmpresaModel;
import com.project.Citas.models.permisosModel;
import com.project.Citas.models.tipoUsuarioModel;
import com.project.Citas.models.Citas.estadoCitaModel;
import com.project.Citas.repositories.EmpresaRepository;
import com.project.Citas.repositories.estadoCitaRepository;
import com.project.Citas.repositories.permisosRepository;
import com.project.Citas.repositories.tipoUsuarioRepository;

@Component
public class DatabaseSeeder implements CommandLineRunner{
     
    @Autowired
    private tipoUsuarioRepository tipoUsuarioRepositorie;
    @Autowired
    private EmpresaRepository empresaRepositorie;
    @Autowired
    private permisosRepository permisosRepositorie;
    @Autowired
    private estadoCitaRepository estadoCitaRepository;

    @Override
    public void run(String... args) throws Exception{
        if(!tipoUsuarioRepositorie.findByNombre("Admin").isPresent()){
            Insertar();
        }
        if(!empresaRepositorie.findByNombre("Empresa1").isPresent()){
            EmpresaInsert();
        }
        if(!permisosRepositorie.findById(1L).isPresent()){
            RolesInsert();
        }
    }

    public void Insertar(){
        tipoUsuarioModel admin = new tipoUsuarioModel();
        tipoUsuarioModel cliente = new tipoUsuarioModel();
        tipoUsuarioModel ayudante = new tipoUsuarioModel();
        estadoCitaModel estado1 = new estadoCitaModel("Agendado");
        estadoCitaModel estado2 = new estadoCitaModel("Cancelada");
        estadoCitaModel estado3 = new estadoCitaModel("Realizada");
        estadoCitaRepository.save(estado1);
        estadoCitaRepository.save(estado2);
        estadoCitaRepository.save(estado3);
        admin.setNombre("Admin");
        cliente.setNombre("Cliente");
        ayudante.setNombre("Ayudante");
        tipoUsuarioRepositorie.save(admin);
        tipoUsuarioRepositorie.save(cliente);
        tipoUsuarioRepositorie.save(ayudante);
        
    }

    public void RolesInsert(){
        permisosModel tmp1 = new permisosModel("Eliminar");
        permisosModel tmp2 = new permisosModel("Editar");
        permisosModel tmp3 = new permisosModel("Crear Usuarios");
        permisosModel tmp4 = new permisosModel("Generar reportes");

        permisosRepositorie.save(tmp1);
        permisosRepositorie.save(tmp2);
        permisosRepositorie.save(tmp3);
        permisosRepositorie.save(tmp4);
    }

    public void EmpresaInsert(){
        LocalTime horaInicio = LocalTime.parse("08:00"); // hora de inicio a las 8:00 AM
        LocalTime horaCierre = LocalTime.parse("18:00"); // hora de cierre a las 6:00 PM
        // Crea el objeto EmpresaModel con valores válidos
        EmpresaModel empresa = new EmpresaModel();
        empresa.setNombre("Empresa1");
        empresa.setDireccion("quetzaltenango");
        empresa.setHoraInicio(horaInicio);
        empresa.setHoraCierre(horaCierre);
        empresa.setUrlImagen("asdasdf");
        empresaRepositorie.save(empresa);
    }
}
