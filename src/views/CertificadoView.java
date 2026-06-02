package views;

import controllers.CertificadoController;
import models.Certificado;

import javax.swing.*;
import java.awt.*;

public class CertificadoView extends JFrame {

    private JTextField txtIdAsambleista;
    private JTextArea areaResultado;

    public CertificadoView() {

        setTitle("Generador de Atestados");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JLabel lblId = new JLabel("ID Asambleísta:");
        txtIdAsambleista = new JTextField(10);

        JButton btnGenerar = new JButton("Generar Certificación");

        areaResultado = new JTextArea();
        areaResultado.setEditable(false);

        JPanel panelSuperior = new JPanel();
        panelSuperior.add(lblId);
        panelSuperior.add(txtIdAsambleista);
        panelSuperior.add(btnGenerar);

        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(areaResultado), BorderLayout.CENTER);

        btnGenerar.addActionListener(e -> generarCertificado());
    }

    private void generarCertificado() {

        try {

            int idAsambleista =
                Integer.parseInt(txtIdAsambleista.getText());

            CertificadoController controller =
                new CertificadoController();

            Certificado certificado =
                controller.generarCertificado(idAsambleista);

            if (certificado == null) {

                JOptionPane.showMessageDialog(
                    this,
                    "No se encontró información para generar la certificación."
                );

                return;
            }

            areaResultado.setText(
                "CERTIFICACIÓN INSTITUCIONAL\n\n" +
                "Folio: " + certificado.getFolio() + "\n" +
                "Nombre: " + certificado.getNombreAsambleista() + "\n" +
                "Cédula: " + certificado.getCedula() + "\n" +
                "Sector: " + certificado.getSector() + "\n" +
                "Puesto: " + certificado.getPuesto() + "\n" +
                "Fecha emisión: " + certificado.getFechaEmision() + "\n\n" +
                "Hash SHA-256:\n" + certificado.getHashSeguridad()
            );

            JOptionPane.showMessageDialog(
                this,
                "Certificación generada y registrada correctamente."
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                this,
                "Ingrese un ID de asambleísta válido."
            );
        }
    }
}