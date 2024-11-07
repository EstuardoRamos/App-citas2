package com.project.Citas.DTO;

import com.project.Citas.models.userModel;

public class verificarCorreoDTO {
    private String codigo;
    private userModel usuarioNuevo;
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public userModel getUsuarioNuevo() {
        return usuarioNuevo;
    }
    public void setUsuarioNuevo(userModel usuarioNuevo) {
        this.usuarioNuevo = usuarioNuevo;
    }
    

}
