package models;

import config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class VotacionDAO {

    public boolean registrarVotacion(Votacion votacion) {

        String sql = """
            INSERT INTO air.votacion (
                id_sesion,
                votos_favor,
                votos_contra,
                abstenciones,
                resultado
            )
            VALUES (?, ?, ?, ?, ?)
        """;

        try (
            Connection connection = DatabaseConnection.connect();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, votacion.getIdSesion());
            statement.setInt(2, votacion.getVotosFavor());
            statement.setInt(3, votacion.getVotosContra());
            statement.setInt(4, votacion.getAbstenciones());
            statement.setString(5, votacion.getResultado());

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println("Error registrando votación:");
            e.printStackTrace();

            return false;
        }
    }

    public String calcularResultado(
        int presentes,
        int minimoQuorum,
        int votosFavor,
        int votosContra
    ) {

        if (presentes < minimoQuorum) {
            return "SIN_QUORUM";
        }

        if (votosFavor > votosContra) {
            return "APROBADO";
        }

        return "RECHAZADO";
    }
}