package models;

import config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ElementoNormativoDAO {

    public List<ElementoNormativo> listarElementos() {

        List<ElementoNormativo> elementos = new ArrayList<>();

        String sql = """
            SELECT
                id_elemento,
                id_padre,
                tipo,
                titulo,
                estado_vigencia,
                orden
            FROM normativa_arbol
            ORDER BY
                COALESCE(id_padre, 0),
                orden,
                id_elemento
        """;

        try (
            Connection connection = DatabaseConnection.connect();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Integer idPadre = null;

                Object valorPadre = resultSet.getObject("id_padre");

                if (valorPadre != null) {
                    idPadre = resultSet.getInt("id_padre");
                }

                ElementoNormativo elemento = new ElementoNormativo(
                    resultSet.getInt("id_elemento"),
                    idPadre,
                    resultSet.getString("tipo"),
                    resultSet.getString("titulo"),
                    resultSet.getString("estado_vigencia"),
                    resultSet.getInt("orden")
                );

                elementos.add(elemento);
            }

        } catch (Exception e) {

            System.out.println("Error listando árbol normativo:");
            e.printStackTrace();
        }

        return elementos;
    }
}