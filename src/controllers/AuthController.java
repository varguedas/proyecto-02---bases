package controllers;

import models.Usuario;
import models.UsuarioDAO;

public class AuthController {

    private UsuarioDAO usuarioDAO;
    private static Usuario usuarioAutenticado;

    public AuthController() {
        usuarioDAO = new UsuarioDAO();
    }

    public boolean login(String correo, String password) {

        Usuario usuario = usuarioDAO.autenticarUsuario(correo, password);

        if (usuario != null) {

            usuarioAutenticado = usuario;

            System.out.println(
                "Bienvenido " + usuario.getNombre() +
                " | Rol: " + usuario.getRol()
            );

            return true;
        }

        System.out.println("Credenciales inválidas");

        return false;
    }

    public static Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }
}