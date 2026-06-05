package models;

import config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class VotacionDAO {

    public boolean registrarVotacion(Votacion votacion) {

        String sql = """
            INSERT INTO air.votacion (
                id_sesion,
                votos_favor,
                votos_contra,
                abstenciones,
                resultado
            )
            VALUES (?, ?, ?, ?, ?)
        """;

        try (
            Connection connection = DatabaseConnection.connect();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, votacion.getIdSesion());
            statement.setInt(2, votacion.getVotosFavor());
            statement.setInt(3, votacion.getVotosContra());
            statement.setInt(4, votacion.getAbstenciones());
            statement.setString(5, votacion.getResultado());

            int filasInsertadas = statement.executeUpdate();

            if (filasInsertadas > 0) {
                registrarLogAuditoria(
                    connection,
                    "INSERT",
                    "votacion",
                    "Votación registrada para sesión " +
                    votacion.getIdSesion() +
                    " con resultado " +
                    votacion.getResultado(),
                    votacion.getIdSesion()
                );
            }

            return filasInsertadas > 0;

        } catch (Exception e) {

            System.out.println("Error registrando votación:");
            e.printStackTrace();

            return false;
        }
    }

    public String calcularResultado(
        int presentes,
        int minimoQuorum,
        int votosFavor,
        int votosContra
    ) {

        if (presentes < minimoQuorum) {
            return "SIN_QUORUM";
        }

        if (votosFavor > votosContra) {
            return "APROBADO";
        }

        return "RECHAZADO";
    }

    public int contarPresentes(int idSesion) {

    String sql = """
        SELECT COUNT(*)
        FROM air.asistencia_sesion
        WHERE id_sesion = ?
        AND estado_asistencia = 'PRESENTE'
    """;

    try (
        Connection connection = DatabaseConnection.connect();
        PreparedStatement statement = connection.prepareStatement(sql)
    ) {

        statement.setInt(1, idSesion);

        var resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt(1);
        }

    } catch (Exception e) {

        System.out.println("Error contando presentes:");
        e.printStackTrace();
    }

    return 0;
}
public boolean actualizarEstadoPropuesta(
    int idPropuesta,
    String resultado
) {

    String nuevoEstado = "PENDIENTE";

    if (resultado.startsWith("APROBADO")) {
        nuevoEstado = "APROBADO";
    } else if (resultado.startsWith("RECHAZADO")) {
        nuevoEstado = "RECHAZADO";
    }

    String sql = """
        UPDATE air.propuesta_acuerdo
        SET estado = ?
        WHERE id_propuesta = ?
    """;

    try (
        Connection connection = DatabaseConnection.connect();
        PreparedStatement statement = connection.prepareStatement(sql)
    ) {

        statement.setString(1, nuevoEstado);
        statement.setInt(2, idPropuesta);

        return statement.executeUpdate() > 0;

    } catch (Exception e) {

        System.out.println("Error actualizando propuesta/acuerdo:");
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