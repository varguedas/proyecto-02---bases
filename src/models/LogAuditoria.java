package models;

import java.time.LocalDateTime;

public class LogAuditoria {

    private int idLog;
    private String usuario;
    private String accion;
    private String tablaAfectada;
    private String detalle;
    private int registroId;
    private LocalDateTime fechaHora;

    public LogAuditoria(
            int idLog,
            String usuario,
            String accion,
            String tablaAfectada,
            String detalle,
            int registroId,
            LocalDateTime fechaHora
    ) {
        this.idLog = idLog;
        this.usuario = usuario;
        this.accion = accion;
        this.tablaAfectada = tablaAfectada;
        this.detalle = detalle;
        this.registroId = registroId;
        this.fechaHora = fechaHora;
    }

    public int getIdLog() {
        return idLog;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getAccion() {
        return accion;
    }

    public String getTablaAfectada() {
        return tablaAfectada;
    }

    public String getDetalle() {
        return detalle;
    }

    public int getRegistroId() {
        return registroId;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
}