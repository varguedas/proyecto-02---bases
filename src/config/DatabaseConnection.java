package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

private static final String URL =
    "jdbc:postgresql://ep-curly-shadow-ap1twptz-pooler.c-7.us-east-1.aws.neon.tech/neondb?sslmode=require&channelBinding=require";
private static final String USER = "neondb_owner";

private static final String PASSWORD = "npg_XOnTVFH8alK4";

    public static Connection connect() {

        try {

            Connection connection = DriverManager.getConnection(
                URL,
                USER,
                PASSWORD
            );

            System.out.println("Conexión exitosa a Neon PostgreSQL");

            return connection;

        } catch (Exception e) {

            System.out.println("Error de conexión:");
            e.printStackTrace();

            return null;
        }
    }
}