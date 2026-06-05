package controllers;

import java.util.List;
import models.LogAuditoria;
import models.LogAuditoriaDAO;

public class LogAuditoriaController {

    private LogAuditoriaDAO logAuditoriaDAO;

    public LogAuditoriaController() {
        logAuditoriaDAO = new LogAuditoriaDAO();
    }

    public List<LogAuditoria> listarLogs() {
        return logAuditoriaDAO.listarLogs();
    }
}
