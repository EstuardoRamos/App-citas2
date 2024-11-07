package com.project.Citas.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.Citas.models.asignacionRolModel;
import com.project.Citas.models.primariasCompuestas.rolUsuarioId;

public interface asignacionRolRepository extends CrudRepository<asignacionRolModel,rolUsuarioId>{
    List<asignacionRolModel> findByIdUsuario_Id(Long idUsuario); 
    void deleteAllByIdUsuario_Id(Long idUsuario);
}
