package com.project.Citas.models.Citas;

import com.project.Citas.models.primariasCompuestas.servidorServicioId;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "asignacionServicios")
public class asignacionServicios {
    @EmbeddedId
    private servidorServicioId id;
    @ManyToOne
    @MapsId("idServicio")
    @JoinColumn(name = "servicio_id") 
    private ServicioModel idServicio;

    @ManyToOne
    @MapsId("idServidor")
    @JoinColumn(name = "servidor_id") 
    private ServidorModel idServidor;

    public servidorServicioId getId() {
        return id;
    }

    public void setId(servidorServicioId id) {
        this.id = id;
    }

    public ServicioModel getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(ServicioModel idServicio) {
        this.idServicio = idServicio;
    }

    public ServidorModel getIdServidor() {
        return idServidor;
    }

    public void setIdServidor(ServidorModel idServidor) {
        this.idServidor = idServidor;
    }
    

}
