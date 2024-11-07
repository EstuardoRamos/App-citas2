package com.project.Citas.models.primariasCompuestas;

import java.io.Serializable;

public class rolPermisoId implements Serializable{
    private Long idRol;
    private Long idPermiso;
    public rolPermisoId(){
        
    }
    public rolPermisoId(Long idRol, Long idPermiso) {
        this.idRol = idRol;
        this.idPermiso = idPermiso;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        rolPermisoId that = (rolPermisoId) o;
        if (!idRol.equals(that.idRol)) return false;
        return idPermiso.equals(that.idPermiso);
    }

    @Override
    public int hashCode() {
        int result = idRol.hashCode();
        result = 31 * result + idPermiso.hashCode();
        return result;
    }
    public Long getIdRol() {
        return idRol;
    }
    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }
    public Long getIdPermiso() {
        return idPermiso;
    }
    public void setIdPermiso(Long idPermiso) {
        this.idPermiso = idPermiso;
    }
}
