package models;

public class AsistenciaSesion {

    private int idSesion;
    private int idAsambleista;
    private String estadoAsistencia;

    public AsistenciaSesion(
        int idSesion,
        int idAsambleista,
        String estadoAsistencia
    ) {
        this.idSesion = idSesion;
        this.idAsambleista = idAsambleista;
        this.estadoAsistencia = estadoAsistencia;
    }

    public int getIdSesion() {
        return idSesion;
    }

    public int getIdAsambleista() {
        return idAsambleista;
    }

    public String getEstadoAsistencia() {
        return estadoAsistencia;
    }
}