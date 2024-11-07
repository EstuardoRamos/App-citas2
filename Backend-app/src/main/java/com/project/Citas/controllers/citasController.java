package com.project.Citas.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.project.Citas.JsonResponse;
import com.project.Citas.DTO.ServidorDTO;
import com.project.Citas.models.Citas.ServicioModel;
import com.project.Citas.models.Citas.ServidorModel;
import com.project.Citas.models.Citas.citasModel;
import com.project.Citas.models.Citas.estadoCitaModel;
import com.project.Citas.services.citasService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("citas")
public class citasController {
    @Autowired
    private citasService citasService;

    @Operation(summary = "Crear una nueva cita")
    @PostMapping
    public JsonResponse saveCitas(@RequestBody citasModel citasModel){
        return citasService.saveCitas(citasModel);
    }

    @Operation(summary = "Crear un nuevo servidor")
    @PostMapping("/servidor")
    public JsonResponse saveServidor(@RequestBody ServidorDTO servidorModel){
        try {
            return citasService.saveServidor(servidorModel);
        } catch (Exception e) {
            System.out.println(e);
            return new JsonResponse(404, e.getMessage());
        }
    }


   

    @Operation(summary = "Crear un nuevo servicio")
    @PostMapping("/servicio")
    public JsonResponse saveServicio(@RequestBody ServicioModel setServicioModel){
        return citasService.saveServicio(setServicioModel);
    }

    @Operation(summary = "Obtener todos los servicios")
    @GetMapping("/servicio")
    public List<ServicioModel> getAllServicios(){
        return citasService.getAllServicios();
    }

    @Operation(summary = "Actualizar un servicio")
    @PutMapping("/servicio")
    public JsonResponse updateServicio(@RequestBody ServicioModel newModel){
        return citasService.updateServicio(newModel);
    }

    @Operation(summary = "Obtener los estados de citas")
    @GetMapping("/estados")
    public List<estadoCitaModel> getEstadosCitas(){
        return citasService.getAllEstadosCitas();
    }

    @Operation(summary = "Obtener todos los servidores")
    @GetMapping("/servidor")
    public List<ServidorModel> getAllServidores(){
        return citasService.getAllServidores();
    }

    @Operation(summary = "Actualizar un servidor")
    @PutMapping("/servidor")
    public JsonResponse updateServidor(@RequestBody ServidorDTO servidorModel){
        try {
            return citasService.updateServidor(servidorModel);
        } catch (Exception e) {
            System.out.println(e);
            return new JsonResponse(404, e.getMessage());
        }
    }


    @Operation(summary = "Obtener todas las citas por id del usuario")
    @GetMapping("/{id}")
    public List<citasModel> getAllCitasByIdUser(@PathVariable("id") Long id){
        return citasService.getAllCitasByIdUser(id);
    }


    @Operation(summary = "Obtener todos los servicios que tiene asignado un servidor")
    @GetMapping("/servicios/{id}")
    public List<ServicioModel> getServiciosporServidor(@PathVariable("id") Long id){
        return citasService.getServiciosPorServidor(id);
    }
}
