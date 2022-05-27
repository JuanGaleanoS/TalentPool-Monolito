package com.talentpool.monolito.model;

import javax.persistence.*;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "nombres", length = 150)
    private String nombres;
    @Column(name = "apellidos", length = 150)
    private String apellidos;
    @Column(name = "tipo_identificacion", length = 3)
    private String tipoIdentificacion;
    @Column(name = "identificacion", length = 30)
    private String identificacion;
    private Integer edad;
    @Column(name = "ciudad_nacimiento", length = 50)
    private String ciudadNacimiento;
    private String foto;

    public Cliente(Integer id, String nombres, String apellidos, String tipoIdentificacion, String identificacion, Integer edad, String ciudadNacimiento, String foto) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.tipoIdentificacion = tipoIdentificacion;
        this.identificacion = identificacion;
        this.edad = edad;
        this.ciudadNacimiento = ciudadNacimiento;
        this.foto = foto;
    }

    public Cliente(String nombres, String apellidos, String tipoIdentificacion, String identificacion, Integer edad, String ciudadNacimiento, String foto) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.tipoIdentificacion = tipoIdentificacion;
        this.identificacion = identificacion;
        this.edad = edad;
        this.ciudadNacimiento = ciudadNacimiento;
        this.foto = foto;
    }

    public Cliente() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTipoIdentificacion() {
        return tipoIdentificacion;
    }

    public void setTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getCiudadNacimiento() {
        return ciudadNacimiento;
    }

    public void setCiudadNacimiento(String ciudadNacimiento) {
        this.ciudadNacimiento = ciudadNacimiento;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
