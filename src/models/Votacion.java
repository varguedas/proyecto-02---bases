package models;

public class Votacion {

    private int idSesion;
    private int idPropuesta;

    private int votosFavor;
    private int votosContra;
    private int abstenciones;

    private String resultado;

    public Votacion(
        int idSesion,
        int idPropuesta,
        int votosFavor,
        int votosContra,
        int abstenciones,
        String resultado
    ) {
        this.idSesion = idSesion;
        this.idPropuesta = idPropuesta;
        this.votosFavor = votosFavor;
        this.votosContra = votosContra;
        this.abstenciones = abstenciones;
        this.resultado = resultado;
    }

    public int getIdSesion() {
        return idSesion;
    }

    public int getIdPropuesta() {
        return idPropuesta;
    }

    public int getVotosFavor() {
        return votosFavor;
    }

    public int getVotosContra() {
        return votosContra;
    }

    public int getAbstenciones() {
        return abstenciones;
    }

    public String getResultado() {
        return resultado;
    }
}