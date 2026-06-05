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

    private JTextField txtBusquedaAsambleista;
    private JComboBox<String> comboSectores;

    public MenuPrincipalView() {

        usuario = AuthController.getUsuarioAutenticado();

        setTitle("Proyecto AIR - Menú Principal | Rol: " + usuario.getRol());
        setSize(1300, 650);
        setMinimumSize(new Dimension(1150, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton btnAsambleistas = new JButton("Listar Asambleístas");
        JButton btnBuscarAsambleistas = new JButton("Buscar Asambleístas");
        JButton btnNormativas = new JButton("Listar Normativas");
        JButton btnEditarNormativa = new JButton("Editar Normativa");
        JButton btnArbolNormativo = new JButton("Ver Árbol Normativo");
        JButton btnNombramientos = new JButton("Gestionar Nombramientos");
        JButton btnCertificados = new JButton("Generar Atestado");
        JButton btnVotaciones = new JButton("Registrar Votación");
        JButton btnAsistencia = new JButton("Registrar Asistencia");
        JButton btnBitacora = new JButton("Ver Bitácora");

        txtBusquedaAsambleista = new JTextField(18);
        comboSectores = new JComboBox<>();

        cargarSectores();

        areaResultado = new JTextArea();
        areaResultado.setEditable(false);

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelAdministracion = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel panelSuperior = new JPanel(new GridLayout(2, 1));

        panelBusqueda.add(btnAsambleistas);
        panelBusqueda.add(new JLabel("Buscar:"));
        panelBusqueda.add(txtBusquedaAsambleista);
        panelBusqueda.add(new JLabel("Sector:"));
        panelBusqueda.add(comboSectores);
        panelBusqueda.add(btnBuscarAsambleistas);

        if (!usuario.getRol().equalsIgnoreCase("ASAMBLEISTA")) {
            panelAdministracion.add(btnNormativas);
            panelAdministracion.add(btnArbolNormativo);
        }

        if (puedeEditarNormativa()) {
            panelAdministracion.add(btnEditarNormativa);
        }

       if (puedeGestionarNombramientos()) {
        panelAdministracion.add(btnNombramientos);
        panelAdministracion.add(btnCertificados);
        panelAdministracion.add(btnVotaciones);
        panelAdministracion.add(btnAsistencia);
    }

    if (puedeVerBitacora()) {
        panelAdministracion.add(btnBitacora);
    }

    panelSuperior.add(panelBusqueda);
    panelSuperior.add(panelAdministracion);

    add(panelSuperior, BorderLayout.NORTH);

        panelSuperior.add(panelBusqueda);
        panelSuperior.add(panelAdministracion);

        add(panelSuperior, BorderLayout.NORTH);

    
        add(new JScrollPane(areaResultado), BorderLayout.CENTER);

        btnAsambleistas.addActionListener(e -> listarAsambleistas());
        btnBuscarAsambleistas.addActionListener(e -> buscarAsambleistas());
        btnNormativas.addActionListener(e -> listarNormativas());
        btnEditarNormativa.addActionListener(e -> editarNormativa());
        btnArbolNormativo.addActionListener(e -> abrirArbolNormativo());
        btnNombramientos.addActionListener(e -> abrirNombramientos());
        btnAsistencia.addActionListener(e -> abrirAsistencia());
        btnBitacora.addActionListener(e -> abrirBitacora());
        btnVotaciones.addActionListener(e -> abrirVotaciones());

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

    private boolean puedeGestionarNombramientos() {

    String rol = usuario.getRol();

    return rol.equalsIgnoreCase("ADMIN") ||
           rol.equalsIgnoreCase("SECRETARIA");
    }


    private boolean puedeVerBitacora() {

        String rol = usuario.getRol();

        return rol.equalsIgnoreCase("ADMIN") ||
                rol.equalsIgnoreCase("SECRETARIA");
    }

    private void cargarSectores() {

        AsambleistaDAO dao = new AsambleistaDAO();

        comboSectores.addItem("TODOS");

        List<String> sectores = dao.listarSectores();

        for (String sector : sectores) {
            comboSectores.addItem(sector);
        }
    }

    private void listarAsambleistas() {

        AsambleistaDAO dao = new AsambleistaDAO();
        List<Asambleista> lista = dao.listarAsambleistas();

        mostrarAsambleistas(lista, "ASAMBLEÍSTAS REGISTRADOS");
    }

    private void buscarAsambleistas() {

        String textoBusqueda = txtBusquedaAsambleista.getText();
        String sectorSeleccionado =
            comboSectores.getSelectedItem().toString();

        AsambleistaDAO dao = new AsambleistaDAO();

        List<Asambleista> lista = dao.buscarAsambleistas(
            textoBusqueda,
            sectorSeleccionado
        );

        mostrarAsambleistas(lista, "RESULTADOS DE BÚSQUEDA");
    }

    private void mostrarAsambleistas(
        List<Asambleista> lista,
        String titulo
    ) {

        areaResultado.setText(titulo + "\n\n");

        if (lista.isEmpty()) {

            areaResultado.append(
                "No se encontraron asambleístas con los criterios indicados.\n"
            );

            return;
        }

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



    private void abrirVotaciones() {

        VotacionView votacionView = new VotacionView();
        votacionView.setVisible(true);
    }

    private void abrirAsistencia() {

    AsistenciaSesionView asistenciaView =
        new AsistenciaSesionView();

    asistenciaView.setVisible(true);
    }

    private void abrirBitacora() {

        BitacoraAuditoriaView bitacoraView =
                new BitacoraAuditoriaView();

        bitacoraView.setVisible(true);
    }

    private void abrirNombramientos() {

        NombramientoView nombramientoView =
                new NombramientoView();

        nombramientoView.setVisible(true);
    }
}