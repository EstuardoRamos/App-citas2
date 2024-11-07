package com.project.Citas.repositories;

import java.time.DayOfWeek;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.project.Citas.models.horarioModel;

public interface horarioRepository extends CrudRepository<horarioModel,Long>{
    Optional<horarioModel> findByDiaAndServidor_Id(DayOfWeek dia,Long servidorId);  
}
