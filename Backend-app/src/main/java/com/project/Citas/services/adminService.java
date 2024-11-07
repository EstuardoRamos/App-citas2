package com.project.Citas.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.Citas.JsonResponse;
import com.project.Citas.DTO.rolPermisosRequest;
import com.project.Citas.Errors.ModelAlreadyExistException;
import com.project.Citas.Errors.ModelNotFoundException;
import com.project.Citas.models.EmpresaModel;
import com.project.Citas.models.asignacionPermisosModel;
import com.project.Citas.models.asignacionRolModel;
import com.project.Citas.models.permisosModel;
import com.project.Citas.models.rolModel;
import com.project.Citas.models.userModel;
import com.project.Citas.models.Citas.citasModel;
import com.project.Citas.models.Citas.estadoCitaModel;
import com.project.Citas.models.primariasCompuestas.rolPermisoId;
import com.project.Citas.models.primariasCompuestas.rolUsuarioId;
import com.project.Citas.repositories.EmpresaRepository;
import com.project.Citas.repositories.asignacionPermisosRepository;
import com.project.Citas.repositories.asignacionRolRepository;
import com.project.Citas.repositories.citasRepository;
import com.project.Citas.repositories.estadoCitaRepository;
import com.project.Citas.repositories.permisosRepository;
import com.project.Citas.repositories.rolRepository;
import com.project.Citas.repositories.userRepository;

@Service
public class adminService {
    @Autowired
    @Mock
    private EmpresaRepository empresaRepositorie;
    @Autowired
    @Mock
    private rolRepository rolRepositorie;
    @Autowired
    @Mock
    private asignacionPermisosRepository asignacionPermisosRepositories;
    @Autowired
    @Mock
    private asignacionRolRepository asignacionRolRepositorie;
    @Autowired
    @Mock
    private userRepository userRepositorie;
    @Autowired
    @Mock
    private permisosRepository permisosRepositorie;
    @Autowired
    @Mock
    private citasRepository citasRepository;
    @Autowired
    @Mock
    private estadoCitaRepository estadoCitaRepository;


    public Optional<EmpresaModel> getEmpresaById(Long id) {
        return empresaRepositorie.findById(id);
    }

    public List<citasModel> getAllCitas(){
        return (List<citasModel>)citasRepository.findAll();
    }

    public List<citasModel> getAllCitasporServidor(Long id){
        return citasRepository.findByIdServidor_Id(id);
    }

    public List<citasModel> getAllCitasporServidorFecha(Long id,LocalDate fecha){
        return citasRepository.findByFechaAndIdServidor_Id(fecha, id);
    }
    
    public List<citasModel> getAllCitasPorEstado(Long estado){
        return citasRepository.findByEstadoCita_Id(estado);
    }

    public List<userModel> getAyudantes(Long tipo){
        return userRepositorie.findByTipoUsuario_Id(tipo);
    }

    public JsonResponse updateEstadoCita(Long idEstado,Long idCita){
        Optional<citasModel> cita = citasRepository.findById(idCita);
        if(cita.isPresent()){
            Optional<estadoCitaModel> estadoCita  =   estadoCitaRepository.findById(idEstado);
            if(estadoCita.isPresent()){
                cita.get().setEstadoCita(estadoCita.get());
                citasRepository.save(cita.get());
                return new JsonResponse(500, "estado de Cita actualizada correctamente");
            }else
                throw new ModelNotFoundException("El estado de cita no existe");
        }else
            throw new ModelNotFoundException("La cita no existe");
    }

    public ArrayList<estadoCitaModel> getAllEstadosCitas() {
        return (ArrayList<estadoCitaModel>) estadoCitaRepository.findAll();
    }

    public List<citasModel> getCitasporFecha(LocalDate fecha){
        return citasRepository.findByFecha(fecha);
    }

    public ArrayList<permisosModel> getAllPermisos(){
        return (ArrayList<permisosModel>) (permisosRepositorie.findAll());
    }

    

    public List<permisosModel> getAllPermisosByIdRol(Long id){
        List<asignacionPermisosModel> permisos = asignacionPermisosRepositories.findByIdRol_Id(id);
        return permisos.stream()
        .map(asignacionPermisosModel::getIdPermiso)
        .collect(Collectors.toList());
    }

    public ArrayList<rolModel> getAllRoles() {
        return (ArrayList<rolModel>) (rolRepositorie.findAll());
    }

    public JsonResponse deleteRol(Long id){
        rolRepositorie.deleteById(id);
        return new JsonResponse(500, "Rol eliminado correctamente");
    }

