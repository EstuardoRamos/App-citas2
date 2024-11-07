package com.project.Citas.models.Citas;

import java.time.LocalDate;
import java.time.LocalTime;

import com.project.Citas.models.userModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "citas")
public class citasModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id") 
    private userModel usuario;

    @ManyToOne
    @JoinColumn(name = "servicio_id", referencedColumnName = "id") 
    private ServicioModel servicio;
    private LocalTime horaInicio;
    private LocalDate fecha;

    @ManyToOne
    @JoinColumn(name = "servidor_id", referencedColumnName = "id") 
    private ServidorModel idServidor;

    @ManyToOne
    @JoinColumn(name = "estado_cita_id", referencedColumnName = "id") 
    private estadoCitaModel estadoCita;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public userModel getUsuario() {
        return usuario;
    }
    public void setUsuario(userModel usuario) {
        this.usuario = usuario;
    }
    public ServicioModel getServicio() {
        return servicio;
    }
    public void setServicio(ServicioModel servicio) {
        this.servicio = servicio;
    }
    public LocalTime getHoraInicio() {
        return horaInicio;
    }
    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }
    
    public ServidorModel getIdServidor() {
        return idServidor;
    }
    public void setIdServidor(ServidorModel idServidor) {
        this.idServidor = idServidor;
    }
    public estadoCitaModel getEstadoCita() {
        return estadoCita;
    }
    public void setEstadoCita(estadoCitaModel estadoCita) {
        this.estadoCita = estadoCita;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    

}
