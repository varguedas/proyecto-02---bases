import controllers.AuthController;

public class Main {

    public static void main(String[] args) {

        AuthController authController = new AuthController();

        boolean autenticado = authController.login(
            "admin@air.ac.cr",
            "admin123"
        );

        if (autenticado) {
            System.out.println("Acceso concedido al sistema.");
        } else {
            System.out.println("Acceso denegado.");
        }
    }
}