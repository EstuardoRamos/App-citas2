package com.project.Citas.models;
import com.project.Citas.models.primariasCompuestas.rolUsuarioId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "asignacionRol")
public class asignacionRolModel {
    
    @EmbeddedId
    private rolUsuarioId id;

    @ManyToOne
    @MapsId("idRol")
    @JoinColumn(name = "rol_id") 
    private rolModel idRol;

    @ManyToOne
    @MapsId("idUsuario")
    @JoinColumn(name = "usuario_id") 
    private userModel idUsuario;

    public rolUsuarioId getId() {
        return id;
    }

    public void setId(rolUsuarioId id) {
        this.id = id;
    }

    public rolModel getIdRol() {
        return idRol;
    }

    public void setIdRol(rolModel idRol) {
        this.idRol = idRol;
    }

    public userModel getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(userModel idUsuario) {
        this.idUsuario = idUsuario;
    }

    
}
