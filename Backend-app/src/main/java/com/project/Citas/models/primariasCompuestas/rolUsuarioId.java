package com.project.Citas.models.primariasCompuestas;

import java.io.Serializable;

public class rolUsuarioId implements Serializable{
    private Long idUsuario;
    private Long idRol;
    public rolUsuarioId(){
        
    }
    public rolUsuarioId(Long idUsuario, Long idRol) {
        this.idUsuario = idUsuario;
        this.idRol = idRol;
    }
    public Long getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
    public Long getIdRol() {
        return idRol;
    }
    public void setIdRol(Long idRol) {
        this.idRol = idRol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        rolUsuarioId that = (rolUsuarioId) o;
        if (!idRol.equals(that.idRol)) return false;
        return idUsuario.equals(that.idUsuario);
    }

    @Override
    public int hashCode() {
        int result = idUsuario.hashCode();
        result = 31 * result + idRol.hashCode();
        return result;
    }
}
