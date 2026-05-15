package controllers;

import models.Asambleista;
import models.AsambleistaDAO;
import java.util.List;

public class SecretariaController {

    private AsambleistaDAO asambleistaDAO;

    public SecretariaController() {
        asambleistaDAO = new AsambleistaDAO();
    }

    public void registrarAsambleista(
        String nombre,
        String cedula,
        String sector,
        String estado
    ) {

        Asambleista asambleista = new Asambleista(
            0,
            nombre,
            cedula,
            sector,
            estado
        );

        boolean registrado =
            asambleistaDAO.registrarAsambleista(asambleista);

        if (registrado) {

            System.out.println("Asambleísta registrado correctamente.");

        } else {

            System.out.println("No se pudo registrar el asambleísta.");
        }
    }

    public void mostrarAsambleistas() {

        List<Asambleista> lista =
            asambleistaDAO.listarAsambleistas();

        for (Asambleista a : lista) {

            System.out.println(
                a.getIdAsambleista() + " | " +
                a.getNombreCompleto() + " | " +
                a.getCedula() + " | " +
                a.getSector() + " | " +
                a.getEstado()
            );
        }
    }
}