package views;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import controllers.AuthController;
import models.Usuario;
import models.Asambleista;
import models.AsambleistaDAO;
import models.Normativa;
import models.NormativaDAO;

public class MenuPrincipalView extends JFrame {

    private JTextArea areaResultado;

    public MenuPrincipalView() {

        Usuario usuario = AuthController.getUsuarioAutenticado();

        setTitle("Proyecto AIR - Menú Principal | Rol: " + usuario.getRol());
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton btnAsambleistas = new JButton("Listar Asambleístas");
        JButton btnNormativas = new JButton("Listar Normativas");

        areaResultado = new JTextArea();
        areaResultado.setEditable(false);

        JPanel panelBotones = new JPanel();

        panelBotones.add(btnAsambleistas);

        if (!usuario.getRol().equalsIgnoreCase("ASAMBLEISTA")) {
            panelBotones.add(btnNormativas);
        }

        add(panelBotones, BorderLayout.NORTH);
        add(new JScrollPane(areaResultado), BorderLayout.CENTER);

        btnAsambleistas.addActionListener(e -> listarAsambleistas());
        btnNormativas.addActionListener(e -> listarNormativas());

        areaResultado.setText(
            "Bienvenido: " + usuario.getNombre() + "\n" +
            "Rol activo: " + usuario.getRol() + "\n\n" +
            "Seleccione una opción del menú superior."
        );
    }

    private void listarAsambleistas() {

        AsambleistaDAO dao = new AsambleistaDAO();
        List<Asambleista> lista = dao.listarAsambleistas();

        areaResultado.setText("ASAMBLEÍSTAS REGISTRADOS\n\n");

        for (Asambleista a : lista) {
            areaResultado.append(
                a.getIdAsambleista() + " | " +
                a.getNombreCompleto() + " | " +
                a.getCedula() + " | " +
                a.getSector() + " | " +
                a.getEstado() + "\n"
            );
        }
    }

    private void listarNormativas() {

        NormativaDAO dao = new NormativaDAO();
        List<Normativa> lista = dao.listarNormativas();

        areaResultado.setText("NORMATIVAS REGISTRADAS\n\n");

        for (Normativa n : lista) {
            areaResultado.append(
                n.getIdNormativa() + " | " +
                n.getTitulo() + " | " +
                n.getFechaAprobacion() + "\n"
            );
        }
    }
}