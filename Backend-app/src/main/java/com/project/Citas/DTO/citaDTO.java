package com.project.Citas.DTO;

import java.time.LocalDate;
public class citaDTO {
    private Long ServicioId;
    private Long ServidorId;
    private LocalDate fecha;
    public Long getServicioId() {
        return ServicioId;
    }
    public Long getServidorId() {
        return ServidorId;
    }
    public void setServidorId(Long servidorId) {
        ServidorId = servidorId;
    }
    public void setServicioId(Long servicioId) {
        ServicioId = servicioId;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
  
}
