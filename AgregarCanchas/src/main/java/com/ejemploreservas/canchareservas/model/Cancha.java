package com.ejemploreservas.canchareservas.model;

public class Cancha {

    private Long id;
    private String nombre;
    private String tipo;
    private Long precioPorHora;
    private String horaInicio;
    private String horaFin;

    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public Long getPrecioPorHora() {
        return precioPorHora;
    }
    public void setPrecioPorHora(Long precioPorHora) {
        this.precioPorHora = precioPorHora;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }
    
    public Cancha() {
    }

    public Cancha(Long id, String nombre, String tipo, Long precioPorHora, String horaInicio, String tHoraFin) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.precioPorHora = precioPorHora;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }
}
