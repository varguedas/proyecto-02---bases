package models;

import config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SesionDAO {

    public boolean crearSesion(Sesion sesion) {

        String sql = """
            INSERT INTO air.sesiones (
                id_tipo_modalidad,
                id_tipo_sesion,
                numero_sesion,
                fecha,
                link_acta,
                quorum_requerido
            )
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (
            Connection connection = DatabaseConnection.connect();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, sesion.getIdTipoModalidad());
            statement.setInt(2, sesion.getIdTipoSesion());
            statement.setString(3, sesion.getNumeroSesion());
            statement.setDate(4, Date.valueOf(sesion.getFecha()));
            statement.setString(5, sesion.getLinkActa());
            statement.setInt(6, sesion.getQuorumRequerido());

            int filas = statement.executeUpdate();

            if (filas > 0) {
                registrarLogAuditoria(
                    connection,
                    "INSERT",
                    "sesiones",
                    "Sesión registrada: " + sesion.getNumeroSesion(),
                    0
                );
            }

            return filas > 0;

        } catch (Exception e) {
            System.out.println("Error al crear sesión:");
            e.printStackTrace();
            return false;
        }
    }

    public List<Sesion> listarSesiones() {

        List<Sesion> sesiones = new ArrayList<>();

        String sql = """
            SELECT
                id_sesion,
                id_tipo_modalidad,
                id_tipo_sesion,
                numero_sesion,
                fecha,
                link_acta,
                quorum_requerido
            FROM air.sesiones
            ORDER BY fecha DESC, id_sesion DESC
        """;

        try (
            Connection connection = DatabaseConnection.connect();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Sesion sesion = new Sesion(
                    resultSet.getInt("id_sesion"),
                    resultSet.getInt("id_tipo_modalidad"),
                    resultSet.getInt("id_tipo_sesion"),
                    resultSet.getString("numero_sesion"),
                    resultSet.getDate("fecha").toLocalDate(),
                    resultSet.getString("link_acta"),
                    resultSet.getInt("quorum_requerido")
                );

                sesiones.add(sesion);
            }

        } catch (Exception e) {
            System.out.println("Error al listar sesiones:");
            e.printStackTrace();
        }

        return sesiones;
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