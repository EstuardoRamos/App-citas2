package com.project.Citas.Services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Optional;

import com.project.Citas.DTO.ServidorDTO;
import com.project.Citas.JsonResponse;
import com.project.Citas.Errors.ModelAlreadyExistException;
import com.project.Citas.Errors.ModelNotFoundException;
import com.project.Citas.models.Citas.ServicioModel;
import com.project.Citas.models.Citas.ServidorModel;
import com.project.Citas.models.Citas.citasModel;
import com.project.Citas.repositories.asignacionServiciosRepository;
import com.project.Citas.repositories.citasRepository;
import com.project.Citas.repositories.estadoCitaRepository;
import com.project.Citas.repositories.horarioRepository;
import com.project.Citas.repositories.servicioRepository;
import com.project.Citas.repositories.servidorRepository;
import com.project.Citas.services.citasService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class citasServiceTest {

    @InjectMocks
    private citasService service;

    @Mock
    private citasRepository citasRepositories;

    @Mock
    private servidorRepository servidorRepositorie;

    @Mock
    private servicioRepository servicioRepositorie;

    @Mock
    private estadoCitaRepository estadoCitaRepositorie;

    @Mock
    private asignacionServiciosRepository asignacionServiciosRepositorie;

    @Mock
    private horarioRepository horarioRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCitas() {
        citasModel newCita = new citasModel();
        JsonResponse response = service.saveCitas(newCita);
        
        assertEquals(500, response.getStatusCode());
        assertEquals("Cita creada correctamente", response.getMensaje());
        verify(citasRepositories, times(1)).save(newCita);
    }

    @Test
    void testSaveServidor_Success() {
        ServidorDTO request = new ServidorDTO();
        ServidorModel servidor = new ServidorModel();
        servidor.setNombre("Servidor Test");
        request.setServidor(servidor);
        request.setServicios(new ArrayList<>());
        request.setHorarios(new ArrayList<>());

        when(servidorRepositorie.findByNombre(servidor.getNombre())).thenReturn(Optional.empty());

        JsonResponse response = service.saveServidor(request);
        
        assertEquals(500, response.getStatusCode());
        assertEquals("servidor creado correctamente", response.getMensaje());
        verify(servidorRepositorie, times(1)).save(servidor);
        verify(asignacionServiciosRepositorie, times(1)).saveAll(anyList());
        verify(horarioRepository, times(1)).saveAll(anyList());
    }

    @Test
    void testSaveServidor_AlreadyExists() {
        ServidorDTO request = new ServidorDTO();
        ServidorModel servidor = new ServidorModel();
        servidor.setNombre("Servidor Test");
        request.setServidor(servidor);
        
        when(servidorRepositorie.findByNombre(servidor.getNombre())).thenReturn(Optional.of(servidor));

        assertThrows(ModelAlreadyExistException.class, () -> service.saveServidor(request));
    }

    @Test
    void testUpdateServidor_Success() {
        ServidorDTO newServidor = new ServidorDTO();
        ServidorModel servidor = new ServidorModel();
        servidor.setId(1L);
        newServidor.setServidor(servidor);
        
        when(servidorRepositorie.findById(servidor.getId())).thenReturn(Optional.of(servidor));

        JsonResponse response = service.updateServidor(newServidor);
        
        assertEquals(500, response.getStatusCode());
        assertEquals("Servidor actualizado correctamente", response.getMensaje());
        verify(servidorRepositorie, times(1)).save(servidor);
        verify(asignacionServiciosRepositorie, times(1)).deleteAllByIdServidor_Id(servidor.getId());
    }

    @Test
    void testUpdateServidor_NotFound() {
        ServidorDTO newServidor = new ServidorDTO();
        ServidorModel servidor = new ServidorModel();
        servidor.setId(1L);
        newServidor.setServidor(servidor);

        when(servidorRepositorie.findById(servidor.getId())).thenReturn(Optional.empty());

        assertThrows(ModelNotFoundException.class, () -> service.updateServidor(newServidor));
    }

    @Test
    void testSaveServicio_Success() {
        ServicioModel newServicioModel = new ServicioModel();
        newServicioModel.setNombre("Servicio Test");
        newServicioModel.setDuracion(30);
        newServicioModel.setPrecio(100);

        when(servicioRepositorie.findByNombre(newServicioModel.getNombre())).thenReturn(Optional.empty());

        JsonResponse response = service.saveServicio(newServicioModel);
        
        assertEquals(500, response.getStatusCode());
        assertEquals("Servicio creado correctamente", response.getMensaje());
        verify(servicioRepositorie, times(1)).save(newServicioModel);
    }

    @Test
    void testSaveServicio_AlreadyExists() {
        ServicioModel newServicioModel = new ServicioModel();
        newServicioModel.setNombre("Servicio Test");

        when(servicioRepositorie.findByNombre(newServicioModel.getNombre())).thenReturn(Optional.of(newServicioModel));

        assertThrows(ModelAlreadyExistException.class, () -> service.saveServicio(newServicioModel));
    }

}