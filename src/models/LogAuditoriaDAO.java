package models;

import config.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LogAuditoriaDAO {

    public List<LogAuditoria> listarLogs() {

        List<LogAuditoria> logs = new ArrayList<>();

        String sql = """
            SELECT
                l.id_log,
                u.nombre AS usuario,
                l.accion,
                l.tabla_afectada,
                l.detalle,
                l.registro_id,
                l.fecha_hora
            FROM sys_log_auditoria l
            LEFT JOIN usuario u
                ON u.id_usuario = u.id_usuario
            ORDER BY l.fecha_hora DESC
        """;

        try (
                Connection connection = DatabaseConnection.connect();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {

                logs.add(
                        new LogAuditoria(
                                resultSet.getInt("id_log"),
                                resultSet.getString("usuario"),
                                resultSet.getString("accion"),
                                resultSet.getString("tabla_afectada"),
                                resultSet.getString("detalle"),
                                resultSet.getInt("registro_id"),
                                resultSet.getTimestamp("fecha_hora")
                                        .toLocalDateTime()
                        )
                );
            }

        } catch (Exception e) {

            System.out.println("Error consultando bitácora:");
            e.printStackTrace();
        }

        return logs;
    }
}