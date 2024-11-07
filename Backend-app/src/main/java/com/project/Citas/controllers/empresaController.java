package com.project.Citas.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.Citas.models.EmpresaModel;
import com.project.Citas.models.Empresa.diasAtencionModel;
import com.project.Citas.services.empresaService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/empresa")

public class empresaController {
    @Autowired
    private empresaService empresaService;

    @Operation(summary = "Obtener Empresa", description = "Obtener empresa por id")
    @GetMapping("/{id}")
    public Optional<EmpresaModel> getEmpresaById(@PathVariable("id") Long id) {
        return empresaService.getEmpresaById(id);
    }

    @Operation(summary = "Obtener los dias y horarios de atencion de la empresa")
    @GetMapping
    public ArrayList<diasAtencionModel> getHorarios(){
        return empresaService.getHorarios();
    }
    
    @PostMapping("/horario")
    public void saveHorarios(@RequestBody ArrayList<diasAtencionModel> horario){
        empresaService.saveHorarios(horario);
    }

    @Operation(summary = "Obtener la imagen en base 64")
    @GetMapping("/imagen")
    public String getBase64Imagen(){
        return empresaService.getBase64();
    }

    @Operation(summary = "Actualizar datos de la empresa", description = "Actualizacion de empresa")
    @PostMapping(consumes =org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public EmpresaModel saveEmpresa(
            @RequestParam("empresaModel") String empresaJson,
            @RequestParam(value = "imagen",required = false) MultipartFile imagen) {
        // Convertir el JSON a un objeto EmpresaModel
        try {
            // Convertir el JSON a un objeto EmpresaModel
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            JsonNode rootNode = objectMapper.readTree(empresaJson);
            EmpresaModel empresaModel = objectMapper.readValue(rootNode.get("empresaModel").toString(), EmpresaModel.class);
            if(imagen == null || imagen.isEmpty())
            return empresaService.saveEmpresa(empresaModel, null);
            else
            return empresaService.saveEmpresa(empresaModel, imagen);
        } catch (JsonProcessingException e) {
            // Manejar el error de procesamiento de JSON
            throw new RuntimeException("Error al procesar el JSON de la empresa: " + e.getMessage());
        }
    }
}
