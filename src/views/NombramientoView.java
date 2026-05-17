package views;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

import models.Asambleista;
import models.AsambleistaDAO;
import models.Nombramiento;
import models.NombramientoDAO;

public class NombramientoView extends JFrame {

    private JComboBox<String> comboAsambleistas;
    private JComboBox<String> comboSectores;
    private JTextField txtFechaInicio;
    private JTextField txtFechaFin;
    private JTextArea areaDetalle;

    public NombramientoView() {

        setTitle("Proyecto AIR - Maestro Detalle de Nombramientos");
        setSize(850, 560);
        setLocationRelativeTo(null);

        comboAsambleistas = new JComboBox<>();
        comboSectores = new JComboBox<>();
        txtFechaInicio = new JTextField(10);
        txtFechaFin = new JTextField(10);
        areaDetalle = new JTextArea();
        areaDetalle.setEditable(false);

        JButton btnCargarDetalle = new JButton("Cargar Nombramientos");
        JButton btnAgregarNombramiento = new JButton("Agregar Nombramiento");

        cargarAsambleistas();
        cargarSectores();

        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 8, 8));

        panelFormulario.add(new JLabel("Asambleísta:"));
        panelFormulario.add(comboAsambleistas);

        panelFormulario.add(new JLabel("Sector:"));
        panelFormulario.add(comboSectores);

        panelFormulario.add(new JLabel("Fecha inicio (YYYY-MM-DD):"));
        panelFormulario.add(txtFechaInicio);

        panelFormulario.add(new JLabel("Fecha fin (YYYY-MM-DD):"));
        panelFormulario.add(txtFechaFin);

        panelFormulario.add(btnCargarDetalle);
        panelFormulario.add(btnAgregarNombramiento);

        add(panelFormulario, BorderLayout.NORTH);
        add(new JScrollPane(areaDetalle), BorderLayout.CENTER);

        btnCargarDetalle.addActionListener(e -> cargarNombramientos());
        btnAgregarNombramiento.addActionListener(e -> agregarNombramiento());
    }

    private void cargarAsambleistas() {

        AsambleistaDAO dao = new AsambleistaDAO();
        List<Asambleista> lista = dao.listarAsambleistas();

        comboAsambleistas.removeAllItems();

        for (Asambleista a : lista) {
            comboAsambleistas.addItem(
                a.getIdAsambleista() + " - " + a.getNombreCompleto()
            );
        }
    }

    private void cargarSectores() {

        AsambleistaDAO dao = new AsambleistaDAO();
        List<String> sectores = dao.listarSectores();

        comboSectores.removeAllItems();

        for (String sector : sectores) {
            comboSectores.addItem(sector);
        }

        if (comboSectores.getItemCount() == 0) {
            comboSectores.addItem("Docente");
            comboSectores.addItem("Administrativo");
            comboSectores.addItem("Estudiantil");
        }
    }

    private int obtenerIdAsambleistaSeleccionado() {

        String seleccion = comboAsambleistas.getSelectedItem().toString();

        String idTexto = seleccion.split(" - ")[0];

        return Integer.parseInt(idTexto);
    }

    private void cargarNombramientos() {

        if (comboAsambleistas.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(
                this,
                "Debe seleccionar un asambleísta."
            );
            return;
        }

        int idAsambleista = obtenerIdAsambleistaSeleccionado();

        NombramientoDAO dao = new NombramientoDAO();
        List<Nombramiento> nombramientos =
            dao.listarPorAsambleista(idAsambleista);

        areaDetalle.setText("NOMBRAMIENTOS DEL ASAMBLEÍSTA\n\n");

        if (nombramientos.isEmpty()) {
            areaDetalle.append(
                "Este asambleísta no tiene nombramientos registrados.\n"
            );
            return;
        }

        for (Nombramiento n : nombramientos) {
            areaDetalle.append(n.toString() + "\n");
        }
    }

    private void agregarNombramiento() {

        if (comboAsambleistas.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(
                this,
                "Debe seleccionar un asambleísta."
            );
            return;
        }

        if (comboSectores.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(
                this,
                "Debe seleccionar un sector."
            );
            return;
        }

        try {

            int idAsambleista = obtenerIdAsambleistaSeleccionado();

            String sector = comboSectores
                .getSelectedItem()
                .toString();

            LocalDate fechaInicio =
                LocalDate.parse(txtFechaInicio.getText().trim());

            LocalDate fechaFin = null;

            if (!txtFechaFin.getText().trim().isBlank()) {
                fechaFin = LocalDate.parse(txtFechaFin.getText().trim());
            }

            NombramientoDAO dao = new NombramientoDAO();

            boolean registrado = dao.registrarNombramiento(
                idAsambleista,
                sector,
                fechaInicio,
                fechaFin
            );

            if (registrado) {

                JOptionPane.showMessageDialog(
                    this,
                    "Nombramiento registrado correctamente."
                );

                cargarNombramientos();

            } else {

                JOptionPane.showMessageDialog(
                    this,
                    "No se pudo registrar el nombramiento.\n" +
                    dao.getUltimoError()
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                this,
                "Revise los datos ingresados. " +
                "Las fechas deben tener formato YYYY-MM-DD."
            );
        }
    }
}