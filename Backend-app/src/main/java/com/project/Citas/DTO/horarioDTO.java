package com.project.Citas.DTO;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class horarioDTO {
    private DayOfWeek dia;
    private LocalTime horaInicio;
    public DayOfWeek getDia() {
        return dia;
    }
    public void setDia(DayOfWeek dia) {
        this.dia = dia;
    }
    private LocalTime horaFin;
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

    
}
