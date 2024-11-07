package com.project.Citas.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.project.Citas.models.userModel;

@Repository
public interface userRepository  extends CrudRepository<userModel,Long>{
    Optional<userModel> findByCorreoElectronico(String correoElectronico);
    Optional<userModel> findByCui(String Cui);
    Optional<userModel> findByNit(String Nit);
    Optional<userModel> findByTelefono(String Telefono);

    List<userModel> findByTipoUsuario_Id(Long id);
}
