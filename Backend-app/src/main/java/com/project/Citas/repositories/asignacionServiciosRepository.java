package com.project.Citas.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.Citas.models.Citas.asignacionServicios;
import com.project.Citas.models.primariasCompuestas.servidorServicioId;

public interface asignacionServiciosRepository extends CrudRepository<asignacionServicios,servidorServicioId>{
    List<asignacionServicios> findByIdServidor_Id(Long id);
    List<asignacionServicios> findByIdServicio_Id(Long id);   
    void deleteAllByIdServidor_Id(Long id);
}
