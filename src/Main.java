import models.Sesion;
import models.SesionDAO;
import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        SesionDAO sesionDAO = new SesionDAO();

        Sesion nuevaSesion = new Sesion(
            0,
            "Sesión ordinaria AIR-001-2026",
            LocalDate.of(2026, 5, 14),
            "Sesión inicial de prueba registrada desde Java",
            "ACTIVA"
        );

        boolean creada = sesionDAO.crearSesion(nuevaSesion);

        if (creada) {
            System.out.println("Sesión creada correctamente.");
        }

        List<Sesion> sesiones = sesionDAO.listarSesiones();

        for (Sesion sesion : sesiones) {
            System.out.println(
                sesion.getIdSesion() + " | " +
                sesion.getTitulo() + " | " +
                sesion.getFecha() + " | " +
                sesion.getEstado()
            );
        }
    }
}