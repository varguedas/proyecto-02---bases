package models;

import java.time.LocalDate;

public class Sesion {

    private int idSesion;
    private int idTipoModalidad;
    private int idTipoSesion;
    private String numeroSesion;
    private LocalDate fecha;
    private String linkActa;
    private int quorumRequerido;

    public Sesion(
        int idSesion,
        int idTipoModalidad,
        int idTipoSesion,
        String numeroSesion,
        LocalDate fecha,
        String linkActa,
        int quorumRequerido
    ) {
        this.idSesion = idSesion;
        this.idTipoModalidad = idTipoModalidad;
        this.idTipoSesion = idTipoSesion;
        this.numeroSesion = numeroSesion;
        this.fecha = fecha;
        this.linkActa = linkActa;
        this.quorumRequerido = quorumRequerido;
    }

    public int getIdSesion() {
        return idSesion;
    }

    public int getIdTipoModalidad() {
        return idTipoModalidad;
    }

    public int getIdTipoSesion() {
        return idTipoSesion;
    }

    public String getNumeroSesion() {
        return numeroSesion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getLinkActa() {
        return linkActa;
    }

    public int getQuorumRequerido() {
        return quorumRequerido;
    }

    @Override
    public String toString() {
        return idSesion + " | " +
               numeroSesion + " | " +
               fecha + " | Quórum: " +
               quorumRequerido + " | Acta: " +
               linkActa;
    }
}