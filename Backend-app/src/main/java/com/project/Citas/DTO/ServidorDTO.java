package com.project.Citas.DTO;

import java.util.ArrayList;

import com.project.Citas.models.Citas.ServicioModel;
import com.project.Citas.models.Citas.ServidorModel;

public class ServidorDTO {
    private ServidorModel servidor;
    private ArrayList<ServicioModel> servicios;
    private ArrayList<horarioDTO> horarios;
    public ArrayList<horarioDTO> getHorarios() {
        return horarios;
    }
    public void setHorarios(ArrayList<horarioDTO> horarios) {
        this.horarios = horarios;
    }
    public ServidorModel getServidor() {
        return servidor;
    }
    public void setServidor(ServidorModel servidor) {
        this.servidor = servidor;
    }
    public ArrayList<ServicioModel> getServicios() {
        return servicios;
    }
    public void setServicios(ArrayList<ServicioModel> servicios) {
        this.servicios = servicios;
    }

    
    

}
