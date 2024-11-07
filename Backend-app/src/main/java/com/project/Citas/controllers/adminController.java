package com.project.Citas.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.Citas.JsonResponse;
import com.project.Citas.DTO.rolPermisosRequest;
import com.project.Citas.DTO.updateCitaDTO;
import com.project.Citas.models.asignacionRolModel;
import com.project.Citas.models.permisosModel;
import com.project.Citas.models.rolModel;
import com.project.Citas.models.userModel;
import com.project.Citas.models.Citas.citasModel;
import com.project.Citas.models.Citas.estadoCitaModel;
import com.project.Citas.models.primariasCompuestas.rolPermisoId;
import com.project.Citas.models.primariasCompuestas.rolUsuarioId;
import com.project.Citas.services.adminService;

import io.swagger.v3.oas.annotations.Operation;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/admin")
public class adminController {

    @Autowired
    private adminService adminService;

    @Operation(summary = "Asignar permiso a un Rol")
    @PostMapping("/permisos")
    public void asignarPermisos(@RequestBody ArrayList<rolPermisoId> permisos){
        adminService.asignacionPermisos(permisos);
    }
    @Operation(summary = "Obtener todos los permisos")
    @GetMapping("/permisos")
    public ArrayList<permisosModel> getAllPermisos(){
        return adminService.getAllPermisos();
    }

    @Operation(summary = "Asignar rol a un Usuario")
    @PostMapping("/permisos/asignar")
    public asignacionRolModel asignarRol(@RequestBody rolUsuarioId asignacion){
        return adminService.asignarRol(asignacion);
    }

    @Operation(summary = "Actualizar rol de un usuario")
    @PutMapping("/permisos/asignar")
    public JsonResponse actualizarRol(@RequestBody rolUsuarioId asignacion){
        return adminService.updateAsignacionRol(asignacion);
    }

    @Operation(summary = "Crear un rol")
    @PostMapping("/crearRol")
    public rolPermisosRequest createRol(@RequestBody rolPermisosRequest tmp){
        return adminService.createRol(tmp.getRol().getNombre(),tmp.getPermisos());
    }

    @Operation(summary = "Actualizar un rol")
    @PutMapping("/rol")
    public rolPermisosRequest updateRol(@RequestBody rolPermisosRequest permisos){
        return adminService.updateRol(permisos.getRol(),permisos.getPermisos());
    }

    @Operation(summary = "Obtener permisos por el id del rol")
    @GetMapping("/rol/permisos/{id}")
    public List<permisosModel> getAllPermisosByRolId(@PathVariable Long id){
        return adminService.getAllPermisosByIdRol(id);
    }

    @Operation(summary = "Obtener usuarios por el tipo de usuario")
    @GetMapping("/usuario/{Tipoid}")
    public List<userModel> getUsuariosporTipoUsuario(@PathVariable("Tipoid") Long id){
        return adminService.getAyudantes(id);
    }


    @DeleteMapping("/rol/{id}")
    public JsonResponse deleteRol(@PathVariable Long id){
        return adminService.deleteRol(id);
    }

    @Operation(summary = "Actualizar el estado de una cita")
    @PutMapping("/citas/estado")
    public JsonResponse updateEstadoCita(@RequestBody updateCitaDTO uCitaDTO){
        return adminService.updateEstadoCita(uCitaDTO.getIdEstado(), uCitaDTO.getIdCita());
    }


    @Operation(summary = "Obtener todos los roles")
    @GetMapping("/rol")
    public ArrayList<rolModel> getallRoles(){
        return adminService.getAllRoles();
    }

    @Operation(summary = "Obtener todos los estados de citas")
    @GetMapping("/citas/estado")
    public ArrayList<estadoCitaModel> getallEstadosCitas(){
        return adminService.getAllEstadosCitas();
    }


    @Operation(summary = "Obtener todos los roles por el id del Usuario")
    @GetMapping("/rol/{id}")
    public List<rolModel> findRolesByUserId(@PathVariable("id") Long id){
        return adminService.findRolsByUserId(id);
    }

    @Operation(summary = "Obtener todos los permisos por el id del Usuario")
    @GetMapping("/permisos/{id}")
    public List<permisosModel> findPermisosByUserId(@PathVariable("id") Long id){
        return adminService.findPermisosByUserId(id);
    }

    @GetMapping("/citas/{fecha}")
    @Operation(summary = "Obtener citas por fecha")
    public List<citasModel> getCitasporFecha(@PathVariable("fecha") LocalDate fecha){
        return adminService.getCitasporFecha(fecha);
    }
    
    @GetMapping("/citas")
    @Operation(summary = "Obtener todas las citas")
    public List<citasModel> getAllCitas(){
        return adminService.getAllCitas();
    }


    @GetMapping("/citas/servidor/{id}")
    @Operation(summary = "Obtener citas por servidor")
    public List<citasModel> getAllCitasporServidor(@PathVariable("id") Long id){
        return adminService.getAllCitasporServidor(id);
    }

    @GetMapping("/citas/servidor/{id}/{fecha}")
    @Operation(summary = "Obtener citas por servidor y fecha")
    public List<citasModel> getAllCitasporServidorFeha(@PathVariable("id") Long id,@PathVariable("fecha") LocalDate feha){
        return adminService.getAllCitasporServidorFecha(id, feha);
    }

    @GetMapping("/citas/estado/{id}")
    @Operation(summary = "Obtener citas por estado")
    public List<citasModel> getAllCitasporEstado(@PathVariable("id") Long id){
        return adminService.getAllCitasPorEstado(id);
    }

}
