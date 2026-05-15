package models;

import config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {

    public Usuario autenticarUsuario(String correo, String password) {

        String sql = """
            SELECT id_usuario, nombre, correo, password_hash
            FROM usuario
            WHERE correo = ? AND password_hash = ?
        """;

        try (
            Connection connection = DatabaseConnection.connect();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, correo);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return new Usuario(
                    resultSet.getInt("id_usuario"),
                    resultSet.getString("nombre"),
                    resultSet.getString("correo"),
                    resultSet.getString("password_hash")
                );
            }

        } catch (Exception e) {
            System.out.println("Error autenticando usuario:");
            e.printStackTrace();
        }

        return null;
    }
}