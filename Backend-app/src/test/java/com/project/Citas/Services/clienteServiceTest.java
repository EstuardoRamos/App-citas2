package com.project.Citas.Services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.project.Citas.JsonResponse;
import com.project.Citas.DTO.citaDTO;
import com.project.Citas.DTO.horarioDisponible;
import com.project.Citas.Errors.ModelNotFoundException;
import com.project.Citas.models.horarioModel;
import com.project.Citas.models.userModel;
import com.project.Citas.models.Citas.ServicioModel;
import com.project.Citas.models.Citas.ServidorModel;
import com.project.Citas.models.Citas.asignacionServicios;
import com.project.Citas.models.Citas.citasModel;
import com.project.Citas.models.Citas.estadoCitaModel;
import com.project.Citas.repositories.asignacionServiciosRepository;
import com.project.Citas.repositories.citasRepository;
import com.project.Citas.repositories.horarioRepository;
import com.project.Citas.repositories.servicioRepository;

public class clienteServiceTest {

    @InjectMocks
    private com.project.Citas.services.clienteService clienteService;

    @Mock
    private citasRepository citasRepositories;

    @Mock
    private horarioRepository horarioRepository;

    @Mock
    private servicioRepository servicioRepository;

    @Mock
    private asignacionServiciosRepository asignacionServiciosRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCitasById() {
        Long usuarioId = 1L;
        citasModel cita = new citasModel();
        ArrayList<citasModel> expectedCitas = new ArrayList<>();
        expectedCitas.add(cita);
        
        when(citasRepositories.findByUsuario_Id(usuarioId)).thenReturn(expectedCitas);

        ArrayList<citasModel> result = clienteService.getCitasById(usuarioId);
        assertEquals(expectedCitas, result);
        verify(citasRepositories).findByUsuario_Id(usuarioId);
    }

    @Test
    public void testSaveCita_WithValidData() {
        citasModel newCita = new citasModel();
        newCita.setEstadoCita(new estadoCitaModel());
        newCita.getEstadoCita().setId(1L);
        newCita.setIdServidor(new ServidorModel());
        newCita.setFecha(LocalDate.now());
        newCita.setServicio(new ServicioModel());
        newCita.setUsuario(new userModel());

        JsonResponse response = clienteService.saveCita(newCita);
        assertEquals(500, response.getStatusCode());
        assertEquals("Cita creada correctamente", response.getMensaje());
        verify(citasRepositories).save(newCita);
    }

    @Test
    public void testSaveCita_WithNullFields() {
        citasModel newCita = new citasModel();

        JsonResponse response = clienteService.saveCita(newCita);
        assertEquals(400, response.getStatusCode());
        assertEquals("Campos nulos", response.getMensaje());
        verify(citasRepositories, never()).save(any());
    }

    @Test
    public void testGetHorariosDisponiblesPorServicio_WithAvailableSlots() {
        citaDTO solicitud = new citaDTO();
        solicitud.setServicioId(1L);
        solicitud.setFecha(LocalDate.now());

        asignacionServicios asignacion = new asignacionServicios();
        asignacion.setIdServidor(new ServidorModel());
        ArrayList<asignacionServicios> asignaciones = new ArrayList<>();
        asignaciones.add(asignacion);
        
        when(asignacionServiciosRepository.findByIdServicio_Id(solicitud.getServicioId())).thenReturn(asignaciones);
        when(horarioRepository.findByDiaAndServidor_Id(any(), any())).thenReturn(Optional.of(new horarioModel()));

        ServicioModel servicio = new ServicioModel();
        servicio.setDuracion(30); // Duración en minutos
        when(servicioRepository.findById(solicitud.getServicioId())).thenReturn(Optional.of(servicio));

        ArrayList<horarioDisponible> result = clienteService.getHorariosDisponiblesPorServicio(solicitud);
        assertNotNull(result);
        // Aquí puedes agregar más aserciones basadas en los resultados esperados
    }

    @Test
    public void testGetHorariosDisponiblesPorServicio_WithNoAvailableSlots() {
        citaDTO solicitud = new citaDTO();
        solicitud.setServicioId(1L);
        solicitud.setFecha(LocalDate.now());

        when(asignacionServiciosRepository.findByIdServicio_Id(solicitud.getServicioId())).thenReturn(new ArrayList<>());

        assertThrows(ModelNotFoundException.class, () -> {
            clienteService.getHorariosDisponiblesPorServicio(solicitud);
        });
    }
}