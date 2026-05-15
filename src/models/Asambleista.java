package models;

public class Asambleista {

    private int idAsambleista;
    private String nombreCompleto;
    private String cedula;
    private String sector;
    private String estado;

    public Asambleista(
        int idAsambleista,
        String nombreCompleto,
        String cedula,
        String sector,
        String estado
    ) {

        this.idAsambleista = idAsambleista;
        this.nombreCompleto = nombreCompleto;
        this.cedula = cedula;
        this.sector = sector;
        this.estado = estado;
    }

    public int getIdAsambleista() {
        return idAsambleista;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public String getCedula() {
        return cedula;
    }

    public String getSector() {
        return sector;
    }

    public String getEstado() {
        return estado;
    }
}