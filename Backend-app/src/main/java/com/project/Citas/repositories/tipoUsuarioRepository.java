package com.project.Citas.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.project.Citas.models.tipoUsuarioModel;

public interface tipoUsuarioRepository extends CrudRepository<tipoUsuarioModel,Long> {
    Optional<tipoUsuarioModel> findByNombre(String correoElectronico);
}
