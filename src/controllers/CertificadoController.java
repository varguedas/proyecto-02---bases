package controllers;

import models.Certificado;
import models.CertificadoDAO;

public class CertificadoController {

    private CertificadoDAO certificadoDAO;

    public CertificadoController() {
        certificadoDAO = new CertificadoDAO();
    }

    public Certificado generarCertificado(
        int idAsambleista
    ) {

        return certificadoDAO
            .generarCertificadoPorAsambleista(
                idAsambleista
            );
    }
}