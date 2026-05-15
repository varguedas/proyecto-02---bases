package models;

import java.time.LocalDate;

public class Normativa {

    private int idNormativa;
    private String titulo;
    private String descripcion;
    private LocalDate fechaAprobacion;

    public Normativa(
        int idNormativa,
        String titulo,
        String descripcion,
        LocalDate fechaAprobacion
    ) {

        this.idNormativa = idNormativa;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaAprobacion = fechaAprobacion;
    }

    public int getIdNormativa() {
        return idNormativa;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDate getFechaAprobacion() {
        return fechaAprobacion;
    }
}


