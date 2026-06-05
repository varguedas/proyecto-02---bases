package views;

import controllers.LogAuditoriaController;
import models.LogAuditoria;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BitacoraAuditoriaView extends JFrame {

    private JTextArea areaResultado;

    public BitacoraAuditoriaView() {

        setTitle("Bitácora de Auditoría");
        setSize(950, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JButton btnActualizar = new JButton("Actualizar Bitácora");

        areaResultado = new JTextArea();
        areaResultado.setEditable(false);
        areaResultado.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSuperior.add(btnActualizar);

        add(panelSuperior, BorderLayout.NORTH);
        add(new JScrollPane(areaResultado), BorderLayout.CENTER);

        btnActualizar.addActionListener(e -> cargarBitacora());

        cargarBitacora();
    }

    private void cargarBitacora() {

        LogAuditoriaController controller =
            new LogAuditoriaController();

        List<LogAuditoria> logs = controller.listarLogs();

        areaResultado.setText("BITÁCORA DE AUDITORÍA DEL SISTEMA\n\n");

        if (logs.isEmpty()) {

            areaResultado.append(
                "No existen registros de auditoría disponibles.\n"
            );

            return;
        }

        areaResultado.append(
            String.format(
                "%-6s | %-22s | %-12s | %-20s | %-10s | %-20s       | %s\n",
                "ID",
                "USUARIO",
                "ACCIÓN",
                "TABLA",
                "REGISTRO",
                "FECHA/HORA",
                "DETALLE"
            )
        );

        areaResultado.append(
            "-----------------------------------------------------------" +
                    "------------------------------------------------------" +
                    "-----------------------------------------------------------\n"
        );

        for (LogAuditoria log : logs) {

            areaResultado.append(
                String.format(
                    "%-6d | %-22s | %-12s | %-20s | %-10d | %-20s | %s\n",
                    log.getIdLog(),
                    log.getUsuario(),
                    log.getAccion(),
                    log.getTablaAfectada(),
                    log.getRegistroId(),
                    log.getFechaHora(),
                    log.getDetalle()
                )
            );
        }
    }
}
