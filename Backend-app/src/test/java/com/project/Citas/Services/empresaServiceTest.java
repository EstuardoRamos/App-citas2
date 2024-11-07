package com.project.Citas.Services;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import com.project.Citas.Errors.ModelNotFoundException;
import com.project.Citas.Images.DropBoxImage;
import com.project.Citas.Images.ImageService;
import com.project.Citas.models.EmpresaModel;
import com.project.Citas.models.Empresa.diasAtencionModel;
import com.project.Citas.repositories.EmpresaRepository;
import com.project.Citas.repositories.diasAtencionRepository;

class empresaServiceTest {

    @InjectMocks
    private com.project.Citas.services.empresaService empresaService;

    @Mock
    private EmpresaRepository empresaRepositorie;

    @Mock
    private diasAtencionRepository diasAtencionRepositorie;

    @Mock
    private MultipartFile imagen;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetEmpresaByIdExists() {
        EmpresaModel empresa = new EmpresaModel();
        empresa.setId(1L);
        empresa.setNombre("Empresa Test");

        when(empresaRepositorie.findById(1L)).thenReturn(Optional.of(empresa));

        Optional<EmpresaModel> result = empresaService.getEmpresaById(1L);

        assertTrue(result.isPresent());
        assertEquals("Empresa Test", result.get().getNombre());
        verify(empresaRepositorie, times(1)).findById(1L);
    }

    @Test
    void testGetEmpresaByIdNotFound() {
        when(empresaRepositorie.findById(1L)).thenReturn(Optional.empty());

        Optional<EmpresaModel> result = empresaService.getEmpresaById(1L);

        assertFalse(result.isPresent());
        verify(empresaRepositorie, times(1)).findById(1L);
    }

    @Test
    void testSaveHorarios() {
        ArrayList<diasAtencionModel> horarios = new ArrayList<>();
        diasAtencionModel horario = new diasAtencionModel();
        horarios.add(horario);

        empresaService.saveHorarios(horarios);

        verify(diasAtencionRepositorie, times(1)).saveAll(horarios);
    }

    @Test
    void testGetHorarios() {
        ArrayList<diasAtencionModel> horarios = new ArrayList<>();
        diasAtencionModel horario = new diasAtencionModel();
        horarios.add(horario);

        when(diasAtencionRepositorie.findAll()).thenReturn(horarios);

        ArrayList<diasAtencionModel> result = empresaService.getHorarios();

        assertEquals(1, result.size());
        verify(diasAtencionRepositorie, times(1)).findAll();
    }

    @Test
    void testGetBase64EmpresaExists() {
        EmpresaModel empresa = new EmpresaModel();
        empresa.setId(1L);
        empresa.setUrlImagen("url_image");

        when(empresaRepositorie.findById(1L)).thenReturn(Optional.of(empresa));
        ImageService imageService = mock(ImageService.class);
        
        when(imageService.convertImageToBase64("url_image")).thenReturn("base64_string");

        String result = empresaService.getBase64();

        assertEquals("base64_string", result);
        verify(empresaRepositorie, times(1)).findById(1L);
    }

    @Test
    void testGetBase64EmpresaNotFound() {
        when(empresaRepositorie.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ModelNotFoundException.class, () -> {
            empresaService.getBase64();
        });

        assertEquals("La empresa no existe", exception.getMessage());
        verify(empresaRepositorie, times(1)).findById(1L);
    }

    @Test
    void testSaveEmpresaExists() {
        EmpresaModel empresa = new EmpresaModel();
        empresa.setId(1L);
        empresa.setNombre("Empresa Test");

        when(empresaRepositorie.findById(1L)).thenReturn(Optional.of(empresa));
        when(DropBoxImage.Cargar(any(MultipartFile.class))).thenReturn("url_image");

        EmpresaModel result = empresaService.saveEmpresa(empresa, imagen);

        assertEquals(empresa, result);
        verify(empresaRepositorie, times(1)).findById(1L);
        verify(empresaRepositorie, times(1)).save(empresa);
    }

    @Test
    void testSaveEmpresaNotFound() {
        EmpresaModel empresa = new EmpresaModel();
        empresa.setId(2L);

        when(empresaRepositorie.findById(empresa.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ModelNotFoundException.class, () -> {
            empresaService.saveEmpresa(empresa, imagen);
        });

        assertEquals("Los datos iniciales de la empresa aun no han sido creados en la base de datos", exception.getMessage());
        verify(empresaRepositorie, times(1)).findById(2L);
    }
}
