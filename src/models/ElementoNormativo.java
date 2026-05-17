package models;

public class ElementoNormativo {

    private int idElemento;
    private Integer idPadre;
    private String tipo;
    private String titulo;
    private String estadoVigencia;
    private int orden;

    public ElementoNormativo(
        int idElemento,
        Integer idPadre,
        String tipo,
        String titulo,
        String estadoVigencia,
        int orden
    ) {
        this.idElemento = idElemento;
        this.idPadre = idPadre;
        this.tipo = tipo;
        this.titulo = titulo;
        this.estadoVigencia = estadoVigencia;
        this.orden = orden;
    }

    public int getIdElemento() {
        return idElemento;
    }

    public Integer getIdPadre() {
        return idPadre;
    }

    public String getTipo() {
        return tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getEstadoVigencia() {
        return estadoVigencia;
    }

    public int getOrden() {
        return orden;
    }

    @Override
    public String toString() {
        return tipo + " - " + titulo + " [" + estadoVigencia + "]";
    }
}