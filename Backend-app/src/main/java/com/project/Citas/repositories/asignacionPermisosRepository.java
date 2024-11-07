package com.project.Citas.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.project.Citas.models.asignacionPermisosModel;
import com.project.Citas.models.primariasCompuestas.rolPermisoId;

public interface asignacionPermisosRepository extends CrudRepository<asignacionPermisosModel,rolPermisoId>{
    List<asignacionPermisosModel> findByIdRol_Id(Long id);
    void deleteAllByIdRol_Id(Long id);
}
