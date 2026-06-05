package views;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

import models.Sesion;
import models.SesionDAO;

public class SesionView extends JFrame {

    private JTextField txtIdTipoModalidad;
    private JTextField txtIdTipoSesion;
    private JTextField txtNumeroSesion;
    private JTextField txtFecha;
    private JTextField txtLinkActa;
    private JTextField txtQuorum;
    private JTextArea areaResultado;

    public SesionView() {

        setTitle("Proyecto AIR - Gestión de Sesiones");
        setSize(900, 520);
        setMinimumSize(new Dimension(850, 480));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        txtIdTipoModalidad = new JTextField("1", 10);
        txtIdTipoSesion = new JTextField("1", 10);
        txtNumeroSesion = new JTextField(15);
        txtFecha = new JTextField("2026-06-05", 12);
        txtLinkActa = new JTextField("Acta pendiente", 20);
        txtQuorum = new JTextField("1", 10);

        areaResultado = new JTextArea();
        areaResultado.setEditable(false);

        JButton btnCrearSesion = new JButton("Crear Sesión");
        JButton btnListarSesiones = new JButton("Listar Sesiones");

        JPanel panelFormulario = new JPanel(new GridLayout(7, 2, 8, 8));

        panelFormulario.add(new JLabel("ID tipo modalidad:"));
        panelFormulario.add(txtIdTipoModalidad);

        panelFormulario.add(new JLabel("ID tipo sesión:"));
        panelFormulario.add(txtIdTipoSesion);

        panelFormulario.add(new JLabel("Número de sesión:"));
        panelFormulario.add(txtNumeroSesion);

        panelFormulario.add(new JLabel("Fecha (YYYY-MM-DD):"));
        panelFormulario.add(txtFecha);

        panelFormulario.add(new JLabel("Link o referencia del acta:"));
        panelFormulario.add(txtLinkActa);

        panelFormulario.add(new JLabel("Quórum requerido:"));
        panelFormulario.add(txtQuorum);

        panelFormulario.add(btnCrearSesion);
        panelFormulario.add(btnListarSesiones);

        add(panelFormulario, BorderLayout.NORTH);
        add(new JScrollPane(areaResultado), BorderLayout.CENTER);

        btnCrearSesion.addActionListener(e -> crearSesion());
        btnListarSesiones.addActionListener(e -> listarSesiones());
    }

    private void crearSesion() {

        try {

            int idTipoModalidad =
                Integer.parseInt(txtIdTipoModalidad.getText().trim());

            int idTipoSesion =
                Integer.parseInt(txtIdTipoSesion.getText().trim());

            String numeroSesion =
                txtNumeroSesion.getText().trim();

            LocalDate fecha =
                LocalDate.parse(txtFecha.getText().trim());

            String linkActa =
                txtLinkActa.getText().trim();

            int quorum =
                Integer.parseInt(txtQuorum.getText().trim());

            if (numeroSesion.isBlank()) {

                JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar el número de sesión."
                );

                return;
            }

            Sesion sesion = new Sesion(
                0,
                idTipoModalidad,
                idTipoSesion,
                numeroSesion,
                fecha,
                linkActa,
                quorum
            );

            SesionDAO dao = new SesionDAO();

            boolean creada = dao.crearSesion(sesion);

            if (creada) {

                JOptionPane.showMessageDialog(
                    this,
                    "Sesión registrada correctamente."
                );

                listarSesiones();

            } else {

                JOptionPane.showMessageDialog(
                    this,
                    "No se pudo registrar la sesión."
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                this,
                "Revise los datos ingresados. " +
                "La fecha debe tener formato YYYY-MM-DD."
            );
        }
    }

    private void listarSesiones() {

        SesionDAO dao = new SesionDAO();

        List<Sesion> sesiones = dao.listarSesiones();

        areaResultado.setText("SESIONES REGISTRADAS\n\n");

        if (sesiones.isEmpty()) {

            areaResultado.append(
                "No hay sesiones registradas en el sistema.\n"
            );

            return;
        }

        for (Sesion sesion : sesiones) {
            areaResultado.append(sesion.toString() + "\n");
        }
    }
}