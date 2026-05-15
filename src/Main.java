import controllers.LegislativoController;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        LegislativoController legislativoController =
            new LegislativoController();

        legislativoController.registrarNormativa(
            "Reglamento AIR de prueba",
            "Normativa registrada desde Java para prueba del módulo legislativo.",
            LocalDate.of(2026, 5, 14)
        );

        legislativoController.mostrarNormativas();
    }
}