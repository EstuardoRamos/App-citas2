package com.project.Citas.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.Citas.DTO.ServidorGanancioDTO;
import com.project.Citas.models.Citas.ServicioModel;
import com.project.Citas.models.Citas.ServidorModel;
import com.project.Citas.models.Citas.citasModel;
import com.project.Citas.repositories.citasRepository;

@Service
public class reporteService {
    
    @Autowired
    private citasRepository citasRepository;

    public ArrayList<citasModel> getAllCitas(){
        return (ArrayList<citasModel>)citasRepository.findAll();
    }

    public List<Object[]> getUsuarioconMasCitas(){
        return citasRepository.findUsuariosConMasCitas();
    }

    public List<citasModel> getCitasporEstado(Long estado){
        return citasRepository.findByEstadoCita_Id(estado);
    }

     public List<ServicioModel> getTopServicios() {
        return ((Collection<citasModel>) citasRepository.findAll()).stream()
                .collect(Collectors.groupingBy(citasModel::getServicio, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<ServicioModel, Long>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    // Obtener los servidores más usados
    public List<ServidorModel> getTopServidores() {
        return ((Collection<citasModel>) citasRepository.findAll()).stream()
                .collect(Collectors.groupingBy(citasModel::getIdServidor, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<ServidorModel, Long>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    // Obtener el dinero generado por fecha en un rango específico
    public Double dineroGeneradoporFecha(LocalDate fechaInicio, LocalDate fechaFin) {
        return ((Collection<citasModel>) citasRepository.findAll()).stream()
                .filter(cita -> !cita.getFecha().isBefore(fechaInicio) && !cita.getFecha().isAfter(fechaFin))
                .mapToDouble(cita -> cita.getServicio().getPrecio())
                .sum();
    }

    // Obtener el dinero generado por cada servicio
    public Map<String, Double> dineroGeneradoporServicio() {
        return ((Collection<citasModel>) citasRepository.findAll()).stream()
                .collect(Collectors.groupingBy(
                        cita -> cita.getServicio().getNombre(), // Agrupa por el nombre del servicio
                        Collectors.summingDouble(cita -> cita.getServicio().getPrecio()) // Suma los precios
                ));
    }
    // Obtener el dinero generado por cada servidor
    public List<ServidorGanancioDTO> dineroGeneradorporServidor() {
        return ((Collection<citasModel>) citasRepository.findAll()).stream()
                .collect(Collectors.groupingBy(
                        citasModel::getIdServidor,
                        Collectors.summingDouble(cita -> cita.getServicio().getPrecio())
                ))
                .entrySet().stream()
                .map(entry -> {
                    ServidorModel servidor = entry.getKey();
                    Double totalGenerado = entry.getValue();
                    return new ServidorGanancioDTO(
                            servidor.getId(),
                            servidor.getNombre(),
                            servidor.getDescripcion(),
                            totalGenerado
                    );
                })
                .collect(Collectors.toList());
    }
}
