package com.project.Citas.models.primariasCompuestas;

import java.io.Serializable;

public class servidorServicioId implements Serializable{
    private Long idServidor;
    private Long idServicio;
    public servidorServicioId(){

    }

    
    public servidorServicioId(Long idServidor, Long idServicio) {
        this.idServidor = idServidor;
        this.idServicio = idServicio;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        servidorServicioId that = (servidorServicioId) o;
        if (!idServidor.equals(that.idServidor)) return false;
        return idServicio.equals(that.idServicio);
    }

    @Override
    public int hashCode() {
        int result = idServidor.hashCode();
        result = 31 * result + idServicio.hashCode();
        return result;
    }
    public Long getIdServidor() {
        return idServidor;
    }
    public void setIdServidor(Long idServidor) {
        this.idServidor = idServidor;
    }
    public Long getIdServicio() {
        return idServicio;
    }
    public void setIdServicio(Long idServicio) {
        this.idServicio = idServicio;
    }

    
}
