package models;

import config.DatabaseConnection;
import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class NombramientoDAO {

    private String ultimoError;

    public boolean registrarNombramiento(
        int idAsambleista,
        String sector,
        LocalDate fechaInicio,
        LocalDate fechaFin
    ) {

        ultimoError = null;

        String sql = """
            SELECT sp_registrar_nombramiento(?, ?, ?, ?)
        """;

        try (
            Connection connection = DatabaseConnection.connect();
            CallableStatement statement = connection.prepareCall(sql)
        ) {

            statement.setInt(1, idAsambleista);
            statement.setString(2, sector);
            statement.setDate(3, Date.valueOf(fechaInicio));

            if (fechaFin != null) {
                statement.setDate(4, Date.valueOf(fechaFin));
            } else {
                statement.setNull(4, Types.DATE);
            }

            statement.execute();

            return true;

        } catch (Exception e) {

            ultimoError = limpiarMensajeError(e.getMessage());

            System.out.println("Error registrando nombramiento:");
            e.printStackTrace();

            return false;
        }
    }

    public List<Nombramiento> listarPorAsambleista(
        int idAsambleista
    ) {

        List<Nombramiento> lista = new ArrayList<>();

        String sql = """
            SELECT
                id_nombramiento,
                id_asambleista,
                sector,
                fecha_inicio,
                fecha_fin,
                estado
            FROM nombramiento
            WHERE id_asambleista = ?
            ORDER BY fecha_inicio DESC
        """;

        try (
            Connection connection = DatabaseConnection.connect();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, idAsambleista);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Date fechaFinSql =
                    resultSet.getDate("fecha_fin");

                LocalDate fechaFin = null;

                if (fechaFinSql != null) {
                    fechaFin = fechaFinSql.toLocalDate();
                }

                Nombramiento nombramiento =
                    new Nombramiento(
                        resultSet.getInt("id_nombramiento"),
                        resultSet.getInt("id_asambleista"),
                        resultSet.getString("sector"),
                        resultSet.getDate("fecha_inicio").toLocalDate(),
                        fechaFin,
                        resultSet.getString("estado")
                    );

                lista.add(nombramiento);
            }

        } catch (Exception e) {

            System.out.println("Error listando nombramientos:");
            e.printStackTrace();
        }

        return lista;
    }

    private String limpiarMensajeError(String mensajeOriginal) {

        if (mensajeOriginal == null || mensajeOriginal.isBlank()) {
            return "Ocurrió un error al registrar el nombramiento.";
        }

        String mensajeLimpio = mensajeOriginal;

        if (mensajeLimpio.contains("Where:")) {
            mensajeLimpio = mensajeLimpio.substring(
                0,
                mensajeLimpio.indexOf("Where:")
            );
        }

        mensajeLimpio = mensajeLimpio
            .replace("ERROR:", "")
            .trim();

        return mensajeLimpio;
    }

    public String getUltimoError() {
        return ultimoError;
    }
}