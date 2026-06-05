package models;

import java.io.FileWriter;
import java.io.IOException;

import config.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CertificadoDAO {

    public Certificado generarCertificadoPorAsambleista(
        int idAsambleista
    ) {

        String sql = """
    SELECT
        a.asambleista_id,
        a.nombre AS nombre_completo,
        a.cedula,
        cs.nombre AS sector,
        n.fecha_inicio,
        n.fecha_fin,
        n.estado
    FROM air.asambleista a
    JOIN air.nombramiento n
        ON a.asambleista_id = n.asambleista_id
    JOIN air.catalogo_sector cs
        ON n.sector_id = cs.id_sector
    WHERE a.asambleista_id = ?
    ORDER BY n.fecha_inicio DESC
    LIMIT 1
""";

        try (
            Connection connection = DatabaseConnection.connect();
            PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, idAsambleista);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                String fechaEmision =
                    LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    );

                String folio =
                    generarFolio(
                        resultSet.getInt("asambleista_id")
                    );

                String contenidoHash =
                    folio + "|" +
                    resultSet.getString("nombre_completo") + "|" +
                    resultSet.getString("cedula") + "|" +
                    resultSet.getString("sector") + "|" +
                    fechaEmision;

                String hash =
                    generarHashSHA256(contenidoHash);

                Certificado certificado =
                    new Certificado(
                        0,
                        folio,
                        resultSet.getString("nombre_completo"),
                        resultSet.getString("cedula"),
                        resultSet.getString("sector"),
                        "Asambleísta",
                        fechaEmision,
                        hash
                    );

                registrarCertificado(
                    certificado,
                    resultSet.getInt("asambleista_id")
                );
                generarDocumentoHTML(certificado);

                return certificado;
            }

        } catch (Exception e) {

            System.out.println(
                "Error generando certificado:"
            );

            e.printStackTrace();
        }

        return null;
    }

    private void registrarCertificado(
        Certificado certificado,
        int idAsambleista
    ) {

        String sql = """
            INSERT INTO air.certificacion_emitida (
                id_asambleista,
                folio_unico,
                hash_seguridad
            )
            VALUES (?, ?, ?)
        """;

        try (
            Connection connection =
                DatabaseConnection.connect();

            PreparedStatement statement =
                connection.prepareStatement(sql)
        ) {

            statement.setInt(1, idAsambleista);
            statement.setString(2, certificado.getFolio());
            statement.setString(
                3,
                certificado.getHashSeguridad()
            );

            int filas = statement.executeUpdate();
            if (filas > 0) {
                registrarLogAuditoria(
                    connection,
                    "INSERT",
                    "certificacion_emitida",
                    "Certificación generada para el asambleísta " + idAsambleista,
                    idAsambleista
                );
            }

            System.out.println(
                "Certificado registrado. Filas insertadas: "
                + filas
            );

        } catch (Exception e) {

            System.out.println(
                "Error registrando certificado:"
            );

            e.printStackTrace();
        }
    }

    private void generarDocumentoHTML(Certificado certificado) {

    String nombreArchivo =
        certificado.getFolio() + ".html";

    String contenido = """
        <html>
        <head>
            <meta charset="UTF-8">
            <title>Certificación Institucional</title>
        </head>
        <body style="font-family: Arial; margin: 40px;">
            <h2>CERTIFICACIÓN INSTITUCIONAL</h2>
            <p><strong>Folio:</strong> %s</p>
            <p><strong>Nombre:</strong> %s</p>
            <p><strong>Cédula:</strong> %s</p>
            <p><strong>Sector:</strong> %s</p>
            <p><strong>Puesto:</strong> %s</p>
            <p><strong>Fecha de emisión:</strong> %s</p>
            <hr>
            <p><strong>Hash SHA-256:</strong></p>
            <p style="font-size: 11px;">%s</p>
            <br><br>
            <p>Documento generado automáticamente por el Sistema AIR.</p>
        </body>
        </html>
    """.formatted(
        certificado.getFolio(),
        certificado.getNombreAsambleista(),
        certificado.getCedula(),
        certificado.getSector(),
        certificado.getPuesto(),
        certificado.getFechaEmision(),
        certificado.getHashSeguridad()
    );

    try (FileWriter writer = new FileWriter(nombreArchivo)) {
        writer.write(contenido);
        System.out.println("Documento generado: " + nombreArchivo);
    } catch (IOException e) {
        System.out.println("Error generando documento HTML:");
        e.printStackTrace();
    }
}

    private String generarFolio(
        int idAsambleista
    ) {

        String timestamp =
            LocalDateTime.now().format(
                DateTimeFormatter.ofPattern(
                    "yyyyMMddHHmmss"
                )
            );

        return "CERT-" +
               idAsambleista +
               "-" +
               timestamp;
    }

    private String generarHashSHA256(
        String texto
    ) throws Exception {

        MessageDigest digest =
            MessageDigest.getInstance(
                "SHA-256"
            );

        byte[] encodedHash =
            digest.digest(
                texto.getBytes("UTF-8")
            );

        StringBuilder hexString =
            new StringBuilder();

        for (byte b : encodedHash) {

            String hex =
                Integer.toHexString(
                    0xff & b
                );

            if (hex.length() == 1) {
                hexString.append('0');
            }

            hexString.append(hex);
        }

        return hexString.toString();
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