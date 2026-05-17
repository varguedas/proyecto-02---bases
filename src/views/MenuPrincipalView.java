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
    private Usuario usuario;

    public MenuPrincipalView() {

        usuario = AuthController.getUsuarioAutenticado();

        setTitle("Proyecto AIR - Menú Principal | Rol: " + usuario.getRol());
        setSize(900, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton btnAsambleistas = new JButton("Listar Asambleístas");
        JButton btnNormativas = new JButton("Listar Normativas");
        JButton btnEditarNormativa = new JButton("Editar Normativa");
        JButton btnArbolNormativo = new JButton("Ver Árbol Normativo");

        areaResultado = new JTextArea();
        areaResultado.setEditable(false);

        JPanel panelBotones = new JPanel();

        panelBotones.add(btnAsambleistas);

        if (!usuario.getRol().equalsIgnoreCase("ASAMBLEISTA")) {
            panelBotones.add(btnNormativas);
            panelBotones.add(btnArbolNormativo);
        }

        if (puedeEditarNormativa()) {
            panelBotones.add(btnEditarNormativa);
        }

        add(panelBotones, BorderLayout.NORTH);
        add(new JScrollPane(areaResultado), BorderLayout.CENTER);

        btnAsambleistas.addActionListener(e -> listarAsambleistas());
        btnNormativas.addActionListener(e -> listarNormativas());
        btnEditarNormativa.addActionListener(e -> editarNormativa());
        btnArbolNormativo.addActionListener(e -> abrirArbolNormativo());

        areaResultado.setText(
            "Bienvenido: " + usuario.getNombre() + "\n" +
            "Rol activo: " + usuario.getRol() + "\n\n" +
            "Seleccione una opción del menú superior."
        );
    }

    private boolean puedeEditarNormativa() {

        String rol = usuario.getRol();

        return rol.equalsIgnoreCase("ADMIN") ||
               rol.equalsIgnoreCase("SECRETARIA");
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

    private void editarNormativa() {

        if (!puedeEditarNormativa()) {

            JOptionPane.showMessageDialog(
                this,
                "Acceso denegado. Su rol no permite editar normativa."
            );

            return;
        }

        String idTexto = JOptionPane.showInputDialog(
            this,
            "Ingrese el ID de la normativa a editar:"
        );

        if (idTexto == null || idTexto.isBlank()) {
            return;
        }

        String nuevaDescripcion = JOptionPane.showInputDialog(
            this,
            "Ingrese la nueva descripción de la normativa:"
        );

        if (nuevaDescripcion == null || nuevaDescripcion.isBlank()) {

            JOptionPane.showMessageDialog(
                this,
                "La descripción no puede estar vacía."
            );

            return;
        }

        try {

            int idNormativa = Integer.parseInt(idTexto);

            NormativaDAO dao = new NormativaDAO();

            boolean actualizada = dao.actualizarDescripcionNormativa(
                idNormativa,
                nuevaDescripcion
            );

            if (actualizada) {

                JOptionPane.showMessageDialog(
                    this,
                    "Normativa actualizada correctamente."
                );

                listarNormativas();

            } else {

                JOptionPane.showMessageDialog(
                    this,
                    "No se encontró la normativa indicada."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                this,
                "El ID debe ser un número válido."
            );
        }
    }

    private void abrirArbolNormativo() {

        NormativaTreeView normativaTreeView = new NormativaTreeView();
        normativaTreeView.setVisible(true);
    }
}