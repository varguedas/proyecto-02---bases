package controllers;

import models.Usuario;
import models.UsuarioDAO;

public class AuthController {

    private UsuarioDAO usuarioDAO;

    public AuthController() {
        usuarioDAO = new UsuarioDAO();
    }

    public boolean login(String correo, String password) {

        Usuario usuario = usuarioDAO.autenticarUsuario(correo, password);

        if (usuario != null) {

            System.out.println("Bienvenido " + usuario.getNombre());

            return true;
        }

        System.out.println("Credenciales inválidas");

        return false;
    }
}