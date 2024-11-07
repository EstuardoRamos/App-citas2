package com.project.Citas.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.Citas.DTO.ServidorGanancioDTO;
import com.project.Citas.models.Citas.ServicioModel;
import com.project.Citas.models.Citas.ServidorModel;
import com.project.Citas.models.Citas.citasModel;
import com.project.Citas.services.reporteService;

@RestController
@RequestMapping("/reporte")
public class reporteController {
    
     @Autowired
    private reporteService reporteService;

    @GetMapping("/citas")
    public ArrayList<citasModel> getAllCitas() {
        return reporteService.getAllCitas();
    }

    @GetMapping("/clientes")
    public List<Object[]> usuarioConMasCitas() {
        return reporteService.getUsuarioconMasCitas();
    }

    // Obtener los servicios más adquiridos
    @GetMapping("/top-servicios")
    public List<ServicioModel> getTopServicios() {
        return reporteService.getTopServicios();
    }

    // Obtener los servidores más usados
    @GetMapping("/top-servidores")
    public List<ServidorModel> getTopServidores() {
        return reporteService.getTopServidores();
    }

    // Obtener el dinero generado en un rango de fechas (por ejemplo: ?fechaInicio=2024-01-01&fechaFin=2024-12-31)
    @GetMapping("/dinero-fecha")
    public Double dineroGeneradoPorFecha(
        @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
        @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        return reporteService.dineroGeneradoporFecha(fechaInicio, fechaFin);
    }

    // Obtener el dinero generado por cada servicio
    @GetMapping("/dinero-por-servicio")
    public Map<String, Double> dineroGeneradoPorServicio() {
        return reporteService.dineroGeneradoporServicio();
    }

    // Obtener el dinero generado por cada servidor
    @GetMapping("/dinero-por-servidor")
    public List<ServidorGanancioDTO> dineroGeneradoPorServidor() {
        return reporteService.dineroGeneradorporServidor();
    }
    
}
