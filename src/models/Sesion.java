package models;


import java.time.LocalDate;

public class Sesion {

    private int idSesion;
    private String titulo;
    private LocalDate fecha;
    private String descripcion;
    private String estado;

    public Sesion(int idSesion, String titulo, LocalDate fecha, String descripcion, String estado) {
        this.idSesion = idSesion;
        this.titulo = titulo;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public int getIdSesion() {
        return idSesion;
    }

    public String getTitulo() {
        return titulo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getEstado() {
        return estado;
    }
}