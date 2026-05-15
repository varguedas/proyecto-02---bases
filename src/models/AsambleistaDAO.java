package models;

import config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AsambleistaDAO {

    public boolean registrarAsambleista(Asambleista asambleista) {

        String sql = """
            INSERT INTO asambleista
            (nombre_completo, cedula, sector, estado)
            VALUES (?, ?, ?, ?)
        """;

        try (
            Connection connection = DatabaseConnection.connect();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, asambleista.getNombreCompleto());
            statement.setString(2, asambleista.getCedula());
            statement.setString(3, asambleista.getSector());
            statement.setString(4, asambleista.getEstado());

            int filas = statement.executeUpdate();

            return filas > 0;

        } catch (Exception e) {

            System.out.println("Error registrando asambleísta:");
            e.printStackTrace();

            return false;
        }
    }

    public List<Asambleista> listarAsambleistas() {

        List<Asambleista> lista = new ArrayList<>();

        String sql = """
            SELECT *
            FROM asambleista
            ORDER BY nombre_completo
        """;

        try (
            Connection connection = DatabaseConnection.connect();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Asambleista asambleista = new Asambleista(
                    resultSet.getInt("id_asambleista"),
                    resultSet.getString("nombre_completo"),
                    resultSet.getString("cedula"),
                    resultSet.getString("sector"),
                    resultSet.getString("estado")
                );

                lista.add(asambleista);
            }

        } catch (Exception e) {

            System.out.println("Error listando asambleístas:");
            e.printStackTrace();
        }

        return lista;
    }
}