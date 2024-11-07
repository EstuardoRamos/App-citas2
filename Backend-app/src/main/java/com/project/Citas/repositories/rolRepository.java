package com.project.Citas.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.project.Citas.models.rolModel;

public interface rolRepository extends CrudRepository<rolModel,Long>{
    Optional<rolModel>findByNombre(String nombre);
}
