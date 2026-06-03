package views;

import controllers.VotacionController;

import javax.swing.*;
import java.awt.*;

public class VotacionView extends JFrame {

    private JTextField txtIdSesion;
    private JTextField txtPresentes;
    private JTextField txtMinimoQuorum;
    private JTextField txtFavor;
    private JTextField txtContra;
    private JTextField txtAbstenciones;
    private JTextArea areaResultado;

    public VotacionView() {

        setTitle("Registro de Votaciones");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        txtIdSesion = new JTextField(10);
        txtPresentes = new JTextField(10);
        txtMinimoQuorum = new JTextField(10);
        txtFavor = new JTextField(10);
        txtContra = new JTextField(10);
        txtAbstenciones = new JTextField(10);

        JButton btnRegistrar = new JButton("Registrar Votación");

        JPanel panelFormulario = new JPanel(new GridLayout(7, 2, 8, 8));

        panelFormulario.add(new JLabel("ID Sesión:"));
        panelFormulario.add(txtIdSesion);

        panelFormulario.add(new JLabel("Presentes:"));
        panelFormulario.add(txtPresentes);

        panelFormulario.add(new JLabel("Quórum mínimo:"));
        panelFormulario.add(txtMinimoQuorum);

        panelFormulario.add(new JLabel("Votos a favor:"));
        panelFormulario.add(txtFavor);

        panelFormulario.add(new JLabel("Votos en contra:"));
        panelFormulario.add(txtContra);

        panelFormulario.add(new JLabel("Abstenciones:"));
        panelFormulario.add(txtAbstenciones);

        panelFormulario.add(new JLabel(""));
        panelFormulario.add(btnRegistrar);

        areaResultado = new JTextArea();
        areaResultado.setEditable(false);

        add(panelFormulario, BorderLayout.NORTH);
        add(new JScrollPane(areaResultado), BorderLayout.CENTER);

        btnRegistrar.addActionListener(e -> registrarVotacion());
    }

    private void registrarVotacion() {

        try {

            int idSesion = Integer.parseInt(txtIdSesion.getText());
            int presentes = Integer.parseInt(txtPresentes.getText());
            int minimoQuorum = Integer.parseInt(txtMinimoQuorum.getText());
            int favor = Integer.parseInt(txtFavor.getText());
            int contra = Integer.parseInt(txtContra.getText());
            int abstenciones = Integer.parseInt(txtAbstenciones.getText());

            VotacionController controller = new VotacionController();

            String resultado = controller.registrarVotacion(
                idSesion,
                presentes,
                minimoQuorum,
                favor,
                contra,
                abstenciones
            );

            areaResultado.setText(
                "RESULTADO DE VOTACIÓN\n\n" +
                "Sesión: " + idSesion + "\n" +
                "Presentes: " + presentes + "\n" +
                "Quórum mínimo: " + minimoQuorum + "\n" +
                "A favor: " + favor + "\n" +
                "En contra: " + contra + "\n" +
                "Abstenciones: " + abstenciones + "\n\n" +
                "Resultado: " + resultado
            );

            JOptionPane.showMessageDialog(
                this,
                "Votación registrada con resultado: " + resultado
            );

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                this,
                "Todos los campos numéricos deben contener valores válidos."
            );
        }
    }
}