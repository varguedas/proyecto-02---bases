package models;

public class Certificado {

    private int idCertificado;
    private String folio;
    private String nombreAsambleista;
    private String cedula;
    private String sector;
    private String puesto;
    private String fechaEmision;
    private String hashSeguridad;

    public Certificado(
        int idCertificado,
        String folio,
        String nombreAsambleista,
        String cedula,
        String sector,
        String puesto,
        String fechaEmision,
        String hashSeguridad
    ) {
        this.idCertificado = idCertificado;
        this.folio = folio;
        this.nombreAsambleista = nombreAsambleista;
        this.cedula = cedula;
        this.sector = sector;
        this.puesto = puesto;
        this.fechaEmision = fechaEmision;
        this.hashSeguridad = hashSeguridad;
    }

    public int getIdCertificado() {
        return idCertificado;
    }

    public String getFolio() {
        return folio;
    }

    public String getNombreAsambleista() {
        return nombreAsambleista;
    }

    public String getCedula() {
        return cedula;
    }

    public String getSector() {
        return sector;
    }

    public String getPuesto() {
        return puesto;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public String getHashSeguridad() {
        return hashSeguridad;
    }
}