    @Transactional
    public rolPermisosRequest updateRol(rolModel rol,ArrayList<permisosModel> permisos){
        asignacionPermisosRepositories.deleteAllByIdRol_Id(rol.getId());
        for (permisosModel permisosModel : permisos) {
            asignacionPermisosModel tmp2 = new asignacionPermisosModel();
            rolPermisoId id = new rolPermisoId(rol.getId(), permisosModel.getId());
            tmp2.setId(id);
            tmp2.setIdPermiso(new permisosModel(permisosModel.getId()));
            tmp2.setIdRol(rol);
            asignacionPermisosRepositories.save(tmp2);
        }
        rolPermisosRequest response = new rolPermisosRequest();
        ArrayList<permisosModel> tmp = (ArrayList<permisosModel>)getAllPermisosByIdRol(rol.getId());
        response.setRol(rolRepositorie.save(rol));
        response.setPermisos(tmp);
        return response;
    }

    public rolPermisosRequest createRol(String nombre,ArrayList<permisosModel> permisos) {
        Optional<rolModel> rOptional = rolRepositorie.findByNombre(nombre);
        if (!rOptional.isPresent()) {
            rolModel nuevo = new rolModel(nombre);
            rolModel cargado = rolRepositorie.save(nuevo);
            for (permisosModel permisosModel : permisos) {
                asignacionPermisosModel tmp2 = new asignacionPermisosModel();
                rolPermisoId id = new rolPermisoId(cargado.getId(), permisosModel.getId());
                tmp2.setId(id);
                tmp2.setIdPermiso(new permisosModel(permisosModel.getId()));
                tmp2.setIdRol(cargado);
                asignacionPermisosRepositories.save(tmp2);
            }
            rolPermisosRequest tmp = new rolPermisosRequest();
            ArrayList<permisosModel> tmp2 = (ArrayList<permisosModel>)getAllPermisosByIdRol(cargado.getId());
            tmp.setRol(cargado);
            tmp.setPermisos(permisos);
            tmp.setPermisos(tmp2);
            return tmp;
        } else {
            throw new ModelAlreadyExistException("Ya existe un rol con el nombre: " + nombre);
        }
    }

    public asignacionRolModel asignarRol(rolUsuarioId rolUsuarioId) {
        Optional<asignacionRolModel> existente = asignacionRolRepositorie.findById(rolUsuarioId);
        if (!existente.isPresent()) {
            asignacionRolModel asignacionRolModel = new asignacionRolModel();
            userModel user = userRepositorie.findById(rolUsuarioId.getIdUsuario())
                    .orElseThrow(() -> new ModelNotFoundException("usuario no encontrado"));
            rolModel rol = rolRepositorie.findById(rolUsuarioId.getIdRol())
                    .orElseThrow(() -> new ModelNotFoundException("Rol no encontrado"));
            asignacionRolModel.setId(rolUsuarioId);
            asignacionRolModel.setIdUsuario(user);
            asignacionRolModel.setIdRol(rol);
            return asignacionRolRepositorie.save(asignacionRolModel);
        } else {
            throw new ModelAlreadyExistException(
                    "El usuario: " + rolUsuarioId.getIdUsuario() + " ya tiene el rol asignado");
        }
    }

    @Transactional
    public JsonResponse updateAsignacionRol(rolUsuarioId rolUsuarioId){
        Optional<asignacionRolModel> existente = asignacionRolRepositorie.findById(rolUsuarioId);
        if (!existente.isPresent()) {
            asignacionRolRepositorie.deleteAllByIdUsuario_Id(rolUsuarioId.getIdUsuario());
            asignarRol(rolUsuarioId);
            return new JsonResponse(500, "Rol acutalizado correctamente");
        } else {
            throw new ModelAlreadyExistException(
                    "El usuario: " + rolUsuarioId.getIdUsuario() + " ya tiene el rol asignado");
        }
    }

    public void asignacionPermisos(ArrayList<rolPermisoId> permisos) {
        for (rolPermisoId tmp : permisos) {
            asignacionPermisosModel tmp2 = new asignacionPermisosModel();
            tmp2.setId(tmp);
            tmp2.setIdPermiso(new permisosModel(tmp.getIdPermiso()));
            tmp2.setIdRol(new rolModel(tmp.getIdRol()));
            asignacionPermisosRepositories.save(tmp2);
        }
    }

    public List<rolModel> findRolsByUserId(Long idUser){
        List<asignacionRolModel> asignaciones =  asignacionRolRepositorie.findByIdUsuario_Id(idUser);
        return asignaciones.stream()
                .map(asignacionRolModel::getIdRol)
                .collect(Collectors.toList());
    }

    public List<permisosModel> findPermisosByUserId(Long id){
        List<rolModel> roles = findRolsByUserId(id);
        ArrayList<Long> ids = new ArrayList<>();
        for (rolModel tmpModel : roles) {
            ids.add(tmpModel.getId());
        } 
        List<asignacionPermisosModel> asignaciones =  asignacionPermisosRepositories.findByIdRol_Id(ids.get(0));
        return asignaciones.stream()
            .map(asignacionPermisosModel::getIdPermiso)
            .collect(Collectors.toList());
    }

}
