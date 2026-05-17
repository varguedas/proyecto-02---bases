package models;

import config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NormativaDAO {

    public boolean registrarNormativa(Normativa normativa) {

        String sql = """
            INSERT INTO normativa
            (titulo, descripcion, fecha_aprobacion)
            VALUES (?, ?, ?)
        """;

        try (
            Connection connection = DatabaseConnection.connect();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, normativa.getTitulo());
            statement.setString(2, normativa.getDescripcion());
            statement.setDate(
                3,
                java.sql.Date.valueOf(normativa.getFechaAprobacion())
            );

            int filas = statement.executeUpdate();

            return filas > 0;

        } catch (Exception e) {

            System.out.println("Error registrando normativa:");
            e.printStackTrace();

            return false;
        }
    }

    public List<Normativa> listarNormativas() {

        List<Normativa> lista = new ArrayList<>();

        String sql = """
            SELECT *
            FROM normativa
            ORDER BY fecha_aprobacion DESC
        """;

        try (
            Connection connection = DatabaseConnection.connect();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Normativa normativa = new Normativa(
                    resultSet.getInt("id_normativa"),
                    resultSet.getString("titulo"),
                    resultSet.getString("descripcion"),
                    resultSet.getDate("fecha_aprobacion").toLocalDate()
                );

                lista.add(normativa);
            }

        } catch (Exception e) {

            System.out.println("Error listando normativas:");
            e.printStackTrace();
        }

        return lista;
    }

    public boolean actualizarDescripcionNormativa(
        int idNormativa,
        String nuevaDescripcion
    ) {

        String sql = """
            UPDATE normativa
            SET descripcion = ?
            WHERE id_normativa = ?
        """;

        try (
            Connection connection = DatabaseConnection.connect();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, nuevaDescripcion);
            statement.setInt(2, idNormativa);

            int filas = statement.executeUpdate();

            return filas > 0;

        } catch (Exception e) {

            System.out.println("Error actualizando normativa:");
            e.printStackTrace();

            return false;
        }
    }
}