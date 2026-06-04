package controllers;

import models.AsistenciaSesion;
import models.AsistenciaSesionDAO;

public class AsistenciaSesionController {

    private AsistenciaSesionDAO asistenciaDAO;

    public AsistenciaSesionController() {
        asistenciaDAO = new AsistenciaSesionDAO();
    }

    public boolean registrarAsistencia(
        int idSesion,
        int idAsambleista,
        String estadoAsistencia
    ) {

        AsistenciaSesion asistencia = new AsistenciaSesion(
            idSesion,
            idAsambleista,
            estadoAsistencia
        );

        return asistenciaDAO.registrarAsistencia(asistencia);
    }
}