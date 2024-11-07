package com.project.Citas.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.project.Citas.models.EmpresaModel;

public interface EmpresaRepository extends CrudRepository<EmpresaModel,Long>{
    Optional<EmpresaModel>findByNombre(String nombre);
}
