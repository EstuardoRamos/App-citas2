package com.project.Citas.DTO;

import java.util.ArrayList;

import com.project.Citas.models.permisosModel;
import com.project.Citas.models.rolModel;

public class rolPermisosRequest {
    private rolModel rol;
    private ArrayList<permisosModel> permisos;

    // Getters y setters
    public rolModel getRol() {
        return rol;
    }

    public void setRol(rolModel rol) {
        this.rol = rol;
    }

    public ArrayList<permisosModel> getPermisos() {
        return permisos;
    }

    public void setPermisos(ArrayList<permisosModel> permisos) {
        this.permisos = permisos;
    }
}
