package com.project.Citas.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.project.Citas.models.Citas.citasModel;

public interface citasRepository extends CrudRepository<citasModel,Long>{
    List<citasModel> findByUsuario_Id(Long id);
    List<citasModel> findByUsuario_IdAndFecha(Long id,LocalDate Fecha);
    List<citasModel> findByFecha(LocalDate Fecha);
    List<citasModel> findByFechaAndServicio_Id(LocalDate fecha, Long tipoServicioId);
    List<citasModel> findByFechaAndIdServidor_Id(LocalDate fecha, Long ServidorId);
    List<citasModel> findByFechaAndIdServidor_IdAndEstadoCita_Id(LocalDate fecha, Long ServidorId,Long estadoID);
    List<citasModel> findByIdServidor_Id(Long servidorId);
    List<citasModel> findByEstadoCita_Id(Long id);
    @Query("SELECT c.usuario.id, c.usuario.nombre, COUNT(c.id) AS totalCitas " +
           "FROM citasModel c " +
           "GROUP BY c.usuario.id, c.usuario.nombre " +
           "ORDER BY totalCitas DESC")
    List<Object[]> findUsuariosConMasCitas();
    

}
