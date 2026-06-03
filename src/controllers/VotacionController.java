package controllers;

import models.Votacion;
import models.VotacionDAO;

public class VotacionController {

    private VotacionDAO votacionDAO;

    public VotacionController() {
        votacionDAO = new VotacionDAO();
    }

    public String registrarVotacion(
        int idSesion,
        int presentes,
        int minimoQuorum,
        int votosFavor,
        int votosContra,
        int abstenciones
    ) {

        String resultado = votacionDAO.calcularResultado(
            presentes,
            minimoQuorum,
            votosFavor,
            votosContra
        );

        Votacion votacion = new Votacion(
            idSesion,
            votosFavor,
            votosContra,
            abstenciones,
            resultado
        );

        boolean registrada =
            votacionDAO.registrarVotacion(votacion);

        if (registrada) {
            return resultado;
        }

        return "ERROR_REGISTRO";
    }
}