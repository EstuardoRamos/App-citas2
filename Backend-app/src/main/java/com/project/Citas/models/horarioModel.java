package com.project.Citas.models;
import java.time.DayOfWeek;
import java.time.LocalTime;
import com.project.Citas.models.Citas.ServidorModel;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "horario")
public class horarioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    private DayOfWeek dia;
    
    private LocalTime horaInicio;
    private LocalTime horaFin;
    
    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private EmpresaModel empresa;

    @ManyToOne
    @JoinColumn(name = "servidor_id")
    private ServidorModel servidor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayOfWeek getDia() {
        return dia;
    }

    public void setDia(DayOfWeek dia) {
        this.dia = dia;
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

    public EmpresaModel getEmpresa() {
        return empresa;
    }

    public void setEmpresa(EmpresaModel empresa) {
        this.empresa = empresa;
    }

    public ServidorModel getServidor() {
        return servidor;
    }

    public void setServidor(ServidorModel servidor) {
        this.servidor = servidor;
    }


    
}
