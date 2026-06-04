package views;

import controllers.AsistenciaSesionController;

import javax.swing.*;
import java.awt.*;

public class AsistenciaSesionView extends JFrame {

    private JTextField txtIdSesion;
    private JTextField txtIdAsambleista;
    private JComboBox<String> comboEstado;
    private JTextArea areaResultado;

    public AsistenciaSesionView() {

        setTitle("Registro de Asistencia");
        setSize(550, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        txtIdSesion = new JTextField(10);
        txtIdAsambleista = new JTextField(10);

        comboEstado = new JComboBox<>();
        comboEstado.addItem("PRESENTE");
        comboEstado.addItem("AUSENTE");
        comboEstado.addItem("JUSTIFICADO");

        JButton btnRegistrar = new JButton("Registrar Asistencia");

        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 8, 8));

        panelFormulario.add(new JLabel("ID Sesión:"));
        panelFormulario.add(txtIdSesion);

        panelFormulario.add(new JLabel("ID Asambleísta:"));
        panelFormulario.add(txtIdAsambleista);

        panelFormulario.add(new JLabel("Estado:"));
        panelFormulario.add(comboEstado);

        panelFormulario.add(new JLabel(""));
        panelFormulario.add(btnRegistrar);

        areaResultado = new JTextArea();
        areaResultado.setEditable(false);

        add(panelFormulario, BorderLayout.NORTH);
        add(new JScrollPane(areaResultado), BorderLayout.CENTER);

        btnRegistrar.addActionListener(e -> registrarAsistencia());
    }

    private void registrarAsistencia() {

        try {

            int idSesion = Integer.parseInt(txtIdSesion.getText());
            int idAsambleista = Integer.parseInt(txtIdAsambleista.getText());
            String estado = comboEstado.getSelectedItem().toString();

            AsistenciaSesionController controller =
                new AsistenciaSesionController();

            boolean registrada = controller.registrarAsistencia(
                idSesion,
                idAsambleista,
                estado
            );

            if (registrada) {

                areaResultado.setText(
                    "Asistencia registrada correctamente.\n\n" +
                    "Sesión: " + idSesion + "\n" +
                    "Asambleísta: " + idAsambleista + "\n" +
                    "Estado: " + estado
                );

                JOptionPane.showMessageDialog(
                    this,
                    "Asistencia registrada correctamente."
                );

            } else {

                JOptionPane.showMessageDialog(
                    this,
                    "No se pudo registrar la asistencia."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                this,
                "Los ID deben ser valores numéricos."
            );
        }
    }
}