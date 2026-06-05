package models;

import config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AsistenciaSesionDAO {

    public boolean registrarAsistencia(
        AsistenciaSesion asistencia
    ) {

        String sql = """
            INSERT INTO air.asistencia_sesion (
                id_sesion,
                id_asambleista,
                estado_asistencia
            )
            VALUES (?, ?, ?)
        """;

        try (
            Connection connection =
                DatabaseConnection.connect();

            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            statement.setInt(1, asistencia.getIdSesion());
            statement.setInt(2, asistencia.getIdAsambleista());
            statement.setString(
                3,
                asistencia.getEstadoAsistencia()
            );

            int filasInsertadas = statement.executeUpdate();

            if (filasInsertadas > 0) {
                registrarLogAuditoria(
                    connection,
                    "INSERT",
                    "asistencia_sesion",
                    "Registro de asistencia para sesión " +
                    asistencia.getIdSesion() +
                    " y asambleísta " +
                    asistencia.getIdAsambleista(),
                    asistencia.getIdSesion()
                );
            }

            return filasInsertadas > 0;

        } catch (Exception e) {

            System.out.println(
                "Error registrando asistencia:"
            );

            e.printStackTrace();

            return false;
        }
    }
    
    private void registrarLogAuditoria(
        Connection connection,
        String accion,
        String tablaAfectada,
        String detalle,
        int registroId
    ) throws Exception {

        String sql = """
            INSERT INTO air.sys_log_auditoria (
                id_usuario,
                accion,
                tabla_afectada,
                detalle,
                registro_id
            )
            VALUES (?, ?, ?, ?, ?)
        """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, 1);
            statement.setString(2, accion);
            statement.setString(3, tablaAfectada);
            statement.setString(4, detalle);
            statement.setInt(5, registroId);

            statement.executeUpdate();
        }
    }


}