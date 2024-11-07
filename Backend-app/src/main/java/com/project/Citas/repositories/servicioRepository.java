package com.project.Citas.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.project.Citas.models.Citas.ServicioModel;

public interface servicioRepository extends CrudRepository<ServicioModel,Long>{
    Optional<ServicioModel> findByNombre(String nombre);
}
