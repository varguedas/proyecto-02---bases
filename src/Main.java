import controllers.SecretariaController;

public class Main {

    public static void main(String[] args) {

        SecretariaController secretariaController =
            new SecretariaController();

        secretariaController.registrarAsambleista(
            "Ana Ruiz",
            "123456789",
            "Docente",
            "ACTIVO"
        );

        secretariaController.mostrarAsambleistas();
    }
}