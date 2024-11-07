package com.project.Citas.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.Citas.JsonResponse;
import com.project.Citas.DTO.citaDTO;
import com.project.Citas.DTO.horarioDTO;
import com.project.Citas.DTO.horarioDisponible;
import com.project.Citas.Errors.ModelNotFoundException;
import com.project.Citas.models.horarioModel;
import com.project.Citas.models.Citas.ServicioModel;
import com.project.Citas.models.Citas.ServidorModel;
import com.project.Citas.models.Citas.asignacionServicios;
import com.project.Citas.models.Citas.citasModel;
import com.project.Citas.repositories.asignacionServiciosRepository;
import com.project.Citas.repositories.citasRepository;
import com.project.Citas.repositories.horarioRepository;
import com.project.Citas.repositories.servicioRepository;

@Service
public class clienteService {
    @Autowired
    @Mock
    private citasRepository citasRepositories;

    @Autowired
    @Mock
    private horarioRepository horarioRepository;

    @Autowired
    @Mock
    private servicioRepository servicioRepository;
    @Autowired
    @Mock
    private asignacionServiciosRepository asignacionServiciosRepository;


    public ArrayList<citasModel> getCitasById(Long id) {
        return (ArrayList<citasModel>) citasRepositories.findByUsuario_Id(id);
    }

    public ArrayList<ServicioModel> getServicios() {
        return (ArrayList<ServicioModel>) servicioRepository.findAll();
    }

    public ArrayList<ServidorModel> getServidorPorServicio(Long id){
        List<asignacionServicios> servicios = asignacionServiciosRepository.findByIdServicio_Id(id);
        ArrayList<ServidorModel> servidores = new ArrayList<>();
        for (asignacionServicios tmpServicios : servicios) {
            servidores.add(tmpServicios.getIdServidor());
        }
        return servidores;
    }

    public JsonResponse saveCita(citasModel newCitasModel) {
        if(newCitasModel.getEstadoCita() != null && newCitasModel.getIdServidor() != null 
        && newCitasModel.getFecha() != null && newCitasModel.getServicio() != null && newCitasModel.getUsuario() != null){
        citasRepositories.save(newCitasModel);
        return new JsonResponse(500, "Cita creada correctamente");
        }else 
            return new JsonResponse(400, "Campos nulos");
    }

    public ArrayList<citasModel> getCitasByIdAndFecha(Long id, LocalDate fecha) {
        return (ArrayList<citasModel>) citasRepositories.findByUsuario_IdAndFecha(id, fecha);
    }

    public ArrayList<horarioDTO> getHorarios(citaDTO solicitud) {
        List<citasModel> citas = citasRepositories.findByFechaAndServicio_Id(solicitud.getFecha(),
                solicitud.getServicioId());
        ArrayList<horarioDTO> ocupados = new ArrayList<>();
        for (citasModel tmp : citas) {
            horarioDTO tmp2 = new horarioDTO();
            tmp2.setDia(solicitud.getFecha().getDayOfWeek());
            tmp2.setHoraInicio(tmp.getHoraInicio());
            LocalTime horaFin = tmp.getHoraInicio().plus(tmp.getServicio().getDuracion(), ChronoUnit.MINUTES);
            tmp2.setHoraFin(horaFin);
            ocupados.add(tmp2);
        }
        return ocupados;
    }

    public ArrayList<horarioDisponible> getHorariosDisponiblesPorServicio(citaDTO solicitud) {
        // Obtener todos los horarios de servidores que trabajan el día solicitado y
        // ofrecen el servicio
        List<asignacionServicios> servidores = asignacionServiciosRepository
                .findByIdServicio_Id(solicitud.getServicioId());
        ArrayList<horarioModel> horariosServidores = new ArrayList<>();
        if (solicitud.getServidorId() != null) {
            Optional<horarioModel> unicoHorario = horarioRepository
                    .findByDiaAndServidor_Id(solicitud.getFecha().getDayOfWeek(), solicitud.getServidorId());
            if(unicoHorario.isPresent())
            horariosServidores.add(unicoHorario.get());
            else throw new ModelNotFoundException("No hay horarios disponibles");
        } else {
            for (asignacionServicios asignacionServicios : servidores) {
                Optional<horarioModel> hModel = horarioRepository.findByDiaAndServidor_Id(
                        solicitud.getFecha().getDayOfWeek(), asignacionServicios.getIdServidor().getId());
                if (hModel.isPresent())
                    horariosServidores.add(hModel.get());
            }
        }
        if(horariosServidores.size() == 0)
        throw new ModelNotFoundException("No hay horarios disponibles");
        ArrayList<horarioDisponible> horariosDisponibles = new ArrayList<>();
        ServicioModel servicio = servicioRepository.findById(solicitud.getServicioId()).get();
        for (horarioModel horario : horariosServidores) {
            // Definir los límites del horario del servidor en este día
            LocalTime inicioTrabajo = horario.getHoraInicio();
            LocalTime finTrabajo = horario.getHoraFin();
            int duracionServicio = servicio.getDuracion(); // en minutos

            // Obtener las citas ocupadas del servidor en el día y servicio solicitado
            List<citasModel> citasOcupadas = citasRepositories.findByFechaAndIdServidor_IdAndEstadoCita_Id(
                    solicitud.getFecha(), horario.getServidor().getId(),1L);
            ArrayList<horarioDisponible> ocupados = new ArrayList<>();
            for (citasModel tmp : citasOcupadas) {
                horarioDisponible tmp2 = new horarioDisponible();
                tmp2.setHoraInicio(tmp.getHoraInicio());
                LocalTime horaFin = tmp.getHoraInicio().plus(tmp.getServicio().getDuracion(), ChronoUnit.MINUTES);
                tmp2.setHoraFin(horaFin);
                tmp2.setServidor(horario.getServidor());
                ocupados.add(tmp2);
            }

            // Crear intervalos dentro del horario del servidor y verificar disponibilidad
            LocalTime horaInicio = inicioTrabajo;
            while (horaInicio.plus(duracionServicio, ChronoUnit.MINUTES).isBefore(finTrabajo) ||
                    horaInicio.plus(duracionServicio, ChronoUnit.MINUTES).equals(finTrabajo)) {

                LocalTime horaFin = horaInicio.plus(duracionServicio, ChronoUnit.MINUTES);
                horarioDisponible intervalo = new horarioDisponible();
                intervalo.setHoraInicio(horaInicio);
                intervalo.setHoraFin(horaFin);
                intervalo.setServidor(horario.getServidor());
                boolean estaOcupado = ocupados.stream()
                        .anyMatch(ocupado -> intervalo.getHoraInicio().isBefore(ocupado.getHoraFin()) &&
                                intervalo.getHoraFin().isAfter(ocupado.getHoraInicio()));

                if (!estaOcupado) {
                    horariosDisponibles.add(intervalo);
                }
                horaInicio = horaInicio.plus(duracionServicio, ChronoUnit.MINUTES);
            }
        }

        return horariosDisponibles;
    }

}
