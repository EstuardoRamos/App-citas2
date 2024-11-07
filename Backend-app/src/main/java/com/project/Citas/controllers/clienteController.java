package com.project.Citas.controllers;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.Citas.JsonResponse;
import com.project.Citas.DTO.citaDTO;
import com.project.Citas.DTO.horarioDisponible;
import com.project.Citas.models.Citas.ServicioModel;
import com.project.Citas.models.Citas.ServidorModel;
import com.project.Citas.models.Citas.citasModel;
import com.project.Citas.services.clienteService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("cliente")
public class clienteController {
    
    @Autowired
    private clienteService clienteService;

    @GetMapping("/citas/{id}")
    @Operation(summary = "Obtener citas por id")
    public ArrayList<citasModel> getCitas(@PathVariable("id") Long id){
        return clienteService.getCitasById(id);
    }

    @GetMapping("/citas/{id}/{fecha}")
    @Operation(summary = "Obtener citas por id y fecha")
    public ArrayList<citasModel> getCitasByIdAndFecha(@PathVariable("id") Long id,@PathVariable("fecha") LocalDate fecha){
       return  clienteService.getCitasByIdAndFecha(id, fecha);
    }

    @GetMapping("/citas/servidores/{id}") 
    @Operation(summary = "Obtener servidores que prestan un servicio")
    public ArrayList<ServidorModel> getServidorPorServicio(@PathVariable("id") Long variable){
        return clienteService.getServidorPorServicio(variable);
    }

    @PostMapping("/cita")
    @Operation(summary = "Guardar una nueva cita")
    public JsonResponse saveCita(@RequestBody citasModel newCitasModel){
        return clienteService.saveCita(newCitasModel);
    }

    @PostMapping("/cita/consultarHorario")
    @Operation(summary = "Consultar horarios disponibles")
    public ArrayList<horarioDisponible> getHorariosDisponibles(@RequestBody citaDTO cita){
        return clienteService.getHorariosDisponiblesPorServicio(cita);
    }

    @GetMapping("/servicios")
    @Operation(summary = "Obtener todos los servicios")
    public ArrayList<ServicioModel> getServicios(){
        return clienteService.getServicios();
    }    
}
