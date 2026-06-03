package models;

public class Votacion {

    private int idSesion;

    private int votosFavor;
    private int votosContra;
    private int abstenciones;

    private String resultado;

    public Votacion(
        int idSesion,
        int votosFavor,
        int votosContra,
        int abstenciones,
        String resultado
    ) {

        this.idSesion = idSesion;
        this.votosFavor = votosFavor;
        this.votosContra = votosContra;
        this.abstenciones = abstenciones;
        this.resultado = resultado;
    }

    public int getIdSesion() {
        return idSesion;
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

    @Override
    public String toString() {

        return
            "Sesión: " + idSesion +
            " | Favor: " + votosFavor +
            " | Contra: " + votosContra +
            " | Abstenciones: " + abstenciones +
            " | Resultado: " + resultado;
    }
}