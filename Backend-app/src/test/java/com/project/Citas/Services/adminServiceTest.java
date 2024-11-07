package com.project.Citas.Services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.project.Citas.DTO.rolPermisosRequest;
import com.project.Citas.Errors.ModelAlreadyExistException;
import com.project.Citas.Errors.ModelNotFoundException;
import com.project.Citas.JsonResponse;
import com.project.Citas.models.Citas.citasModel;
import com.project.Citas.models.permisosModel;
import com.project.Citas.models.rolModel;
import com.project.Citas.repositories.citasRepository;
import com.project.Citas.repositories.permisosRepository;
import com.project.Citas.repositories.rolRepository;
import com.project.Citas.repositories.userRepository;
import com.project.Citas.services.adminService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class adminServiceTest {

    @InjectMocks
    private adminService service;

    @Mock
    private citasRepository citasRepository;

    @Mock
    private rolRepository rolRepository;

    @Mock
    private userRepository userRepository;

    @Mock
    private permisosRepository permisosRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCitas() {
        List<citasModel> citasList = new ArrayList<>();
        citasList.add(new citasModel());
        when(citasRepository.findAll()).thenReturn(citasList);

        List<citasModel> result = service.getAllCitas();

        assertEquals(1, result.size());
        verify(citasRepository, times(1)).findAll();
    }

    @Test
    void testUpdateEstadoCita_CitaExists() {
        Long idCita = 1L;
        Long idEstado = 1L;
        citasModel cita = new citasModel();
        cita.setId(idCita);
        
        // Mock for cita and estado
        when(citasRepository.findById(idCita)).thenReturn(Optional.of(cita));
        when(citasRepository.save(any(citasModel.class))).thenReturn(cita);

        JsonResponse response = service.updateEstadoCita(idEstado, idCita);

        assertEquals(500, response.getStatusCode());
        assertEquals("estado de Cita actualizada correctamente", response.getMensaje());
        verify(citasRepository, times(1)).save(cita);
    }

    @Test
    void testUpdateEstadoCita_CitaNotFound() {
        Long idCita = 1L;
        Long idEstado = 1L;

        when(citasRepository.findById(idCita)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ModelNotFoundException.class, () -> {
            service.updateEstadoCita(idEstado, idCita);
        });

        assertEquals("La cita no existe", exception.getMessage());
    }

    @Test
    void testCreateRol_RolDoesNotExist() {
        String nombreRol = "Admin";
        ArrayList<permisosModel> permisos = new ArrayList<>();
        permisos.add(new permisosModel(1L));

        when(rolRepository.findByNombre(nombreRol)).thenReturn(Optional.empty());
        when(rolRepository.save(any(rolModel.class))).thenReturn(new rolModel(nombreRol));

        rolPermisosRequest result = service.createRol(nombreRol, permisos);

        assertNotNull(result);
        assertEquals(nombreRol, result.getRol().getNombre());
        verify(rolRepository, times(1)).save(any(rolModel.class));
    }

    @Test
    void testCreateRol_RolAlreadyExists() {
        String nombreRol = "Admin";
        ArrayList<permisosModel> permisos = new ArrayList<>();
        
        rolModel existingRole = new rolModel(nombreRol);
        when(rolRepository.findByNombre(nombreRol)).thenReturn(Optional.of(existingRole));

        Exception exception = assertThrows(ModelAlreadyExistException.class, () -> {
            service.createRol(nombreRol, permisos);
        });

        assertEquals("Ya existe un rol con el nombre: " + nombreRol, exception.getMessage());
    }
}