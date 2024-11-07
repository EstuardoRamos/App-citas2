package com.project.Citas.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.Citas.JsonResponse;
import com.project.Citas.DTO.ServidorDTO;
import com.project.Citas.DTO.horarioDTO;
import com.project.Citas.Errors.ModelAlreadyExistException;
import com.project.Citas.Errors.ModelNotFoundException;
import com.project.Citas.models.horarioModel;
import com.project.Citas.models.Citas.ServicioModel;
import com.project.Citas.models.Citas.ServidorModel;
import com.project.Citas.models.Citas.asignacionServicios;
import com.project.Citas.models.Citas.citasModel;
import com.project.Citas.models.Citas.estadoCitaModel;
import com.project.Citas.models.primariasCompuestas.servidorServicioId;
import com.project.Citas.repositories.asignacionServiciosRepository;
import com.project.Citas.repositories.citasRepository;
import com.project.Citas.repositories.estadoCitaRepository;
import com.project.Citas.repositories.horarioRepository;
import com.project.Citas.repositories.servicioRepository;
import com.project.Citas.repositories.servidorRepository;

import jakarta.transaction.Transactional;

@Service
public class citasService {
    @Autowired
    @Mock
    private citasRepository citasRepositories;

    @Autowired
    @Mock
    private servidorRepository servidorRepositorie;

    @Autowired
    @Mock
    private servicioRepository servicioRepositorie;

    @Autowired
    @Mock
    private estadoCitaRepository estadoCitaRepositorie;
    @Autowired
    @Mock
    private asignacionServiciosRepository asignacionServiciosRepositorie;
    @Autowired
    @Mock
    private horarioRepository horarioRepository;

    public JsonResponse saveCitas(citasModel newCita) {
        citasRepositories.save(newCita);
        return new JsonResponse(500, "Cita creada correctamente");
    }

    public JsonResponse saveServidor(ServidorDTO request) {
        Optional<ServidorModel> exist = servidorRepositorie.findByNombre(request.getServidor().getNombre());
        if (!exist.isPresent()) {
            ServidorModel servidor = request.getServidor();
            if(servidor.getNombre()!=null && servidor.getDescripcion() != null){
            ServidorModel newServidor = servidorRepositorie.save(request.getServidor());
            ArrayList<asignacionServicios> serviciosEntrantes = generarAsignacionesdeServicios(request.getServicios(),newServidor.getId());
            asignacionServiciosRepositorie.saveAll(serviciosEntrantes);
            generarHorarios(newServidor, request.getHorarios());
            return new JsonResponse(500, "servidor creado correctamente");
            }else
                return new JsonResponse(404, "No fue posible crear el servidor");
        } else
            throw new ModelAlreadyExistException(
                    "Ya existe un servidor con el nombre: " + request.getServidor().getNombre());
    }

    public void generarHorarios(ServidorModel servidor,ArrayList<horarioDTO> horarios){
        ArrayList<horarioModel> horariosTmp = new ArrayList<>();
        for (horarioDTO tmpDto : horarios) {
            horarioModel tmp = new horarioModel();
            tmp.setServidor(servidor);
            tmp.setDia(tmpDto.getDia());
            tmp.setHoraFin(tmpDto.getHoraFin());
            tmp.setHoraInicio(tmpDto.getHoraInicio());
            horariosTmp.add(tmp);
        }
        horarioRepository.saveAll(horariosTmp);
    }
    public ArrayList<asignacionServicios> generarAsignacionesdeServicios(ArrayList<ServicioModel> servicioModels,Long id){
        ArrayList<asignacionServicios> serviciosEntrantes = new ArrayList<>();
            for (ServicioModel servicio : servicioModels) {
                asignacionServicios tmp = new asignacionServicios();
                tmp.setId(new servidorServicioId(id, servicio.getId()));
                tmp.setIdServicio(new ServicioModel(servicio.getId()));
                tmp.setIdServidor(new ServidorModel(id));
                serviciosEntrantes.add(tmp);
            }
            return serviciosEntrantes;
    }

    public ArrayList<estadoCitaModel> getAllEstadosCitas() {
        return (ArrayList<estadoCitaModel>) estadoCitaRepositorie.findAll();
    }

    public List<citasModel> getAllCitasByIdUser(Long id) {
        return citasRepositories.findByUsuario_Id(id);
    }

    public ArrayList<ServicioModel> getAllServicios() {
        return (ArrayList<ServicioModel>) (servicioRepositorie.findAll());
    }

    public ArrayList<ServidorModel> getAllServidores() {
        return (ArrayList<ServidorModel>) (servidorRepositorie.findAll());
    }

    public ArrayList<citasModel> getAllCitas() {
        return (ArrayList<citasModel>) (citasRepositories.findAll());
    }
    
    @Transactional
    public JsonResponse updateServidor(ServidorDTO newServidor) {
        if (servidorRepositorie.findById(newServidor.getServidor().getId()).isPresent()) {
            servidorRepositorie.save(newServidor.getServidor());
            asignacionServiciosRepositorie.deleteAllByIdServidor_Id(newServidor.getServidor().getId());
            asignacionServiciosRepositorie.saveAll(generarAsignacionesdeServicios(newServidor.getServicios(), newServidor.getServidor().getId()));
            return new JsonResponse(500, "Servidor actualizado correctamente");
        } else
            throw new ModelNotFoundException("No existe el servidor");
    }

    public JsonResponse saveServicio(ServicioModel newServicioModel) {
        Optional<ServicioModel> exist = servicioRepositorie.findByNombre(newServicioModel.getNombre());
        if (!exist.isPresent()) {
            if(newServicioModel.getNombre() != null && newServicioModel.getDuracion() > 0 && newServicioModel.getPrecio() > 0){
                servicioRepositorie.save(newServicioModel);
                return new JsonResponse(500, "Servicio creado correctamente");
            }else
                return new JsonResponse(404, "No fue posible crear el servicio");
            
        } else
            throw new ModelAlreadyExistException(
                    "Ya existe un servicio con el nombre: " + newServicioModel.getNombre());
    }

    public JsonResponse updateServicio(ServicioModel newServicioModel) {
        Optional<ServicioModel> exist = servicioRepositorie.findById(newServicioModel.getId());
        if (exist.isPresent()) {
            servicioRepositorie.save(newServicioModel);
            return new JsonResponse(500, "Servicio actualizado correctamente");
        } else
            throw new ModelNotFoundException("El servicio no existe");

    }

    public List<ServicioModel> getServiciosPorServidor(Long id){
        List<asignacionServicios> asignaciones = asignacionServiciosRepositorie.findByIdServidor_Id(id);
        
        // Extraer los servicios de las asignaciones
        return asignaciones.stream()
                .map(asignacionServicios::getIdServicio)
                .collect(Collectors.toList());
    }

}
