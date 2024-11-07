package com.project.Citas.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.project.Citas.models.Citas.ServidorModel;

public interface servidorRepository extends CrudRepository<ServidorModel,Long>{
    Optional<ServidorModel> findByNombre(String nombre);
}
