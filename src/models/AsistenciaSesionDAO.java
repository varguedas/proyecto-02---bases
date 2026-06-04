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

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                "Error registrando asistencia:"
            );

            e.printStackTrace();

            return false;
        }
    }
}