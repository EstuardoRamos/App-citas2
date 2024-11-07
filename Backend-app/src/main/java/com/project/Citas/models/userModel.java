package com.project.Citas.models;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class userModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private long id;
    private String nombre;
    
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String contrasenia;
    @ManyToOne
    @JoinColumn(name = "tipo_usuario_id", referencedColumnName = "id")  
    private tipoUsuarioModel tipoUsuario;
    @Column(name = "correo_electronico", unique = true)  // Correo electrónico debe ser único
    private String correoElectronico;
    @Column(unique = true)
    private String telefono;
    @Column(unique = true)
    private String nit;
    @Column(unique = true)
    private String cui;
    private boolean a2f = false;
    @Column(name ="fecha_nacimiento",nullable = false)
    private Date fechaNacimiento;

    
    
    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public boolean isA2f() {
        return a2f;
    }

    public void setA2f(boolean a2f) {
        this.a2f = a2f;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String toString() {
        return "Nombre: " + this.nombre + " id: " + this.id;
    }

    public tipoUsuarioModel getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(tipoUsuarioModel tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
