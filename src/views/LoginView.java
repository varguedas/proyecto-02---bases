package views;

import javax.swing.*;
import java.awt.*;
import controllers.AuthController;

public class LoginView extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JButton btnLogin;

    public LoginView() {

        setTitle("Proyecto AIR - Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));

        JLabel lblUsuario = new JLabel("Usuario:");
        txtUsuario = new JTextField();

        JLabel lblPassword = new JLabel("Contraseña:");
        txtPassword = new JPasswordField();

        btnLogin = new JButton("Iniciar Sesión");

        panel.add(lblUsuario);
        panel.add(txtUsuario);
        panel.add(lblPassword);
        panel.add(txtPassword);
        panel.add(btnLogin);

        add(panel);

        btnLogin.addActionListener(e -> login());
    }

    private void login() {

        String usuario = txtUsuario.getText();
        String password =
            new String(txtPassword.getPassword());

        AuthController authController =
            new AuthController();

        boolean acceso =
            authController.login(usuario, password);

        if (acceso) {

            JOptionPane.showMessageDialog(
                this,
                "Acceso concedido"
            );

            dispose();

            new MenuPrincipalView().setVisible(true);

        } else {

            JOptionPane.showMessageDialog(
                this,
                "Usuario o contraseña incorrectos"
            );
        }
    }
}