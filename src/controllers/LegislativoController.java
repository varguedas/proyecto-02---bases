package controllers;

import models.Normativa;
import models.NormativaDAO;
import java.time.LocalDate;
import java.util.List;

public class LegislativoController {

    private NormativaDAO normativaDAO;

    public LegislativoController() {
        normativaDAO = new NormativaDAO();
    }

    public void registrarNormativa(
        String titulo,
        String descripcion,
        LocalDate fechaAprobacion
    ) {

        Normativa normativa = new Normativa(
            0,
            titulo,
            descripcion,
            fechaAprobacion
        );

        boolean registrada = normativaDAO.registrarNormativa(normativa);

        if (registrada) {
            System.out.println("Normativa registrada correctamente.");
        } else {
            System.out.println("No se pudo registrar la normativa.");
        }
    }

    public void mostrarNormativas() {

        List<Normativa> lista = normativaDAO.listarNormativas();

        for (Normativa n : lista) {
            System.out.println(
                n.getIdNormativa() + " | " +
                n.getTitulo() + " | " +
                n.getFechaAprobacion()
            );
        }
    }
}