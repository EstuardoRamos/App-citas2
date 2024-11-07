package com.project.Citas.models;

import com.project.Citas.models.primariasCompuestas.rolPermisoId;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "asignacionPermisos")
public class asignacionPermisosModel {
    
    @EmbeddedId
    private rolPermisoId id;


    @ManyToOne
    @MapsId("idRol")
    @JoinColumn(name = "rol_id") 
    private rolModel idRol;

    @ManyToOne
    @MapsId("idPermiso")
    @JoinColumn(name = "permisos_id") 
    private permisosModel idPermiso;


    
    public rolModel getIdRol() {
        return idRol;
    }

    public void setIdRol(rolModel idRol) {
        this.idRol = idRol;
    }

    public permisosModel getIdPermiso() {
        return idPermiso;
    }

    public void setIdPermiso(permisosModel idPermiso) {
        this.idPermiso = idPermiso;
    }

    public rolPermisoId getId() {
        return id;
    }

    public void setId(rolPermisoId id) {
        this.id = id;
    }

    
}
