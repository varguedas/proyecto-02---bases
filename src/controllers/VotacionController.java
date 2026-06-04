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
        int idPropuesta,
        int minimoQuorum,
        int votosFavor,
        int votosContra,
        int abstenciones
    ) {

        int presentes =
            votacionDAO.contarPresentes(idSesion);

        String resultado =
            votacionDAO.calcularResultado(
                presentes,
                minimoQuorum,
                votosFavor,
                votosContra
            );

        Votacion votacion =
            new Votacion(
                idSesion,
                idPropuesta,
                votosFavor,
                votosContra,
                abstenciones,
                resultado
            );

        boolean registrada =
            votacionDAO.registrarVotacion(votacion);

        if (registrada) {

            votacionDAO.actualizarEstadoPropuesta(
                idPropuesta,
                resultado
            );

            return resultado + " | Presentes reales: " + presentes;
        }

        return "ERROR_REGISTRO";
    }
}