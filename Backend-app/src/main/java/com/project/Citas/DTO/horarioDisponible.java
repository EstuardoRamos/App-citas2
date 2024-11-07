package com.project.Citas.DTO;

import java.time.LocalTime;

import com.project.Citas.models.Citas.ServidorModel;

public class horarioDisponible {
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private ServidorModel servidor;
    
    public horarioDisponible() {
    }
    public horarioDisponible(LocalTime horaInicio, LocalTime horaFin, ServidorModel servidor) {
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.servidor = servidor;
    }
    public LocalTime getHoraInicio() {
        return horaInicio;
    }
    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }
    public LocalTime getHoraFin() {
        return horaFin;
    }
    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }
    public ServidorModel getServidor() {
        return servidor;
    }
    public void setServidor(ServidorModel servidor) {
        this.servidor = servidor;
    }

    

}
