package models;

import java.time.LocalDate;

public class Nombramiento {

    private int idNombramiento;
    private int idAsambleista;
    private String sector;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;

    public Nombramiento(
        int idNombramiento,
        int idAsambleista,
        String sector,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        String estado
    ) {
        this.idNombramiento = idNombramiento;
        this.idAsambleista = idAsambleista;
        this.sector = sector;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    public int getIdNombramiento() {
        return idNombramiento;
    }

    public int getIdAsambleista() {
        return idAsambleista;
    }

    public String getSector() {
        return sector;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return idNombramiento + " | " +
               sector + " | " +
               fechaInicio + " | " +
               fechaFin + " | " +
               estado;
    }
}