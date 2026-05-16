package models;

import config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {

    public Usuario autenticarUsuario(
        String correo,
        String password
    ) {

        String sql = """
            SELECT 
                u.id_usuario,
                u.nombre,
                u.correo,
                u.password_hash,
                r.nombre AS rol
            FROM usuario u
            JOIN usuario_rol ur
                ON u.id_usuario = ur.id_usuario
            JOIN rol r
                ON ur.id_rol = r.id_rol
            WHERE u.correo = ?
            AND u.password_hash = ?
        """;

        try (
            Connection connection =
                DatabaseConnection.connect();

            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            statement.setString(1, correo);
            statement.setString(2, password);

            ResultSet resultSet =
                statement.executeQuery();

            if (resultSet.next()) {

                return new Usuario(
                    resultSet.getInt("id_usuario"),
                    resultSet.getString("nombre"),
                    resultSet.getString("correo"),
                    resultSet.getString("password_hash"),
                    resultSet.getString("rol")
                );
            }

        } catch (Exception e) {

            System.out.println(
                "Error autenticando usuario:"
            );

            e.printStackTrace();
        }

        return null;
    }
}