package com.project.Citas.DTO;

public class ServidorGanancioDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double totalGenerado;

    public ServidorGanancioDTO(Long id, String nombre, String descripcion, Double totalGenerado) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.totalGenerado = totalGenerado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getTotalGenerado() {
        return totalGenerado;
    }

    public void setTotalGenerado(Double totalGenerado) {
        this.totalGenerado = totalGenerado;
    }
    

}
