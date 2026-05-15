package models;

import config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SesionDAO {

    public boolean crearSesion(Sesion sesion) {
        String sql = "INSERT INTO sesiones (titulo, fecha, descripcion, estado) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, sesion.getTitulo());
            statement.setDate(2, java.sql.Date.valueOf(sesion.getFecha()));
            statement.setString(3, sesion.getDescripcion());
            statement.setString(4, sesion.getEstado());

            int filasInsertadas = statement.executeUpdate();
            return filasInsertadas > 0;

        } catch (Exception e) {
            System.out.println("Error al crear sesión:");
            e.printStackTrace();
            return false;
        }
    }

    public List<Sesion> listarSesiones() {
        List<Sesion> sesiones = new ArrayList<>();

        String sql = "SELECT id_sesion, titulo, fecha, descripcion, estado FROM sesiones ORDER BY fecha DESC";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Sesion sesion = new Sesion(
                    resultSet.getInt("id_sesion"),
                    resultSet.getString("titulo"),
                    resultSet.getDate("fecha").toLocalDate(),
                    resultSet.getString("descripcion"),
                    resultSet.getString("estado")
                );

                sesiones.add(sesion);
            }

        } catch (Exception e) {
            System.out.println("Error al listar sesiones:");
            e.printStackTrace();
        }

        return sesiones;
    }

    public Sesion buscarSesionPorId(int idSesion) {
        String sql = "SELECT id_sesion, titulo, fecha, descripcion, estado FROM sesiones WHERE id_sesion = ?";

        try (Connection connection = DatabaseConnection.connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, idSesion);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Sesion(
                        resultSet.getInt("id_sesion"),
                        resultSet.getString("titulo"),
                        resultSet.getDate("fecha").toLocalDate(),
                        resultSet.getString("descripcion"),
                        resultSet.getString("estado")
                    );
                }
            }

        } catch (Exception e) {
            System.out.println("Error al buscar sesión:");
            e.printStackTrace();
        }

        return null;
    }
}